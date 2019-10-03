package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroRegion implements Serializable {

    private Integer id;

    @JsonProperty("nome")
    private String name;

    @JsonProperty("mesorregiao")
    private MesoRegion mesoRegion;

}
