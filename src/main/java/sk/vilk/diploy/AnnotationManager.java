package sk.vilk.diploy;

import org.apache.commons.lang3.reflect.FieldUtils;
import java.lang.reflect.Field;

public class AnnotationManager {

    public static String getIdValue(Object object) {

        String attributeName = getIdName(object);

        try {
            Object idValue = FieldUtils.readDeclaredField(object, attributeName, true);
            return idValue != null ? idValue.toString() : null;
        } catch (IllegalAccessException e) {
            // TODO: Throw exception ? No Id found
            e.printStackTrace();
        }
        return null;
    }

    private static String getIdName(Object object) {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(object.getClass(), javax.persistence.Id.class);
        /* TODO: If more than one field is returned => throw Exception (there can be only one Id field)
                 If none is returned => throw Exception (there has to be exactly one field)
         */
        return fields[0].getName();
    }

    public static void setIdValue(Object object, Object id) {
        String attributeName = getIdName(object);
        try {
            FieldUtils.writeDeclaredField(object, attributeName, id, true);
        } catch (IllegalAccessException e) {
            // TODO: Throw exception ? No Id found
            e.printStackTrace();
        }
    }

}
