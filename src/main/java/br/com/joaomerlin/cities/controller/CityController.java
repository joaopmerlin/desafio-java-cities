package br.com.joaomerlin.cities.controller;

import br.com.joaomerlin.cities.service.CityService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation("Search city by name")
    public ResponseEntity<Integer> search(@PathVariable String name) {
        return service.findByName(name).map(e -> ResponseEntity.ok(e.getId()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("search/{state}/{name}")
    @ApiOperation("Search city by state and name")
    public ResponseEntity<Integer> search(@PathVariable String state, @PathVariable String name) {
        return service.findByNameAndState(name, state).map(e -> ResponseEntity.ok(e.getId()))
                .orElse(ResponseEntity.notFound().build());
    }

}
