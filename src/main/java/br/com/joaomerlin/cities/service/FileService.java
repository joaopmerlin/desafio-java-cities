package br.com.joaomerlin.cities.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    InputStream get(String name);

    File store(String name, byte[] bytes) throws IOException;
}
