package com.example.pppetrv.testapplication.net.simplexml;

import com.example.pppetrv.testapplication.util.Constants;

import org.simpleframework.xml.Serializer;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class WinXmlResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Class<T> cls;
    private final Serializer serializer;
    private final boolean strict;

    WinXmlResponseBodyConverter(Class<T> cls, Serializer serializer, boolean strict) {
        this.cls = cls;
        this.serializer = serializer;
        this.strict = strict;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        try {
            Charset charset = Charset.forName(Constants.WIN_1251);
            WinReader reader = new WinReader(value.source(), charset);
            T read = serializer.read(cls, reader, strict);
            if (read == null) {
                throw new IllegalStateException("Could not deserialize body as " + cls);
            }
            return read;
        } catch (RuntimeException | IOException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            value.close();
        }
    }
}
