package br.com.joaomerlin.cities.service.impl;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.City;
import br.com.joaomerlin.cities.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityClient cityClient;

    @Override
    @Cacheable("CityServiceImplFindByName")
    public Optional<City> findByName(String name) {
        return cityClient.findAll().parallelStream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    @Cacheable("CityServiceImplFindByNameAndState")
    public Optional<City> findByNameAndState(String name, String state) {
        return cityClient.findAll().parallelStream()
                .filter(e -> e.getMicroRegion().getMesoRegion().getState().getAcronym().equalsIgnoreCase(state))
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
