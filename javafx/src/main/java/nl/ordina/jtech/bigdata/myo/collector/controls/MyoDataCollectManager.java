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

package nl.ordina.jtech.bigdata.myo.collector.controls;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.StreamEmgType;
import nl.ordina.jtech.bigdata.myo.core.collectors.RecordListener;
import nl.ordina.jtech.bigdata.myo.core.collectors.impl.DataRecordDeviceListener;
import nl.ordina.jtech.bigdata.myo.core.collectors.impl.FileMyoDataCollector;
import nl.ordina.jtech.bigdata.myo.core.collectors.impl.SocketServerCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by pieter on 11/11/2015.
 */
public class MyoDataCollectManager {
    public static final Logger LOGGER = LogManager.getLogger(MyoDataCollectManager.class);
    private final DataRecordDeviceListener dataRecordDeviceListener;
    private Hub myoHub;
    private Myo myo;
    private AtomicBoolean connected = new AtomicBoolean(false);
    private AtomicBoolean collecting = new AtomicBoolean(false);
    private Thread hubRunner;


    public MyoDataCollectManager() {

        dataRecordDeviceListener = new DataRecordDeviceListener();
        dataRecordDeviceListener.addListener(new SocketServerCollector());
    }

    public void addFileCollector(final String path) {
        dataRecordDeviceListener.addListener(new FileMyoDataCollector(path));
        LOGGER.info("Activated Collecting mode, storage to: {}", path);
    }

    private void initializeMyo() {
        myoHub = new Hub(MyoDataCollectManager.class.getCanonicalName());
        LOGGER.debug("Created instance for Myo Hub");
        connected.getAndSet(true);
        LOGGER.info("Connecting to Myo");
        myo = myoHub.waitForMyo(10000);

        if (myo == null) {
            LOGGER.error("Unable to connect to Myo");
            throw new RuntimeException("Unable to find a Myo!");
        }

        myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);
        LOGGER.info("EMG Stream enabled");
        myoHub.addListener(dataRecordDeviceListener);
        LOGGER.info("Added {} listeners to Myo.", dataRecordDeviceListener.getListeners().size());
    }

    public void connectMyo() {
        initializeMyo();
    }

    public void start() {
        collecting.getAndSet(true);
        dataRecordDeviceListener.getListeners().stream().forEach(RecordListener::start);
        hubRunner = new Thread(new HubRunner());
        hubRunner.setDaemon(true);
        hubRunner.start();

    }

    public void stop() {
        collecting.getAndSet(false);
        dataRecordDeviceListener.getListeners().stream().forEach(RecordListener::stop);
        dataRecordDeviceListener.getListeners().stream().forEach(RecordListener::dump);
        hubRunner.interrupt();
    }

    public SparkChannelStatus connected2Spark() {
        if (connected.get() && collecting.get()) {
            return SparkChannelStatus.SENDING;
        }

        if (connected.get() && !collecting.get()) {
            return SparkChannelStatus.CONNECTED;
        }

        return SparkChannelStatus.DISCONNECTED;
    }

    private class HubRunner implements Runnable {

        @Override
        public void run() {
            myoHub.addListener(dataRecordDeviceListener);
            LOGGER.info("Starting Hub runner");
            while (!Thread.currentThread().isInterrupted()) {
                myoHub.run(100);
            }
            LOGGER.info("Stopped Hub runner");
        }
    }
}
