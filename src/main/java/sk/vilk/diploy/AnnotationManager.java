package sk.vilk.diploy;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class AnnotationManager {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationManager.class);

    static String getIdValue(Properties classProperties, Object entity) {
        Field idField = classProperties.getIdField();
        Object idValue = getFieldValue(idField.getName(), entity);
        return idValue != null ? idValue.toString() : null;
    }

    static void setIdValue(Properties classProperties, Object entity, Object id) {
        Field idField = classProperties.getIdField();
        setFieldValue(idField.getName(), entity, id);
    }

    static EntityWrapper createEntityWrapper(Object entity, Properties properties) {
        EntityWrapper entityWrapper = new EntityWrapper();
        for (RelationalColumn relation : properties.getRelations()) {
            Field field = relation.getField();
            Annotation annotation = relation.getAnnotation();
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(entity);

                if (annotation instanceof OneToOne) {
                    if (((OneToOne) annotation).mappedBy().equals("")) {
                        if (fieldValue != null) {
                            Object idOfAnnotatedField = getIdValue(properties, fieldValue);
                            entityWrapper.addRelation(new Relation(annotation, field.getName(), idOfAnnotatedField));
                        }
                    }
                } else if (annotation instanceof OneToMany) {
                    List<Object> listOfIds = new ArrayList<>();
                    // TODO: Force cast to List, could be anything else!
                    List list = (List) fieldValue;
                    if (list != null) {
                        for (Object value : list) {
                            listOfIds.add(getIdValue(properties, value));
                        }
                        entityWrapper.addRelation(new Relation(annotation, field.getName(), listOfIds));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return entityWrapper;
    }

    public static Object getFieldValue(Field field, Object entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(String fieldName, Object entity) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch (NoSuchFieldException e) {
            logger.error("There is no field '"+ fieldName +"' in entity class '" + entity.getClass() + "' when getting field", e);
        } catch (IllegalAccessException e) {
            logger.error("Can't access field '"+ fieldName +"' in entity class '" + entity.getClass() + "' when getting field", e);
        }
        return null;
    }

    static void setFieldValue(String fieldName, Object entity, Object foreignEntity) {
        try {
            Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(entity, foreignEntity);
        } catch (NoSuchFieldException e) {
            logger.error("There is no field '"+ fieldName +"' in entity class '" + entity.getClass() + "' when setting field", e);
        } catch (IllegalAccessException e) {
            logger.error("Can't access field '"+ fieldName +"' in entity class '" + entity.getClass() + "' when setting field", e);
        }
    }

    public static Field getField(String fieldName, Class<?> decodedClass) {
        try {
            return decodedClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getDecodedClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Annotation getAnnotation(Field field, Class relationClass) {
        return field.getDeclaredAnnotation(relationClass);
    }
}
