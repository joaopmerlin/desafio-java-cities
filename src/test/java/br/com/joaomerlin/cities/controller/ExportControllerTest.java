package br.com.joaomerlin.cities.controller;

import br.com.joaomerlin.cities.CitiesApplicationTests;
import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.*;
import br.com.joaomerlin.cities.service.FileService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExportControllerTest extends CitiesApplicationTests {

    @MockBean
    private CityClient cityClient;

    @MockBean
    private FileService fileService;

    @Autowired
    private MockMvc mvc;

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
        when(fileService.get(anyString())).thenReturn(null);
    }

    @Test
    public void exportJsonTest() throws Exception {
        String expected = "[{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Blumenau\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Blumenau/SC\"},{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Brusque\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Brusque/SC\"},{\"idEstado\":1,\"siglaEstado\":\"SC\",\"regiaoNome\":\"Sul\",\"nomeCidade\":\"Gaspar\",\"nomeMesorregiao\":\"Vale do Itajaí\",\"nomeFormatado\":\"Gaspar/SC\"}]";

        mvc.perform(MockMvcRequestBuilders
                .get("/export/json")
                .accept("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void exportCsvTest() throws Exception {
        String expected = "idEstado,nomeCidade,nomeFormatado,nomeMesorregiao,regiaoNome,siglaEstado\n" +
                "1,Blumenau,Blumenau/SC,\"Vale do Itajaí\",Sul,SC\n" +
                "1,Brusque,Brusque/SC,\"Vale do Itajaí\",Sul,SC\n" +
                "1,Gaspar,Gaspar/SC,\"Vale do Itajaí\",Sul,SC\n";

        mvc.perform(MockMvcRequestBuilders
                .get("/export/csv")
                .accept("text/csv"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void exportInvalidFormatTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/export/xml")
                .accept("application/xml"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
