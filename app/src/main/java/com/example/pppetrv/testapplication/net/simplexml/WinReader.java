package com.example.pppetrv.testapplication.net.simplexml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.internal.Util;
import okio.BufferedSource;

public class WinReader extends Reader {
    private final BufferedSource source;
    private final Charset charset;

    private boolean closed;
    private Reader delegate;

    WinReader(BufferedSource source, Charset charset) {
        this.source = source;
        this.charset = charset;
    }

    @Override public int read(char[] cbuf, int off, int len) throws IOException {
        if (closed) throw new IOException("Stream closed");

        Reader delegate = this.delegate;
        if (delegate == null) {
            Charset charset = Util.bomAwareCharset(source, this.charset);
            delegate = this.delegate = new InputStreamReader(source.inputStream(), charset);
        }
        return delegate.read(cbuf, off, len);
    }

    @Override public void close() throws IOException {
        closed = true;
        if (delegate != null) {
            delegate.close();
        } else {
            source.close();
        }
    }
}
