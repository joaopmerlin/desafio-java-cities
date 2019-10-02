package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {

    private Integer id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("microrregiao")
    private MicroRegion microRegion;
}
