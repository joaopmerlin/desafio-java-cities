package br.com.joaomerlin.cities.service.impl;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;

@Service
public class ExportCsvServiceImpl extends ExportServiceImpl implements ExportService {

    private final CsvMapper csvMapper;

    public ExportCsvServiceImpl(CityClient cityClient) {
        super(cityClient);
        this.csvMapper = new CsvMapper();
    }

    @Override
    protected void write(OutputStream outputStream) {
        try {
            CsvSchema csvSchema = csvMapper.schemaFor(Export.class).withHeader();
            outputStream.write(csvMapper.writer(csvSchema).writeValueAsBytes(buildExport()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write csv", e);
        }
    }

    @Override
    public Format getFormat() {
        return Format.CSV;
    }
}
