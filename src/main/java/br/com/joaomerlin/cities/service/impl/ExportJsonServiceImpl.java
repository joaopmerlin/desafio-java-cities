package br.com.joaomerlin.cities.service.impl;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportJsonServiceImpl implements ExportService {

    private final CityClient cityClient;
    private final ObjectMapper objectMapper;

    @Override
    public void export(OutputStream outputStream) {
        List<Export> exportList = cityClient.findAll().parallelStream()
                .map(e -> Export.builder()
                        .stateId(e.getMicroRegion().getMesoRegion().getState().getId())
                        .stateAcronym(e.getMicroRegion().getMesoRegion().getState().getAcronym())
                        .regionName(e.getMicroRegion().getMesoRegion().getState().getRegion().getName())
                        .cityName(e.getName())
                        .mesoRegionName(e.getMicroRegion().getMesoRegion().getName())
                        .build())
                .collect(Collectors.toList());

        try {
            outputStream.write(objectMapper.writeValueAsBytes(exportList));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write json", e);
        }
    }

    @Override
    public Format getFormat() {
        return Format.JSON;
    }
}
