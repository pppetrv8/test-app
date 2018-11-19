package com.example.pppetrv.testapplication.net.simplexml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class CustomSimpleXmlConverterFactory extends Converter.Factory {

    private final Serializer serializer;
    private final boolean strict;

    /** Create an instance using a default {@link Persister} instance for conversion. */
    public static CustomSimpleXmlConverterFactory create() {
        return create(new Persister());
    }

    /** Create an instance using {@code serializer} for conversion. */
    public static CustomSimpleXmlConverterFactory create(Serializer serializer) {
        return new CustomSimpleXmlConverterFactory(serializer, true);
    }

    /** Create an instance using a default {@link Persister} instance for non-strict conversion. */
    public static CustomSimpleXmlConverterFactory createNonStrict() {
        return createNonStrict(new Persister());
    }

    /** Create an instance using {@code serializer} for non-strict conversion. */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static CustomSimpleXmlConverterFactory createNonStrict(Serializer serializer) {
        if (serializer == null) throw new NullPointerException("serializer == null");
        return new CustomSimpleXmlConverterFactory(serializer, false);
    }

    private CustomSimpleXmlConverterFactory(Serializer serializer, boolean strict) {
        this.serializer = serializer;
        this.strict = strict;
    }

    public boolean isStrict() {
        return strict;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        Class<?> cls = (Class<?>) type;
        return new WinXmlResponseBodyConverter<>(cls, serializer, strict);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        return new WinXmlRequestBodyConverter<>(serializer);
    }
}
