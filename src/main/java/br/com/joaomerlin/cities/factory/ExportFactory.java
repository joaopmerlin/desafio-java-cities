package br.com.joaomerlin.cities.factory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;

@Component
public class ExportFactory {

    private final Map<Format, ExportService> exportServices;

    public ExportFactory(List<ExportService> exportServices) {
        this.exportServices = exportServices.stream().collect(Collectors.toMap(ExportService::getFormat, e -> e));
    }

    public ExportService getService(Format format) {
        return exportServices.get(format);
    }
}
