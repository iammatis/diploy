package sk.vilk.diploy.io;

import sk.vilk.diploy.serializers.RecordSerializer;

import java.io.File;

public class AbstractIO {

    protected static final RecordSerializer serializer = new RecordSerializer();

    protected static final String filename = "diploy.bin";

    protected static long dbSize;

    static {
        File file = new File(filename);
        dbSize = file.length();
    }

    protected void updateSize(long size) {
        dbSize += size;
    }
}
