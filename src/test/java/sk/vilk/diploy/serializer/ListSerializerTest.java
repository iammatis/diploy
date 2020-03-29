package sk.vilk.diploy.serializer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.vilk.diploy.buffer.InputBuffer;
import sk.vilk.diploy.buffer.InputByteBuffer;
import sk.vilk.diploy.buffer.OutputByteBuffer;
import sk.vilk.diploy.serializers.ListSerializer;

import java.util.List;

public class ListSerializerTest {
    private OutputByteBuffer outputBuffer;

    @BeforeMethod
    public void setup() {
        outputBuffer = new OutputByteBuffer();
    }

    @Test
    public void integerListTest() {
        ListSerializer<Integer> serializer = new ListSerializer<>(Integer.class);
        List<Integer> list = List.of(1, Integer.MIN_VALUE, 2, Integer.MAX_VALUE, 3);
        serializer.serialize(outputBuffer, list);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        List<Integer> deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, list);
    }

    @Test
    public void stringListTest() {
        ListSerializer<String> serializer = new ListSerializer<>(String.class);
        List<String> list = List.of("1", "Integer.MIN_VALUE", "2", "Integer.MAX_VALUE", "3");
        serializer.serialize(outputBuffer, list);

        InputBuffer inputBuffer = new InputByteBuffer(outputBuffer.getBytes());
        List<String> deserialized = serializer.deserialize(inputBuffer);
        Assert.assertEquals(deserialized, list);
    }
}
