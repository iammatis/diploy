package sk.vilk.diploy.utils;

import sk.vilk.diploy.*;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class EntityEncoder {

    public static void encode(Object entity) {
        Properties properties = ClassScanner.getProperties(entity);
        Integer byteSize = calculateTotalByteSize(entity);
        EntityBuffer entityBuffer = new EntityBuffer(byteSize);
//        List<ByteBuffer> bytes = new ArrayList<>();

        // TODO: Class Flag
        entityBuffer.putInt(Integer.MAX_VALUE);

        Object id = AnnotationManager.getFieldValue(properties.getIdField(), entity);
        byte[] bytes = UuidSerializer.encode((String) id);
        entityBuffer.putBytes(bytes);

        // TODO: Total Byte Size
        entityBuffer.putInt(Integer.MAX_VALUE);

        // TODO: Lock Mode
        entityBuffer.putByte(Byte.MAX_VALUE);

        for (Column column : properties.getPlainObjects()) {
            entityBuffer.putByte(column.getId());
            Field field = column.getField();
            AnnotationManager.getFieldValue(field, entity);
            entityBuffer.putField(column.getField());
        }

        for (RelationalColumn relationalColumn : properties.getRelations()) {
            entityBuffer.putByte(relationalColumn.getId());
            AnnotationManager.getFieldValue(relationalColumn.getField(), entity);
            entityBuffer.putField(relationalColumn.getAnnotation(), relationalColumn.getField());
        }

        // TODO: Entity end flag ?

        Class entityClass = entity.getClass();
    }

    public static Integer calculateTotalByteSize(Object entity) {
        return 1;
    }

}
