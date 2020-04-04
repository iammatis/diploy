package sk.vilk.diploy.io;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.entities.SimpleEntity;
import sk.vilk.diploy.record.Record;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataTest {

    private DataOutput output;
    private DataInput input;
    private SimpleEntity entity;

    @BeforeMethod
    public void setup() {
        output = new DataOutput();
        input = new DataInput();
        entity = new SimpleEntity(UUID.randomUUID(), "attribute", 1);

        Path resourceDirectory = Paths.get("src", "../");
        File file = new File(resourceDirectory.toString() + "/diploy.bin");
        file.delete();
    }

    @Test
    public void writeEntity() throws IllegalAccessException {
        Record record = Record.fromEntity(entity);

        output.writeRecord(record);

        Record newRecord = input.findRecord(record.getId());

        System.out.println(record);
        System.out.println(newRecord);
    }

    @Test
    public void findEntity() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity chosen = new SimpleEntity(UUID.randomUUID(), "attribute chosen", 1);

        List<Record> list = List.of(
                Record.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1)),
                Record.fromEntity(chosen),
                Record.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1))
        );

        for (Record record: list) {
            output.writeRecord(record);
        }

        Record foundRecord = input.findRecord(chosen.getId());
        SimpleEntity found = (SimpleEntity) Record.toEntity(foundRecord, SimpleEntity.class);

        Assert.assertEquals(chosen, found);
    }

    @Test
    public void bigTest() throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        SimpleEntity chosen = new SimpleEntity(UUID.randomUUID(), "attribute chosen", 1);

        int NUMBER = 10_000;

        List<Record> list = new ArrayList<>();
//        list.add(Record.fromEntity(chosen));
        for (int i = 0; i < NUMBER; i++) {
            list.add(Record.fromEntity(new SimpleEntity(UUID.randomUUID(), "attribute", 1)));
        }

        list.add(Record.fromEntity(chosen));

        long startWrite = System.nanoTime();
        for (Record record: list) {
            output.writeRecord(record);
        }
        long endWrite = System.nanoTime();
        double secondsWrite = (1.0 * (endWrite - startWrite)) / 1000000000;
        System.out.println("Wrote in: " + secondsWrite + "s");

        long startFind = System.nanoTime();
        Record foundRecord = input.findRecord(chosen.getId());
        SimpleEntity found = (SimpleEntity) Record.toEntity(foundRecord, SimpleEntity.class);
        long endFind = System.nanoTime();
        double secondsFind = (1.0 * (endFind - startFind)) / 1000000000;
        System.out.println("Found in: " + secondsFind + "s");

        Assert.assertEquals(chosen, found);
    }

}
