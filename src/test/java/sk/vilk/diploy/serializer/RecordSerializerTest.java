package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.entities.SimpleEntity;
import sk.vilk.diploy.record.Attribute;
import sk.vilk.diploy.record.Header;
import sk.vilk.diploy.record.Record;
import sk.vilk.diploy.record.SerialType;
import sk.vilk.diploy.serializers.RecordSerializer;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RecordSerializerTest {
    private OutputByteBuffer outputBuffer;
    private RecordSerializer serializer;

    @BeforeMethod
    public void setup() {
        serializer = new RecordSerializer();
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void typeTest() {
        Assert.assertEquals(Record.class, serializer.type());
    }

    @Test
    public void simpleTest() {
        Record record = new Record();

        record.setId(UUID.randomUUID());

        Header header = new Header(2);
        header.setSerialTypes(List.of(SerialType.INTEGER, SerialType.BOOLEAN));
        record.setHeader(header);

        record.setValues(List.of(new Attribute<>(300), new Attribute<>(true)));

        serializer.serialize(outputBuffer, record);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Record deserialized = serializer.deserialize(inputBuffer);

        Assert.assertEquals(record, deserialized);
    }

    @Test
    public void entitySerializationTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity entity = new SimpleEntity(UUID.randomUUID(), "attribute 1", 1);
        Record record = Record.fromEntity(entity);

        System.out.println(record);

        serializer.serialize(outputBuffer, record);

        System.out.println(Arrays.toString(outputBuffer.getBytes()));

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Record deserialized = serializer.deserialize(inputBuffer);
        SimpleEntity newEntity = (SimpleEntity) Record.toEntity(deserialized, SimpleEntity.class);

        Assert.assertEquals(entity, newEntity);
    }
}
