package br.com.joaomerlin.cities.client.fallback;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.City;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CityClientFallback implements CityClient {

    @Override
    @CacheEvict("CityClientFindAll")
    public List<City> findAll() {
        log.warn("Returning fallback for CityClient.findAll");
        return List.of();
    }

    @Override
    @CacheEvict("CityClientFindByState")
    public List<City> findByState(Integer id) {
        log.warn("Returning fallback for CityClient.findByState");
        return List.of();
    }
}
