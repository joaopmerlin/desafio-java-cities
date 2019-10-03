package br.com.joaomerlin.cities.client;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.joaomerlin.cities.client.fallback.CityClientFallback;
import br.com.joaomerlin.cities.model.City;

@FeignClient(value = "ibge", path = "api/v1/localidades", fallback = CityClientFallback.class)
public interface CityClient {

    @Cacheable("CityClientFindAll")
    @RequestMapping(value = "municipios", method = RequestMethod.GET)
    List<City> findAll();

    @RequestMapping(value = "estados/{id}/municipios", method = RequestMethod.GET)
    List<City> findByState(@PathVariable Integer id);

}
