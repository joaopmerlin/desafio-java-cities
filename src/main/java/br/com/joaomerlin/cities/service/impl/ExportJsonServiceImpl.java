package br.com.joaomerlin.cities.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;
import br.com.joaomerlin.cities.service.FileService;

@Service
public class ExportJsonServiceImpl extends ExportServiceImpl implements ExportService {

    private final ObjectMapper objectMapper;

    protected ExportJsonServiceImpl(CityClient cityClient, FileService fileService, ObjectMapper objectMapper) {
        super(cityClient, fileService);
        this.objectMapper = objectMapper;
    }

    @Override
    protected byte[] write(List<Export> exports) throws Exception {
        return objectMapper.writeValueAsBytes(exports);
    }

    @Override
    public Format getFormat() {
        return Format.JSON;
    }
}
