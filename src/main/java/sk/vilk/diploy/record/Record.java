package sk.vilk.diploy.record;

import java.util.*;

public class Record {
    private UUID id;
    // "Pointer" to next entity in file
    private int next;
    private Header header;
    // TODO: LockMode?
    private List<Attribute<?>> values;
    private List<Relation<?>> relations;

    public Record() {
        values = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
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

    public List<Relation<?>> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation<?>> relations) {
        this.relations = relations;
    }

    public void addRelation(Relation<?> relation) {
        this.relations.add(relation);
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

    @Override
    public boolean equals(Object otherRecord) {
        return otherRecord.getClass() == Record.class &&
                ((Record) otherRecord).header.equals(this.header) &&
                ((Record) otherRecord).next == this.next &&
                ((Record) otherRecord).id.equals(this.id) &&
                ((Record) otherRecord).values.equals(this.values);
    }
}
