package br.com.joaomerlin.cities.service.impl;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;

@Service
public class ExportJsonServiceImpl extends ExportServiceImpl implements ExportService {

    private final ObjectMapper objectMapper;

    protected ExportJsonServiceImpl(CityClient cityClient, ObjectMapper objectMapper) {
        super(cityClient);
        this.objectMapper = objectMapper;
    }

    @Override
    protected void write(OutputStream outputStream) {
        try {
            outputStream.write(objectMapper.writeValueAsBytes(buildExport()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write json", e);
        }
    }

    @Override
    public Format getFormat() {
        return Format.JSON;
    }
}
