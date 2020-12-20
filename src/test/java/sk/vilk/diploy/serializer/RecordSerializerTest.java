package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.entities.SimpleEntity;
import sk.vilk.diploy.record.*;
import sk.vilk.diploy.serializers.RecordSerializer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
    public void singleEntityTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity entity = new SimpleEntity(UUID.randomUUID(), "attribute 1", 1);
        Record record = EntityTransform.fromEntity(entity);

        serializer.serialize(outputBuffer, record);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        Record deserialized = serializer.deserialize(inputBuffer);
        SimpleEntity newEntity = (SimpleEntity) EntityTransform.toEntity(deserialized, SimpleEntity.class);

        Assert.assertEquals(entity, newEntity);
    }

    @Test
    public void multipleEntitiesTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        List<SimpleEntity> list = List.of(
                new SimpleEntity(UUID.randomUUID(), "attribute 1", 1),
                new SimpleEntity(UUID.randomUUID(), "attribute 2", 2),
                new SimpleEntity(UUID.randomUUID(), "attribute 3", 3)
        );

        for (SimpleEntity entity: list) {
            Record record = EntityTransform.fromEntity(entity);
            serializer.serialize(outputBuffer, record);
        }

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());

        List<SimpleEntity> deserialized = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Record des = serializer.deserialize(inputBuffer);
            SimpleEntity newEntity = (SimpleEntity) EntityTransform.toEntity(des, SimpleEntity.class);
            deserialized.add(newEntity);
        }

        Assert.assertEquals(list, deserialized);
    }
}
