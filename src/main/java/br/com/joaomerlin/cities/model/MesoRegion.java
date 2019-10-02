package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MesoRegion implements Serializable {

    private Integer id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("UF")
    private State state;
}
