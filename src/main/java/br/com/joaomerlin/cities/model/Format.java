package br.com.joaomerlin.cities.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Format {

    JSON("json", "application/json"),
    CSV("csv", "text/csv");

    private String ext;
    private String contentType;

    public static Format getFormat(String name) {
        return Stream.of(values()).filter(e -> e.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
