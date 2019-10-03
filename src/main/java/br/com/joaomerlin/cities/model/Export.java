package br.com.joaomerlin.cities.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Export implements Serializable {

    @JsonProperty("idEstado")
    private Integer stateId;

    @JsonProperty("siglaEstado")
    private String stateAcronym;

    @JsonProperty("regiaoNome")
    private String regionName;

    @JsonProperty("nomeCidade")
    private String cityName;

    @JsonProperty("nomeMesorregiao")
    private String mesoRegionName;

    public static Export fromCity(City city) {
        return Export.builder()
                .stateId(city.getMicroRegion().getMesoRegion().getState().getId())
                .stateAcronym(city.getMicroRegion().getMesoRegion().getState().getAcronym())
                .regionName(city.getMicroRegion().getMesoRegion().getState().getRegion().getName())
                .cityName(city.getName())
                .mesoRegionName(city.getMicroRegion().getMesoRegion().getName())
                .build();
    }

    @JsonProperty("nomeFormatado")
    public String getFormattedName() {
        return String.format("%s/%s", cityName, stateAcronym);
    }
}
