package sk.vilk.diploy;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;

class AnnotationManager {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationManager.class);

    static String getIdValue(Object object) {
        try {
            String attributeName = getIdName(object);

            Object idValue = FieldUtils.readDeclaredField(object, attributeName, true);
            return idValue != null ? idValue.toString() : null;
        } catch (IllegalAccessException e) {
            logger.error("Can't access id field in given entity class when getting id", e);
        } catch (Exception e) {
            logger.error("No id field found in entity class when getting id", e);
        }
        return null;
    }

    private static String getIdName(Object object) throws Exception {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(object.getClass(), javax.persistence.Id.class);

        // fields need to consist of exactly one element => There can't be more than one Id.class in Entity class
        if (fields.length != 1) {
            // TODO: Implement custom exception ?
            throw new Exception("No id field found!");
        }

        return fields[0].getName();
    }

    static void setIdValue(Object object, Object id) {
        try {
            String attributeName = getIdName(object);

            FieldUtils.writeDeclaredField(object, attributeName, id, true);
        } catch (IllegalAccessException e) {
            logger.error("Can't access id field in given entity class when setting id", e);
        } catch (Exception e) {
            logger.error("No id field found in entity class when setting id", e);
        }
    }

}
