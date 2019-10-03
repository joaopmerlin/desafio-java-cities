package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region implements Serializable {

    private Integer id;

    @JsonProperty("sigla")
    private String acronym;

    @JsonProperty("nome")
    private String name;
}
