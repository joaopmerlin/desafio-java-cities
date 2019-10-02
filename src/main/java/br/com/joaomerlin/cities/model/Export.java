package br.com.joaomerlin.cities.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

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

    @JsonProperty("nomeFormatado")
    public String getFormattedName() {
        return String.format("%s/%s", cityName, stateAcronym);
    }
}
