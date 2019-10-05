package br.com.joaomerlin.cities.service.impl;

import br.com.joaomerlin.cities.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${files.store.dir:#{systemProperties['java.io.tmpdir'] + systemProperties['file.separator'] + 'cities'}}")
    private String fileDir;

    @PostConstruct
    public void init() {
        new File(fileDir).mkdirs();
    }

    @Override
    public InputStream get(String name) {
        File file = new File(fileDir + File.separator + name);
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public File store(String name, byte[] bytes) throws IOException {
        File file = new File(fileDir + File.separator + name);
        new FileOutputStream(file).write(bytes);
        return file;
    }
}
