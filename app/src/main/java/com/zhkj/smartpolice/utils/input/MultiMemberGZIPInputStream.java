package com.zhkj.smartpolice.utils.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.GZIPInputStream;

public class MultiMemberGZIPInputStream extends GZIPInputStream {
    private MultiMemberGZIPInputStream parent;
    private MultiMemberGZIPInputStream child;
    private int size;
    private boolean eos;

    public MultiMemberGZIPInputStream(InputStream in, int size) throws IOException {
        // Wrap the stream in a PushbackInputStream…
        super(new PushbackInputStream(in, size), size);
        this.size = size;
    }

    public MultiMemberGZIPInputStream(InputStream in) throws IOException {
        // Wrap the stream in a PushbackInputStream…
        super(new PushbackInputStream(in, 1024));
        this.size = -1;
    }

    private MultiMemberGZIPInputStream(MultiMemberGZIPInputStream parent) throws IOException {
        super(parent.in);
        this.size = -1;
        this.parent = parent.parent == null ? parent : parent.parent;
        this.parent.child = this;
    }

    private MultiMemberGZIPInputStream(MultiMemberGZIPInputStream parent, int size) throws IOException {
        super(parent.in, size);
        this.size = size;
        this.parent = parent.parent == null ? parent : parent.parent;
        this.parent.child = this;
    }

    public int read(byte[] inputBuffer, int inputBufferOffset, int inputBufferLen) throws IOException {

        if (eos) {
            return -1;
        }
        if (this.child != null)
            return this.child.read(inputBuffer, inputBufferOffset, inputBufferLen);

        int charsRead = super.read(inputBuffer, inputBufferOffset, inputBufferLen);
        if (charsRead == -1) {
            int n = inf.getRemaining() - 8;
            if (n > 0) {
                ((PushbackInputStream) this.in).unread(buf, len - n, n);
            } else {
                byte[] b = new byte[1];
                int ret = in.read(b, 0, 1);
                if (ret == -1) {
                    eos = true;
                    return -1;
                } else
                    ((PushbackInputStream) this.in).unread(b, 0, 1);
            }
            MultiMemberGZIPInputStream child;
            if (this.size == -1) {
                child = new MultiMemberGZIPInputStream(this);
            } else {
                child = new MultiMemberGZIPInputStream(this, this.size);
            }
            int result = child.read(inputBuffer, inputBufferOffset, inputBufferLen);
            child.close();
            return result;
        } else
            return charsRead;
    }
}
