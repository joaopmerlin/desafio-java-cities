package br.com.joaomerlin.cities.service;

import br.com.joaomerlin.cities.model.Format;

import java.io.OutputStream;

public interface ExportService {

    void export(OutputStream outputStream);

    Format getFormat();
}
