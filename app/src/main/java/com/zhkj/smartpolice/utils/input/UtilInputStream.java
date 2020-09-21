package com.zhkj.smartpolice.utils.input;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilInputStream extends BufferedInputStream {

    public UtilInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int charsRead = super.read();
        byte[] b = new byte[]{((byte) charsRead)};
        MultiInputStreamHelper.encrypt(b);
        return b[0];
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        int charsRead = super.read(buffer);
        if (buffer.length > 0) {
            buffer = MultiInputStreamHelper.encrypt(buffer);
        }
        return charsRead;
    }

    @Override
    public int read(byte[] buffer, int offset, int byteCount) throws IOException {
        int charsRead = super.read(buffer, offset, byteCount);
        if (buffer.length > 0) {
            buffer = MultiInputStreamHelper.encrypt(buffer);
        }
        return charsRead;
    }

}
