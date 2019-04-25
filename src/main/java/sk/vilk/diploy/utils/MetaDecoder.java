package sk.vilk.diploy.utils;

import sk.vilk.diploy.AnnotationManager;
import sk.vilk.diploy.Properties;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class MetaDecoder implements MetaConstants {

    public static List<Properties> decode(byte[] bytes) {
        ClassBuffer byteBuffer = new ClassBuffer(bytes);
        List<Properties> list = new ArrayList<>();

        while (byteBuffer.hasRemaining()) {
            Properties properties = new Properties();

            Class<?> decodedClass = decodedClass(byteBuffer);
            properties.setEntityClass(decodedClass);

            Field decodedIdField = decodeField(byteBuffer, decodedClass);
            properties.setIdField(decodedIdField);

            // Class Id
            Integer classId = byteBuffer.getInt();
            properties.setClassId(classId);

            byte flag = byteBuffer.get();
            // Fields
            if (flag == FIELDS_BYTES_START) {
                flag = decodeFields(byteBuffer, decodedClass, properties);
            }

            // Relations
            if (flag == RELATIONS_BYTES_START) {
                decodeRelations(byteBuffer, decodedClass, properties);
            }

            list.add(properties);
        }

        return list;
    }


    private static String decodeString(ClassBuffer byteBuffer) {
        byte size = byteBuffer.get();
        byte[] bytes = new byte[size];
        byteBuffer.get(bytes, 0, size);

        return new String(bytes);
    }

    private static Class<?> decodedClass(ClassBuffer byteBuffer) {
        String className = decodeString(byteBuffer);

        return AnnotationManager.getDecodedClass(className);
    }

    private static Field decodeField(ClassBuffer byteBuffer, Class<?> decodedClass) {
        String fieldName = decodeString(byteBuffer);

        return AnnotationManager.getField(fieldName, decodedClass);
    }

    private static byte decodeFields(ClassBuffer byteBuffer, Class<?> decodedClass, Properties properties) {
        byte columnId = byteBuffer.get();

        while (columnId != RELATIONS_BYTES_START && columnId != ENTITY_BYTES_END) {
            // TODO: Check field type ?
            byte fieldType = byteBuffer.get();
            Field field = decodeField(byteBuffer, decodedClass);
            properties.addField(field);

            columnId = byteBuffer.get();
        }

        return columnId;
    }

    private static void decodeRelations(ClassBuffer byteBuffer, Class<?> decodedClass, Properties properties) {
        byte columnId = byteBuffer.get();

        while (columnId != ENTITY_BYTES_END) {
            // TODO: Check relation type ?
            byte relationType = byteBuffer.get();
            String relationName = decodeString(byteBuffer);

            Field field = AnnotationManager.getField(relationName, decodedClass);
            // TODO field might be null! Might it be? Exception should be thrown
            Class relationClass = getRelationClass(relationType);
            Annotation annotation = AnnotationManager.getAnnotation(field, relationClass);

            properties.addRelation(annotation, field);

            columnId = byteBuffer.get();
        }
    }

    private static Class getRelationClass(byte relationType) {
        switch (relationType) {
            case ONE_TO_ONE:
                return OneToOne.class;
            case ONE_TO_MANY:
                return OneToMany.class;
            case MANY_TO_MANY:
                return ManyToMany.class;
        }
        return null;
    }

}
