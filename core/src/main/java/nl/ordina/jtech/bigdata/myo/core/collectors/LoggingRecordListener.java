package nl.ordina.jtech.bigdata.myo.core.collectors;

import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

/**
 * Created by pieter on 10/21/2015.
 */
public class LoggingRecordListener implements RecordListener {
    private boolean running = false;

    @Override
    public void newRecord(MyoDataRecord record) {
        if (running) {
            System.out.println(record);
        }
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void dump(String key) {
        //Nothing to do
    }
}
