package sk.vilk.diploy.utils;

import org.apache.commons.lang3.tuple.Pair;
import sk.vilk.diploy.Column;
import sk.vilk.diploy.Properties;
import sk.vilk.diploy.RelationalColumn;

import java.lang.annotation.Annotation;
import java.nio.charset.Charset;

public class MetaEncoder implements MetaConstants {

    public static byte[] encode(Properties properties) {
        Integer byteSize = calculateTotalByteSize(properties);

        ClassBuffer byteBuffer = new ClassBuffer(byteSize);

        // Class Name
        int entityClassLength = properties.getEntityClass()
                .getName()
                .getBytes(Charset.forName(CHARSET)).length;
        byteBuffer.putByte((byte) entityClassLength);
        byteBuffer.putString(properties.getEntityClass().getName());

        // Class id Field
        int idFieldLength = properties.getIdField()
                .getName()
                .getBytes(Charset.forName(CHARSET)).length;
        byteBuffer.putByte((byte) idFieldLength);
        byteBuffer.putString(properties.getIdField().getName());

        // Class ID, Generate UUID ?
        byteBuffer.putInt(1);

        // Fields
        if (!properties.getPlainObjects().isEmpty()) byteBuffer.putByte(FIELDS_BYTES_START);
        for (Column plainObject : properties.getPlainObjects()) {
            byteBuffer.putByte(plainObject.getId());
            byteBuffer.putField(plainObject.getField());
        }

        // Relations
        if (!properties.getRelations().isEmpty()) byteBuffer.putByte(RELATIONS_BYTES_START);
        for (RelationalColumn relation : properties.getRelations()) {
            byteBuffer.putByte(relation.getId());
            byteBuffer.putField(relation.getAnnotation(), relation.getField());
        }

        byteBuffer.putByte(ENTITY_BYTES_END);

        return byteBuffer.array();
    }

    private static Integer calculateTotalByteSize(Properties properties) {
        // Lengths are needed to be able to allocate ByteBuffer
        int entityClassLength = properties.getEntityClass()
                .getName()
                .getBytes(Charset.forName(CHARSET)).length;

        int idFieldLength = properties.getIdField()
                .getName()
                .getBytes(Charset.forName(CHARSET)).length;

        int fieldsByteSize = properties.getPlainObjects()
                .stream()
                .mapToInt(column -> column.getField().getName().getBytes(Charset.forName(CHARSET)).length)
                .sum();

        int relationsByteSize = properties.getRelations()
                .stream()
                .mapToInt(pair -> pair.getField().getName().getBytes(Charset.forName(CHARSET)).length)
                .sum();

        return
                /* One byte to store className length and rest for class name itself */
                1 + entityClassLength +
                1 + idFieldLength +
                /* 4 bytes to store unique class Id */
                CLASS_ID_SIZE +
                /* One byte to store field flag 127
                   Field bytes itself
                   Size of field name before every field (therefore fields.size()),
                   it is also doubled because there is field type before every field name
                 */
                1 + fieldsByteSize + properties.getPlainObjects().size() * 3 +
                /* One byte to store relation flag 126
                   Relation bytes itself
                   Size of relation name before every (therefore relations.size()),
                   it is also doubled because there is relation type before every relation name
                 */
                1 + relationsByteSize + properties.getRelations().size() * 3 +
                /* Last byte containing end of entity flag 128 */
                1;
    }
}
