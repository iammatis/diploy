package sk.vilk.diploy;

import sk.vilk.diploy.utils.MetaDecoder;
import sk.vilk.diploy.utils.MetaEncoder;

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

    void scanClass(Class entityClass) {
        if (!contains(entityClass)) {
            Field[] fields = entityClass.getDeclaredFields();
            Properties properties = new Properties();
            properties.setEntityClass(entityClass);

            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();

                Annotation annotation;
                if((annotation = isRelation(annotations)) != null) {
                    properties.addRelation(annotation, field);
                } else if (isId(annotations)) {
                    properties.setIdField(field);
                } else {
                    properties.addField(field);
                }
            }
            byte[] bytes = MetaEncoder.encode(properties);
            System.out.println(properties);
            System.out.println(MetaDecoder.decode(bytes));
            // TODO: Append to meta file
//            new MetaSerializer(properties).encode();
            entityClasses.put(entityClass, properties);
        }
    }

    public Properties getProperties(Object entity) {
        return entityClasses.get(entity.getClass());
    }

    private Annotation isRelation(Annotation[] declaredAnnotations) {
        for(Annotation annotation : declaredAnnotations) {
            if (relationClasses.contains(annotation.annotationType())) return annotation;
        }
        return null;
    }

    private boolean isId(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations).anyMatch(annotation ->  annotation.annotationType() == Id.class);
    }

    private boolean contains(Class clazz) {
        return entityClasses.containsKey(clazz);
    }
}
