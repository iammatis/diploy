package sk.vilk.diploy.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.record.Record;

import java.io.*;
import java.util.List;

public class DataOutput extends AbstractIO implements Output {

    private static final Logger logger = LoggerFactory.getLogger(DataOutput.class);

    @Override
    public void writeRecord(Record record) {
        OutputByteBuffer buffer = new OutputByteBuffer();
        serializer.serialize(buffer, record);
        writeBytes(buffer.toBytes());
    }

    @Override
    public void writeRecords(List<Record> records) {
        OutputByteBuffer buffer = new OutputByteBuffer();
        for (Record record: records) {
            serializer.serialize(buffer, record);
        }

        writeBytes(buffer.toBytes());
    }

    public void writeBytes(byte[] bytes) {
        try (
                FileOutputStream fos = new FileOutputStream(filename, true);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ){
            bos.write(bytes);

            updateSize(bytes.length);

        } catch (FileNotFoundException e) {
            logger.error("Database file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to save to database file " + filename, e);
        }
    }
}
