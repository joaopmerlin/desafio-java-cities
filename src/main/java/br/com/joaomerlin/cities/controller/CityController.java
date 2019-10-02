package br.com.joaomerlin.cities.controller;

import br.com.joaomerlin.cities.model.City;
import br.com.joaomerlin.cities.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("city")
@RequiredArgsConstructor
public class CityController {

    private final CityService service;

    @GetMapping("search/{name}")
    public Integer search(@PathVariable String name) {
        return service.findByName(name).map(City::getId).orElse(null);
    }

    @GetMapping("search/{state}/{name}")
    public Integer search(@PathVariable String state, @PathVariable String name) {
        return service.findByNameAndState(name, state).map(City::getId).orElse(null);
    }

}
