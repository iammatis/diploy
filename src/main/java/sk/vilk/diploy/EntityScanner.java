package sk.vilk.diploy;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EntityScanner {

    private HashMap<Class, Properties> entityClasses;
    private final List<Class<?>> relationClasses = List.of(OneToOne.class, OneToMany.class, ManyToOne.class, ManyToMany.class);

    public EntityScanner() {
        entityClasses = new HashMap<>();
    }

    public void scanClass(Object entity) {
        Class<?> clazz = entity.getClass();

        if (!contains(clazz)) {
            System.out.println("scanning");
            Field[] fields = clazz.getDeclaredFields();
            Properties properties = new Properties();

            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();

                if(isRelation(annotations)) {
                    properties.addRelationField(field);
                } else if (isId(annotations)) {
                    properties.setIdField(field);
                } else {
                    properties.addRegularField(field);
                }
            }
            entityClasses.put(entity.getClass(), properties);
        }
    }

    private boolean isRelation(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations).anyMatch(annotation -> relationClasses.contains(annotation.annotationType()));
    }

    private boolean isId(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations).anyMatch(annotation ->  annotation.annotationType() == Id.class);
    }

    public boolean contains(Class clazz) {
        return entityClasses.containsKey(clazz);
    }

    public HashMap<Class, Properties> getEntityClasses() {
        return entityClasses;
    }

    public void setEntityClasses(HashMap<Class, Properties> entityClasses) {
        this.entityClasses = entityClasses;
    }
}
