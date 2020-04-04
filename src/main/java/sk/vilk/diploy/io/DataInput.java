package sk.vilk.diploy.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.record.Record;
import sk.vilk.diploy.serializers.UUIDSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DataInput extends AbstractIO implements Input {

    private static final Logger logger = LoggerFactory.getLogger(DataInput.class);
    private static final UUIDSerializer uuidSerializer = new UUIDSerializer();
    private InputByteBuffer inputBuffer;

    @Override
    public Record readRecord(long position) {
        try (
                FileInputStream fis = new FileInputStream(filename);
                BufferedInputStream bis = new BufferedInputStream(fis)
                ) {

            long skipped = bis.skip(position);
            if (skipped != position) {
                throw new IOException("Insufficient data when skipping to record");
            }


            byte[] uid = bis.readNBytes(16);

            byte[] sizeBytes = new byte[4];
            long skip = bis.skip(16);
            if (skip != 16) {
                throw new IOException("Incorrect data when skipping");
            }

            int read = bis.read(sizeBytes);

            if (read != 4) {
                throw new IOException("Incorrect data when reading next");
            }

            System.out.println(Arrays.toString(sizeBytes));
            InputByteBuffer buffer = new InputByteBuffer(sizeBytes);
            int size = buffer.readInt();
            System.out.println("size: " + size);
            System.out.println();
            byte[] recordBytes = new byte[size];
            int readBytes = bis.read(recordBytes);
            System.out.println("read bytes: " + readBytes);
            System.out.println("record bytes");
            System.out.println(Arrays.toString(recordBytes));
            buffer.reset(recordBytes);
            return serializer.deserialize(buffer);

        } catch (FileNotFoundException e) {
            logger.error("Database file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to read from database file " + filename, e);
        }

        return null;
    }

    @Override
    public List<Record> readRecords() {
        return null;
    }

    public Record findRecord(UUID uuid) {
        OutputByteBuffer out = new OutputByteBuffer(16);
        uuidSerializer.serialize(out, uuid);
        return findRecord(out.toBytes());
    }

    @Override
    public Record findRecord(byte[] uuid) {
        return findRecord(uuid, 0);
    }

    private Record findRecord(byte[] uuid, long skip) {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "r")){
            while (skip < dbSize) {
                raf.seek(skip);
                byte[] uuidBytes = new byte[16];
                int readUUID = raf.read(uuidBytes);

                if (readUUID != 16) {
                    throw new IOException("Error reading record uuid from file " + filename);
                }

                byte[] sizeB = new byte[4];
                int readSize = raf.read(sizeB);

                if (readSize != 4) {
                    throw new IOException("Error reading record size from file " + filename);
                }

                int size = ((sizeB[0] << 24) + (sizeB[1] << 16) + (sizeB[2] << 8) + (sizeB[3]));
                if (Arrays.equals(uuid, uuidBytes)) {
                    // read rest of record and return
                    byte[] recordB = new byte[size];
                    raf.seek(skip);
                    int recordRead = raf.read(recordB);

                    if (recordRead != size) {
                        throw new IOException("Error reading record from file " + filename);
                    }

                    inputBuffer = new InputByteBuffer(recordB);
                    return serializer.deserialize(inputBuffer);
                } else {
                    skip += size;
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Database file: " + filename + " was not found!", e);
        } catch (IOException e) {
            logger.error("An error occurred trying to read from database file " + filename, e);
        }

        return null;
    }
}

























