package nl.ordina.jtech.bigdata.myo.collector.myo;

import org.junit.Test;

/**
 * Created by pieter on 10/2/2015.
 */
public class RawDataCollectorTest {

    @Test
    public void testOnEmgData() throws Exception {
        RawDataCollector rawDataCollector = new RawDataCollector();

        byte[] bytes = {-10, 100, -23, -54};
        rawDataCollector.onEmgData(null,1, bytes);
    }
}