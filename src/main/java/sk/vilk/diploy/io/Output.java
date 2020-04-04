package sk.vilk.diploy.io;

import sk.vilk.diploy.record.Record;

import java.util.List;

public interface Output {

    void writeRecord(Record record);

    void writeRecords(List<Record> records);
}
