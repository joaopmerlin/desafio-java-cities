package br.com.joaomerlin.cities.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;
import br.com.joaomerlin.cities.service.FileService;

@Service
public class ExportCsvServiceImpl extends ExportServiceImpl implements ExportService {

    private final CsvMapper csvMapper;

    public ExportCsvServiceImpl(CityClient cityClient, FileService fileService) {
        super(cityClient, fileService);
        this.csvMapper = new CsvMapper();
    }

    @Override
    protected byte[] write(List<Export> exports) throws Exception {
        CsvSchema csvSchema = csvMapper.schemaFor(Export.class).withHeader();
        return csvMapper.writer(csvSchema).writeValueAsBytes(exports);
    }

    @Override
    public Format getFormat() {
        return Format.CSV;
    }
}
