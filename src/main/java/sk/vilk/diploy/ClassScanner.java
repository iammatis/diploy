package sk.vilk.diploy;

import sk.vilk.diploy.file.MetaFileManager;
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
 * Scans new Entity and its fields when not found int classes Map
 */
public class ClassScanner {

    private HashMap<Class, Properties> classes;
    private static final List<Class<?>> relationClasses = List.of(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class);

    ClassScanner() {
        classes = new HashMap<>();
    }

    void scan(Class entityClass) {
        if (!contains(entityClass)) {
            Properties properties = getProperties(entityClass);
            byte[] bytes = MetaEncoder.encode(properties);
            MetaFileManager.append(bytes);
            classes.put(entityClass, properties);
        }
    }

    private Properties getProperties(Class entityClass) {
        Properties properties = new Properties();

        Field[] fields = entityClass.getDeclaredFields();
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

        return properties;
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
        return classes.containsKey(clazz);
    }

    public Properties getProperties(Object entity) {
        return classes.get(entity.getClass());
    }

    public void initiate() {
        byte[] bytes = MetaFileManager.read();

        if (bytes != null) {
            this.classes = MetaDecoder.decode(bytes);
        }
    }
}
