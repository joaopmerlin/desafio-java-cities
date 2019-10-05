package br.com.joaomerlin.cities.service;

import br.com.joaomerlin.cities.CitiesApplicationTests;
import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.client.StateClient;
import br.com.joaomerlin.cities.model.*;
import br.com.joaomerlin.cities.service.impl.CityServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CityServiceImplTest extends CitiesApplicationTests {

    @MockBean
    private CityClient cityClient;

    @MockBean
    private StateClient stateClient;

    @Autowired
    private CityServiceImpl service;

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
        when(stateClient.findAll()).thenReturn(List.of(state));
        when(cityClient.findAll()).thenReturn(cities);
        when(cityClient.findByState(1)).thenReturn(cities);
    }

    @Test
    public void findByNameTest() {
        City output = service.findByName("blumenau").get();

        assertEquals("Blumenau", output.getName());
        assertEquals("Blumenau", output.getMicroRegion().getName());
        assertEquals("Vale do Itajaí", output.getMicroRegion().getMesoRegion().getName());
        assertEquals("Santa Catarina", output.getMicroRegion().getMesoRegion().getState().getName());
        assertEquals("Sul", output.getMicroRegion().getMesoRegion().getState().getRegion().getName());
    }

    @Test
    public void findByNameAndStateTest() {
        City output = service.findByNameAndState("blumenau", "sc").get();

        assertEquals("Blumenau", output.getName());
        assertEquals("Blumenau", output.getMicroRegion().getName());
        assertEquals("Vale do Itajaí", output.getMicroRegion().getMesoRegion().getName());
        assertEquals("Santa Catarina", output.getMicroRegion().getMesoRegion().getState().getName());
        assertEquals("Sul", output.getMicroRegion().getMesoRegion().getState().getRegion().getName());
    }
}
