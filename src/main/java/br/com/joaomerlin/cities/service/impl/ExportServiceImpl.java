package br.com.joaomerlin.cities.service.impl;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import br.com.joaomerlin.cities.client.CityClient;
import br.com.joaomerlin.cities.model.Export;
import br.com.joaomerlin.cities.service.ExportService;

public abstract class ExportServiceImpl implements ExportService {

    private final CityClient cityClient;

    protected ExportServiceImpl(CityClient cityClient) {
        this.cityClient = cityClient;
    }

    @Override
    public void export(OutputStream outputStream) {
        // TODO: 03/10/2019 cache
        write(outputStream);
    }

    protected abstract void write(OutputStream outputStream);

    protected List<Export> buildExport() {
        return cityClient.findAll().parallelStream()
                .map(e -> Export.builder()
                        .stateId(e.getMicroRegion().getMesoRegion().getState().getId())
                        .stateAcronym(e.getMicroRegion().getMesoRegion().getState().getAcronym())
                        .regionName(e.getMicroRegion().getMesoRegion().getState().getRegion().getName())
                        .cityName(e.getName())
                        .mesoRegionName(e.getMicroRegion().getMesoRegion().getName())
                        .build())
                .collect(Collectors.toList());
    }
}
