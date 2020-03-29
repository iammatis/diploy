package sk.vilk.diploy.record;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Record {
    private UUID id;
    // "Pointer" to next entity in file
    private long next;
    private Header header;
    // TODO: LockMode?
    private List<Attribute<?>> values;

    public Record() {
        values = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getNext() {
        return next;
    }

    public void setNext(long next) {
        this.next = next;
    }

    public List<Attribute<?>> getValues() {
        return values;
    }

    public void setValues(List<Attribute<?>> values) {
        this.values = values;
    }

    public void addValue(Attribute<?> value) {
        this.values.add(value);
    }

    public static Record fromEntity(Object object) throws IllegalAccessException {
        Record record = new Record();
        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getDeclaredFields();
        Header header = new Header(fields.length - 1);
        record.setHeader(header);

        for (Field field: fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();

            field.setAccessible(true);
            if(isId(annotations)) {
                record.setId((UUID) field.get(object));
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

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", next=" + next +
                ", header=" + header +
                ", values=" + values +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public boolean equals(Object otherRecord) {
        return otherRecord.getClass() == Record.class &&
                ((Record) otherRecord).header.equals(this.header) &&
                ((Record) otherRecord).next == this.next &&
                ((Record) otherRecord).id.equals(this.id) &&
                ((Record) otherRecord).values.equals(this.values);
    }
}
