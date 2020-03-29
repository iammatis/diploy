package sk.vilk.diploy;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.entities.SimpleEntity;
import sk.vilk.diploy.record.Record;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class RecordTest {
    private SimpleEntity simpleEntity;

    @BeforeMethod
    public void setup() {
        simpleEntity = new SimpleEntity(UUID.randomUUID(), "attribute1 value", 1);
    }

    @Test
    public void serializeAndDeserializeTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Record record = Record.fromEntity(simpleEntity);

        SimpleEntity newEntity = (SimpleEntity) Record.toEntity(record, SimpleEntity.class);
        Assert.assertEquals(simpleEntity, newEntity);
    }
}