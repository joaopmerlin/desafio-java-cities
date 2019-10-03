package br.com.joaomerlin.cities.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.service.ExportService;
import br.com.joaomerlin.cities.service.FileService;

public abstract class ExportServiceImpl implements ExportService {

    private final CityClient cityClient;
    private final FileService fileService;

    protected ExportServiceImpl(CityClient cityClient, FileService fileService) {
        this.cityClient = cityClient;
        this.fileService = fileService;
    }

    @Override
    public void export(OutputStream outputStream) {
        String fileName = String.format("export_%s.%s", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE), getFormat().getExt());
        File file = fileService.get(fileName);
        if (file.exists()) {
            try {
                new FileInputStream(file).transferTo(outputStream);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            byte[] bytes = write(buildExport());
            fileService.store(fileName, bytes);
            outputStream.write(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract byte[] write(List<Export> exports) throws Exception;

    protected List<Export> buildExport() {
        return cityClient.findAll().parallelStream()
                .map(Export::fromCity)
                .collect(Collectors.toList());
    }
}
