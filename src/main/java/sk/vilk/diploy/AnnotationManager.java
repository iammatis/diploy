package sk.vilk.diploy;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

class AnnotationManager {

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
        for (Pair<Annotation, Field> pair : properties.getRelationFields()) {
            Field field = pair.getRight();
            Annotation annotation = pair.getLeft();
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(entity);

                if (annotation instanceof OneToOne) {
                    if (((OneToOne) annotation).mappedBy().equals("")) {
                        Object idOfAnnotatedField = getIdValue(properties, fieldValue);
                        entityWrapper.addRelation(new Relation(annotation, field.getName(), idOfAnnotatedField));
                    }
                } else if (annotation instanceof OneToMany) {
                    List<Object> listOfIds = new ArrayList<>();
                    // TODO: Force cast to List, could be anything else!
                    List list = (List) fieldValue;
                    for (Object value : list) {
                        listOfIds.add(getIdValue(properties, value));
                    }
                    entityWrapper.addRelation(new Relation(annotation, field.getName(), listOfIds));
                } else if (annotation instanceof ManyToOne) {
//                    if (((ManyToOne) annotation).mappedBy().equals("")) {
//                        Object idOfAnnotatedField = getIdValue(fieldValue);
//                        entityWrapper.addRelation(new ImmutablePair<>(annotation, idOfAnnotatedField));
//                    }
                } else {
//                    System.out.println("manytomany");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return entityWrapper;
    }

    static Object getFieldValue(String fieldName, Object entity) {
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
}
