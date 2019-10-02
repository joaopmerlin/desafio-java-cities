package br.com.joaomerlin.cities.client;

import br.com.joaomerlin.cities.client.fallback.StateClientFallback;
import br.com.joaomerlin.cities.model.State;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "ibge", path = "api/v1/localidades", fallback = StateClientFallback.class)
public interface StateClient {

    @Cacheable("StateClientFindAll")
    @RequestMapping(value = "estados", method = RequestMethod.GET)
    List<State> findAll();

}
