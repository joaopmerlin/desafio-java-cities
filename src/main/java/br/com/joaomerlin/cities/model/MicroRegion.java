package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MicroRegion implements Serializable {

    private Integer id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("mesorregiao")
    private MesoRegion mesoRegion;

}
