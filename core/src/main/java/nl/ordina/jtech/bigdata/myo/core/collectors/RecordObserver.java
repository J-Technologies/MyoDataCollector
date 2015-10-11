package nl.ordina.jtech.bigdata.myo.core.collectors;

import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter on 10/11/2015.
 */
public interface RecordObserver {
    List<RecordListener> listeners = new ArrayList();

    default void addObserver(final RecordListener listener) {
        listeners.add(listener);
    }

    default void removeObserver(final RecordListener listener) {
        listeners.remove(listener);
    }

    default void emit(final MyoDataRecord record) {
        listeners.stream().forEach(s -> s.newRecord(record));
    }
}
