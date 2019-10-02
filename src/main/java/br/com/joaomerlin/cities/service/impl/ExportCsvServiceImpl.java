package br.com.joaomerlin.cities.service.impl;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportCsvServiceImpl implements ExportService {

    private final CityClient cityClient;
    private final CsvMapper csvMapper;

    public ExportCsvServiceImpl(CityClient cityClient) {
        this.cityClient = cityClient;
        this.csvMapper = new CsvMapper();
    }

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
            CsvSchema csvSchema = csvMapper.schemaFor(Export.class).withHeader();
            outputStream.write(csvMapper.writer(csvSchema).writeValueAsBytes(exportList));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write csv", e);
        }
    }

    @Override
    public Format getFormat() {
        return Format.CSV;
    }
}
