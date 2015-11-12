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
import com.thalmic.myo.enums.StreamEmgType;
import eu.hansolo.enzo.gauge.OneEightyGauge;
import eu.hansolo.enzo.simpleindicator.SimpleIndicator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import nl.ordina.jtech.bigdata.myo.core.collectors.RecordListener;
import nl.ordina.jtech.bigdata.myo.core.collectors.impl.FileMyoDataCollector;
import nl.ordina.jtech.bigdata.myo.core.collectors.impl.JsonDataCollector;
import nl.ordina.jtech.bigdata.myo.core.collectors.impl.SocketServerCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Main application
 */
public class MainApplicationPresenter implements Initializable {

    public static final Logger LOGGER = LogManager.getLogger(MainApplicationPresenter.class);
    @FXML
    Label myoStatus;
    @FXML
    Label collectorStatus;
    @FXML
    Button startButton;
    @FXML
    Button stopButton;
    @FXML
    SimpleIndicator indicatorMyo;
    @FXML
    SimpleIndicator indicatorServer;
    @FXML
    ListView inboundConnections;
    @FXML
    OneEightyGauge gauge;
    List<String> connections = new ArrayList<>();
    ObservableList<String> observableList = FXCollections.observableList(connections);


    private boolean collecting = true;
    private Hub hub;
    private JsonDataCollector jsonDataCollector;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indicatorMyo.setIndicatorStyle(SimpleIndicator.IndicatorStyle.RED);
        indicatorMyo.setOn(true);
        indicatorServer.setIndicatorStyle(SimpleIndicator.IndicatorStyle.GREEN);
        indicatorServer.setOn(true);
        statusUpdate("");
        stopButton.setDisable(true);
        startButton.setDisable(true);
        hub = new Hub(this.getClass().getCanonicalName());
        inboundConnections.setItems(observableList);
        LOGGER.info("Initialized");
    }


    private void statusUpdate(final String status) {
        myoStatus.setText(status);
    }


    public void startAction() {
        collecting = true;
        jsonDataCollector.getListeners().stream().forEach(RecordListener::start);
        startButton.setDefaultButton(false);
        stopButton.setDefaultButton(true);
        toggleButtons();
        //todo must be killed by stop action
        new Thread(() -> {
            while (collecting) {
                hub.run(100);
            }
            System.out.println("collecting = " + collecting);
        }).start();
    }


    public void stopAction() {
        collecting = false;
        jsonDataCollector.getListeners().stream().forEach(RecordListener::stop);
        toggleButtons();
        jsonDataCollector.getListeners().stream().forEach(s -> s.dump("Data"));
        startButton.setDefaultButton(true);
        stopButton.setDefaultButton(false);
    }

    private void toggleButtons() {
        startButton.setDisable(collecting);
        stopButton.setDisable(!collecting);
    }

    public void connectMyo() {
        try {

            LOGGER.info("Connecting to Myo");
            updateMyoStatus("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                LOGGER.error("Unable to connect to Myo");
                throw new RuntimeException("Unable to find a Myo!");
            }

            myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);
            LOGGER.info("EMG Stream enabled");
            updateMyoStatus("Connected to a Myo armband!");


            jsonDataCollector = new JsonDataCollector();
            jsonDataCollector.addListsner(new FileMyoDataCollector("c:\\tmp\\myo"));
            jsonDataCollector.addListsner(new SocketServerCollector());

            hub.addListener(jsonDataCollector);
            LOGGER.info("Listener added");

            indicatorMyo.setIndicatorStyle(SimpleIndicator.IndicatorStyle.GREEN);
            startButton.setDisable(false);
            startButton.setDefaultButton(true);

            Task<ObservableList<String>> task = new Task<ObservableList<String>>() {
                @Override
                protected ObservableList<String> call() throws Exception {


                    while (true) {
                        List<RecordListener> collect = jsonDataCollector.getListeners().stream().filter(s -> s instanceof SocketServerCollector).collect(Collectors.toList());
                        List<String> connections = collect.stream().map(s -> ((SocketServerCollector) s).channels).flatMap(c -> c.stream().map(f -> mapRemoteAddress(f))).collect(Collectors.toList());
                        updateValue(FXCollections.observableList(connections));
                        Thread.sleep(10);

                    }


                }
            };


            Task<Boolean> booleanTask = new Task<Boolean>() {
                @Override
                protected Boolean call() throws Exception {
                    while (true) {
                        List<RecordListener> collect = jsonDataCollector.getListeners().stream().filter(s -> s instanceof SocketServerCollector).collect(Collectors.toList());
                        long count = collect.stream().map(s -> ((SocketServerCollector) s).channels).flatMap(Collection::stream).filter(AsynchronousSocketChannel::isOpen).count();
                        updateValue(count > 0 ? Boolean.TRUE : Boolean.FALSE);
                        Thread.sleep(10);
                    }

                }
            };

            Task<SimpleIndicator.IndicatorStyle> stringTask =  new Task<SimpleIndicator.IndicatorStyle>() {

                @Override
                protected SimpleIndicator.IndicatorStyle call() throws Exception {
                    while (true) {
                        List<RecordListener> collect = jsonDataCollector.getListeners().stream().filter(s -> s instanceof SocketServerCollector).collect(Collectors.toList());
                        long count = collect.stream().map(s -> ((SocketServerCollector) s).channels).flatMap(Collection::stream).filter(AsynchronousSocketChannel::isOpen).count();

                        updateValue(count > 0 ? SimpleIndicator.IndicatorStyle.RED : SimpleIndicator.IndicatorStyle.GREEN);
                        Thread.sleep(10);
                    }
                }
            };

            inboundConnections.itemsProperty().bind(task.valueProperty());
            indicatorServer.onProperty().bind(booleanTask.valueProperty());


            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();

            Thread thread2 = new Thread(booleanTask);
            thread2.setDaemon(true);
            thread2.start();

            Thread thread3 = new Thread(stringTask);
            thread3.setDaemon(true);
            thread3.start();


        } catch (Exception e) {
            e.printStackTrace();
            updateMyoStatus("Error: " + e.getMessage());
        }
    }

    public void connectAnalyzer() {

    }

    private void updateMyoStatus(final String msg) {
        myoStatus.setText("Myo: " + msg);
    }

    private String mapRemoteAddress(AsynchronousSocketChannel channel) {
        try {
            return channel.getRemoteAddress().toString();
        } catch (IOException e) {
            return "Unknown connection";
        }
    }

}
