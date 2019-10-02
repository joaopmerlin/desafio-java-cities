package br.com.joaomerlin.cities.client.fallback;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.City;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CityClientFallback implements CityClient {

    @Override
    public List<City> findAll() {
        log.warn("Returning fallback for CityClient.findAll");
        return List.of();
    }

    @Override
    public List<City> findByState(Integer id) {
        log.warn("Returning fallback for CityClient.findByState");
        return List.of();
    }
}
