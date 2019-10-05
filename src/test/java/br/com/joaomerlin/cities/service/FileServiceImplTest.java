package br.com.joaomerlin.cities.service;

import br.com.joaomerlin.cities.CitiesApplicationTests;
import br.com.joaomerlin.cities.service.impl.FileServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileServiceImplTest extends CitiesApplicationTests {

    @Autowired
    private FileServiceImpl service;

    @Test
    public void storeTest() throws IOException {
        File store = service.store("test.txt", "test".getBytes());

        assertTrue(store.exists());

        String output = new String(new FileInputStream(store).readAllBytes());

        assertEquals("test", output);

        store.delete();
    }

    @Test
    public void getTest() throws IOException {
        File store = service.store("test.txt", "test".getBytes());

        InputStream stream = service.get("test.txt");

        String output = new String(stream.readAllBytes());

        assertEquals("test", output);

        store.delete();
    }
}
