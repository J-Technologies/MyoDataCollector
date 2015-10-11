package nl.ordina.jtech.bigdata.myo.collector.myo;

import nl.ordina.jtech.bigdata.myo.core.collectors.JsonDataCollector;
import org.junit.Test;

/**
 * Created by pieter on 10/2/2015.
 */
public class JsonDataCollectorTest {

    @Test
    public void testOnEmgData() throws Exception {
        JsonDataCollector jsonDataCollector = new JsonDataCollector();

        byte[] bytes = {-10, 100, -23, -54};
        jsonDataCollector.onEmgData(null, 1, bytes);
    }
}