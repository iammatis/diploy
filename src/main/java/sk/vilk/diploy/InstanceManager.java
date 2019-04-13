package sk.vilk.diploy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class InstanceManager {

    private static final Logger logger = LoggerFactory.getLogger(InstanceManager.class);

    public static Object createNewInstance(Object entity, Properties properties) {
        try {
            // TODO: Entity class needs to have an empty constructor for this to work!
            Object newInstance = entity.getClass().getDeclaredConstructor().newInstance();
            for (Field field : properties.getRegularFields()) {
                field.setAccessible(true);
                Object fieldValue = field.get(entity);
                field.set(newInstance, fieldValue);
            }

            Field idField = properties.getIdField();
            idField.setAccessible(true);
            Object idFieldValue = idField.get(entity);
            idField.set(newInstance, idFieldValue);

            return newInstance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
