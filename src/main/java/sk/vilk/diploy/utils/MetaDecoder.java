package sk.vilk.diploy.utils;

import sk.vilk.diploy.AnnotationManager;
import sk.vilk.diploy.Properties;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

public class MetaDecoder implements MetaConstants {

    public static HashMap<Class, Properties> decode(byte[] bytes) throws IllegalArgumentException {
        ClassBuffer byteBuffer = new ClassBuffer(bytes);
        HashMap<Class, Properties> classes = new HashMap<>();

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

            classes.put(decodedClass, properties);
        }

        return classes;
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

    private static byte decodeFields(ClassBuffer byteBuffer, Class<?> decodedClass, Properties properties) throws IllegalArgumentException {
        byte columnId = byteBuffer.get();

        while (columnId != RELATIONS_BYTES_START && columnId != ENTITY_BYTES_END) {
            byte fieldType = byteBuffer.get();
            Field field = decodeField(byteBuffer, decodedClass);

            if (!equalFieldTypes(fieldType, field)) {
                throw new IllegalArgumentException("Type of field '" + field.getName() + "' has changed!");
            }

            properties.addField(field);
            columnId = byteBuffer.get();
        }

        return columnId;
    }

    private static void decodeRelations(ClassBuffer byteBuffer, Class<?> decodedClass, Properties properties) {
        byte columnId = byteBuffer.get();

        while (columnId != ENTITY_BYTES_END) {
            byte relationType = byteBuffer.get();
            String relationName = decodeString(byteBuffer);

            Field field = AnnotationManager.getField(relationName, decodedClass);
            Class relationClass = getRelationClass(relationType);
            Annotation annotation = AnnotationManager.getAnnotation(field, relationClass);

            if (!equalRelationTypes(relationType, annotation)) {
                throw new IllegalArgumentException("Relation of field '" + field.getName() + "' has changed!");
            }

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


    private static boolean equalFieldTypes(byte fieldType, Field field) {
        switch (field.getType().getSimpleName()) {
            case "String":
                return fieldType == BYTE_VALUE_STRING;
            case "Integer":
                return fieldType == BYTE_VALUE_INTEGER;
            default:
                return false;
        }
    }

    private static boolean equalRelationTypes(byte relationType, Annotation annotation) {
        switch (annotation.annotationType().getSimpleName()) {
            case "OneToOne":
                return relationType == ONE_TO_ONE;
            case "OneToMany":
                return relationType == ONE_TO_MANY;
            case "ManyToMany":
                return relationType == MANY_TO_MANY;
            default:
                return false;
        }
    }

}
