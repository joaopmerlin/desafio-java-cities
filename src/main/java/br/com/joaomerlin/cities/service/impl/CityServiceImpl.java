package br.com.joaomerlin.cities.service.impl;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.client.StateClient;
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
    private final StateClient stateClient;

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
        return stateClient.findAll().parallelStream()
                .filter(e -> e.getAcronym().equalsIgnoreCase(state)).findAny()
                .flatMap(e -> cityClient.findByState(e.getId()).parallelStream()
                        .filter(i -> i.getName().equalsIgnoreCase(name)).findAny());
    }
}
