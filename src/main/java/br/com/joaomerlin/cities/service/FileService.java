package br.com.joaomerlin.cities.service;

import java.io.File;
import java.io.IOException;

public interface FileService {

    File get(String name);

    File store(String name, byte[] bytes) throws IOException;
}
