package br.com.joaomerlin.cities.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.joaomerlin.cities.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Value("${files.store.dir:#{systemProperties['java.io.tmpdir'] + systemProperties['file.separator'] + 'cities'}}")
    private String fileDir;

    @PostConstruct
    public void init() {
        new File(fileDir).mkdirs();
    }

    @Override
    public File get(String name) {
        return new File(fileDir + File.separator + name);
    }

    @Override
    public File store(String name, byte[] bytes) throws IOException {
        File file = new File(fileDir + File.separator + name);
        new FileOutputStream(file).write(bytes);
        return file;
    }
}
