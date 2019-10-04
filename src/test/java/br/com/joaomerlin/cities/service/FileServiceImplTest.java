package br.com.joaomerlin.cities.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.joaomerlin.cities.CitiesApplicationTests;
import br.com.joaomerlin.cities.service.impl.FileServiceImpl;

public class FileServiceImplTest extends CitiesApplicationTests {

    @Autowired
    private FileServiceImpl service;

    @Test
    public void storeTest() {
        File store = null;
        try {
            store = service.store("test.txt", "test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(store.exists());

        String output = null;
        try {
            output = new String(new FileInputStream(store).readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("test", output);

        store.delete();
    }

    @Test
    public void getTest() {
        try {
            service.store("test.txt", "test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = service.get("test.txt");

        String output = null;
        try {
            output = new String(new FileInputStream(file).readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("test", output);

        file.delete();
    }
}
