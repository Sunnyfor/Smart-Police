package com.zhkj.smartpolice.utils.input;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MultiInputStreamHelper {

    private static String getEncoding(Map<String, String> responseHeaders) {
        String header1 = responseHeaders.get("Accept-Encoding");
        String header2 = responseHeaders.get("Content-Encoding");
        StringBuilder encoding = new StringBuilder();
        if (header1 != null) {
            encoding.append(header1);
        }

        if (header2 != null) {
            encoding.append(header2);
        }

        return encoding.toString();
    }

    public static byte[] encrypt(byte[] old) {
        for (int i = 0; i < old.length; i++) {
            old[i] = (byte) ~old[i];
        }
        return old;
    }

    public static InputStream getInputStream(Map<String, String> responseHeaders, InputStream in) throws IOException {
        return getInputStream(getEncoding(responseHeaders), in);
    }

    public static InputStream getInputStream(String encoding, InputStream in) throws IOException {

        if (TextUtils.isEmpty(encoding)) {
            return in;
        }

        if (encoding.contains(IEncoding.GZIP.encoding) && encoding.contains(IEncoding.ESENC.encoding)) {
            return new MultiGzipInputStream(in);
        }

        if (encoding.contains(IEncoding.GZIP.encoding)) {
            return new MultiMemberGZIPInputStream(in);
        }

        if (encoding.contains(IEncoding.ESENC.encoding)) {
            return new UtilInputStream(in);
        }

        return in;
    }

    public enum IEncoding {
        NONE(""), ESENC("esenc"), GZIP("gzip"), ESENCGZIP("gzip,esenc");

        public final String encoding;

        IEncoding(String encoding) {
            this.encoding = encoding;
        }
    }

}
