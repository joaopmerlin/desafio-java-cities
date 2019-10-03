package br.com.joaomerlin.cities.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.joaomerlin.cities.client.fallback.StateClientFallback;
import br.com.joaomerlin.cities.model.State;

@FeignClient(value = "ibge", path = "api/v1/localidades", fallback = StateClientFallback.class)
public interface StateClient {

    @RequestMapping(value = "estados", method = RequestMethod.GET)
    List<State> findAll();

}
