package br.com.joaomerlin.cities.controller;

import br.com.joaomerlin.cities.CitiesApplicationTests;
import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.client.StateClient;
import br.com.joaomerlin.cities.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest extends CitiesApplicationTests {

    @MockBean
    private CityClient cityClient;

    @MockBean
    private StateClient stateClient;

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
        when(stateClient.findAll()).thenReturn(List.of(state));
        when(cityClient.findAll()).thenReturn(cities);
        when(cityClient.findByState(1)).thenReturn(cities);
    }

    @Test
    public void searchByNameTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/city/search/{name}", "blumenau")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void searchByNameNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/city/search/{name}", "notfound")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchByNameAndStateTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/city/search/{state}/{name}", "sc", "brusque")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(2));
    }

    @Test
    public void searchByNameAndStateNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/city/search/{state}/{name}", "sc", "notfound")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
