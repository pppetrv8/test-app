package com.example.pppetrv.testapplication.util;

import com.example.pppetrv.testapplication.net.simplexml.CustomSimpleXmlConverterFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import timber.log.Timber;

public class TestUtils {

    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    public void log(String log) {
        System.out.println(log);
    }

    public Gson getGson() {
        return gson;
    }


    public <T> T readJson(String fileName, Class<T> inClass) {
        return gson.fromJson(readString(fileName), inClass);
    }

    public <T> T readXml(String fileName, Class<T> inClass) throws Exception {
        Persister persister = new Persister();
        return persister.read(inClass, readString(fileName));
    }

    public String readString(String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            int size = stream.available();
            byte[] buffer = new byte[size];
            int result = stream.read(buffer);
            return new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
