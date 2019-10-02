package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Region implements Serializable {

    private Integer id;

    @JsonProperty("sigla")
    private String acronym;

    @JsonProperty("nome")
    private String name;
}
