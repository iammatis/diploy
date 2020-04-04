package sk.vilk.diploy.io;

import sk.vilk.diploy.record.Record;

import java.util.List;

public interface Input {

    Record readRecord(long position);

    List<Record> readRecords();

    Record findRecord(byte[] uuid);

}
