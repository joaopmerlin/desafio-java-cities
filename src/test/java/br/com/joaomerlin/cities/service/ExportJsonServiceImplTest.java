package br.com.joaomerlin.cities.service;

import br.com.joaomerlin.cities.CitiesApplicationTests;
import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.*;
import br.com.joaomerlin.cities.service.impl.ExportJsonServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExportJsonServiceImplTest extends CitiesApplicationTests {

    @MockBean
    private CityClient cityClient;

    @MockBean
    private FileService fileService;

    @Autowired
    private ExportJsonServiceImpl service;

    @Before
    public void before() {
        Region region = new Region(1, "S", "Sul");
        State state = new State(1, "SC", "Santa Catarina", region);
        MesoRegion mesoRegion = new MesoRegion(1, "Vale do Itajaí", state);
        MicroRegion microRegion = new MicroRegion(1, "Blumenau", mesoRegion);
        List<City> cities = List.of(
                new City(1, "Blumenau", microRegion),
                new City(2, "Brusque", microRegion),
                new City(3, "Gaspar", microRegion)
        );
        when(cityClient.findAll()).thenReturn(cities);
    }

    @Test
    public void exportTest() throws IOException {
        when(fileService.get(anyString())).thenReturn(null);
        when(fileService.store(anyString(), any())).thenReturn(null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        service.export(outputStream);

        String expected = "[{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Blumenau\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Blumenau/SC\"},{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Brusque\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Brusque/SC\"},{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Gaspar\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Gaspar/SC\"}]";

        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void exportWithCacheTest() throws IOException {
        when(fileService.get(anyString())).thenReturn(null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        service.export(outputStream);

        String fileName = String.format("export_%s.%s", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE), "json");

        String expected = "[{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Blumenau\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Blumenau/SC\"},{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Brusque\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Brusque/SC\"},{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Gaspar\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Gaspar/SC\"}]";

        when(fileService.get(anyString())).thenReturn(new ByteArrayInputStream(expected.getBytes()));

        service.export(outputStream);

        verify(fileService, new Times(2)).get(fileName);
        verify(fileService, new Times(1)).store(fileName, expected.getBytes());
    }
}
