package br.com.joaomerlin.cities.client.fallback;

import br.com.joaomerlin.cities.client.StateClient;
import br.com.joaomerlin.cities.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StateClientFallback implements StateClient {

    @Override
    public List<State> findAll() {
        log.warn("Returning fallback for StateClient.findAll");
        return List.of();
    }
}
