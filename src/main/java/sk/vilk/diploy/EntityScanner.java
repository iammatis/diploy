package sk.vilk.diploy;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class scans entities and keeps their relationships, lockMode and entity itself
 * Scans new Entity and its fields when not found int entityClasses Map
 */
public class EntityScanner {

    private HashMap<Class, Properties> entityClasses;
    private final List<Class<?>> relationClasses = List.of(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class);

    EntityScanner() {
        entityClasses = new HashMap<>();
    }

    void scanClass(Object entity) {
        Class<?> clazz = entity.getClass();

        if (!contains(clazz)) {
            Field[] fields = clazz.getDeclaredFields();
            Properties properties = new Properties();

            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();

                if(isRelation(annotations)) {
                    // TODO: Danger zone -> Takes first element, might cause problems (more annotations ? no annotation ?)
                    Annotation annotation = annotations[0];
                    properties.addRelationField(annotation, field);
                } else if (isId(annotations)) {
                    properties.setIdField(field);
                } else {
                    properties.addRegularField(field);
                }
            }
            entityClasses.put(entity.getClass(), properties);
        }
    }

    Properties getProperties(Object entity) {
        return entityClasses.get(entity.getClass());
    }

    private boolean isRelation(Annotation[] declaredAnnotations) {
        // TODO: Should filter and return only relationship annotations not just true/false
        return Arrays.stream(declaredAnnotations).anyMatch(annotation -> relationClasses.contains(annotation.annotationType()));
    }

    private boolean isId(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations).anyMatch(annotation ->  annotation.annotationType() == Id.class);
    }

    private boolean contains(Class clazz) {
        return entityClasses.containsKey(clazz);
    }
}
