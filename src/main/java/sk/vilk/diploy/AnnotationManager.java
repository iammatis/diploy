package sk.vilk.diploy;

import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class AnnotationManager {

    public AnnotationManager() {
    }

    public Object getAttributeValue(Object object, Class clazz) {

        String attributeName = getAttributeName(object, clazz);

        try {
            return FieldUtils.readDeclaredField(object, attributeName, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getAttributeName(Object object, Class clazz) {
//        FieldUtils.getFieldsListWithAnnotation(object.getClass(), clazz).isEmpty() // error
        Field[] fields = FieldUtils.getFieldsWithAnnotation(object.getClass(), clazz);
        return fields[0].getName();
    }

}
