package br.com.joaomerlin.cities.service;

import br.com.joaomerlin.cities.model.City;

import java.util.Optional;

public interface CityService {

    Optional<City> findByName(String name);

    Optional<City> findByNameAndState(String name, String state);
}
