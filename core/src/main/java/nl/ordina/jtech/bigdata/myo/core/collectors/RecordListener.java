package nl.ordina.jtech.bigdata.myo.core.collectors;

import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

/**
 * Created by pieter on 10/11/2015.
 */
public interface RecordListener {
    void newRecord(final MyoDataRecord listener);

    void start();

    void stop();

    void dump(final String key);

}
