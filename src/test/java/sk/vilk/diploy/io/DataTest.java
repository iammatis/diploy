package sk.vilk.diploy.io;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.entities.SimpleEntity;
import sk.vilk.diploy.record.EntityTransform;
import sk.vilk.diploy.record.Record;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataTest {

    private DataOutput output;
    private DataInput input;

    @BeforeMethod
    public void setup() {
        output = new DataOutput();
        input = new DataInput();

        Path resourceDirectory = Paths.get("src", "../");
        File file = new File(resourceDirectory.toString() + "/diploy.bin");
        file.delete();
    }

    @Test
    public void findEntity() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity chosen = new SimpleEntity(UUID.randomUUID(), "attribute chosen", 1);

        List<Record> list = List.of(
                EntityTransform.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1)),
                EntityTransform.fromEntity(chosen),
                EntityTransform.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1))
        );

        for (Record record: list) {
            output.writeRecord(record);
        }

        Record foundRecord = input.findRecord(chosen.getId());
        SimpleEntity found = (SimpleEntity) EntityTransform.toEntity(foundRecord, SimpleEntity.class);

        Assert.assertEquals(chosen, found);
    }

    @Test
    public void bigSingleTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity chosen = new SimpleEntity(UUID.randomUUID(), "attribute chosen", 1);

        int NUMBER = 100;

        List<Record> list = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            list.add(EntityTransform.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1)));
        }

        list.add(EntityTransform.fromEntity(chosen));

        for (Record record: list) {
            output.writeRecord(record);
        }

        Record foundRecord = input.findRecord(chosen.getId());
        SimpleEntity found = (SimpleEntity) EntityTransform.toEntity(foundRecord, SimpleEntity.class);

        Assert.assertEquals(chosen, found);
    }

    @Test
    public void bigMultipleTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity chosen = new SimpleEntity(UUID.randomUUID(), "attribute chosen", 1);

        int NUMBER = 100;

        List<Record> list = new ArrayList<>();
        for (int i = 0; i < NUMBER; i++) {
            list.add(EntityTransform.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1)));
        }

        list.add(EntityTransform.fromEntity(chosen));

        output.writeRecords(list);

        Record foundRecord = input.findRecord(chosen.getId());
        SimpleEntity found = (SimpleEntity) EntityTransform.toEntity(foundRecord, SimpleEntity.class);

        Assert.assertEquals(chosen, found);
    }

}
