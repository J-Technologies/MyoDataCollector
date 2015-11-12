/*
 * Copyright (c) 2014 Pieter van der Meer (pieter_at_elucidator_nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package nl.ordina.jtech.bigdata.myo.collector.view;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import nl.ordina.jtech.bigdata.myo.core.collectors.RecordObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter on 11/11/2015.
 */
public class DataCollectManager {
    public static final Logger LOGGER = LogManager.getLogger(DataCollectManager.class);
    private final Hub hub;
    private Myo myo;
    private boolean connected;
    private boolean collecting;
    private List<RecordObserver> recordObservers = new ArrayList<>();


    public DataCollectManager() {
        this.hub = new Hub(this.getClass().getCanonicalName());
        LOGGER.debug("Created instance for Myo Hub");
    }

    public void connect() {
        connected = true;
        //TODO Need the myo to test
//        LOGGER.info("Connecting to Myo");
//        myo = hub.waitForMyo(10000);
//
//        if (myo == null) {
//            LOGGER.error("Unable to connect to Myo");
//            throw new RuntimeException("Unable to find a Myo!");
//        }
//
//        myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);
//        LOGGER.info("EMG Stream enabled");
    }

    public void disconnect() {
        connected = false;
    }

    public boolean isConnected() {
        return connected;
    }

    public void start() {
        //TODO Start collecting
        collecting = true;
    }

    public void stop() {
        //TODO Stop collecting
        collecting = false;
    }

    public SparkChannelStatus connected2Spark() {
        //TODO Check for connection from spark to send data
        if (connected && collecting) {
            return SparkChannelStatus.SENDING;
        }

        if (connected && !collecting) {
            return SparkChannelStatus.CONNECTED;
        }

        return SparkChannelStatus.DISCONNECTED;
    }
}
