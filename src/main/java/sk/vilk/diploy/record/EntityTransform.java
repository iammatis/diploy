package sk.vilk.diploy.record;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EntityTransform {

    private static final HashMap<Class<?>, Class<?>> RELATION_CLASSES = new HashMap<>();

    static {
        RELATION_CLASSES.put(OneToOne.class, OneToOne.class);
        RELATION_CLASSES.put(OneToMany.class, OneToMany.class);
        RELATION_CLASSES.put(ManyToOne.class, ManyToOne.class);
    }

    public static Record fromEntity(Object object) throws IllegalAccessException {
        Record record = new Record();
        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();
        Header header = new Header(fields.length - 1);
        record.setHeader(header);

        for (Field field: fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            Annotation annotation;
            field.setAccessible(true);
            if(isId(annotations)) {
                record.setId((UUID) field.get(object));
            } else if((annotation = isRelation(annotations)) != null) {
                Relation<?> relation = new Relation<>();
                record.addRelation(relation);
            } else {
                Object oj = field.get(object);
                header.addSerialType(oj.getClass());
                record.addValue(new Attribute<>(oj));
            }
        }

        return record;
    }

    public static Object toEntity(Record record, Class<?> clazz) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Object object = clazz.getDeclaredConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();
        List<Attribute<?>> values = record.getValues();
        int attributeIndex = 0;

        for (Field field : fields) {
            field.setAccessible(true);

            if (isId(field.getDeclaredAnnotations())) {
                field.set(object, record.getId());
                continue;
            }

            field.set(object, values.get(attributeIndex).getValue());
            attributeIndex++;
        }

        return object;
    }

    private static boolean isId(Annotation[] declaredAnnotations) {
        return Arrays.stream(declaredAnnotations).anyMatch(annotation ->  annotation.annotationType() == Id.class);
    }

    private static Annotation isRelation(Annotation[] declaredAnnotations) {
        for(Annotation annotation : declaredAnnotations) {
            if (RELATION_CLASSES.containsKey(annotation.annotationType())) return annotation;
        }
        return null;
    }
}
