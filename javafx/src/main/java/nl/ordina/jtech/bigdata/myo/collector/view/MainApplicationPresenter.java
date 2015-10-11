package nl.ordina.jtech.bigdata.myo.collector.view;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.StreamEmgType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import nl.ordina.jtech.bigdata.myo.collector.collectors.RecordCountCollector;
import nl.ordina.jtech.bigdata.myo.collector.myo.RawDataCollector;
import nl.ordina.jtech.bigdata.myo.core.*;
import nl.ordina.jtech.bigdata.myo.core.csv.CsvDataCollectionWriter;
import nl.ordina.jtech.bigdata.myo.core.csv.CsvDataCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by pieter on 9/18/2015.
 */
public class MainApplicationPresenter implements Initializable {

    public static final Logger LOGGER = LogManager.getLogger(MainApplicationPresenter.class);

    private boolean collecting = true;
    public static final String HEADER_LINE = "timestamp;qx;qy;qz;pitch;roll;yaw;ax;ay;az;gx;gy;gz";


    @FXML
    Label myoStatus;
    @FXML
    Label collectorStatus;

    @FXML
    Button startButton;
    @FXML
    Button okButton;
    @FXML
    Button badButton;


    private Hub hub;
    private List<DataCollector> dataCollectors = new ArrayList<>();
    private List<DataCollectionWriter> dataCollectionWriters = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusUpdate("");

        CsvDataCollector csvDataCollector = new CsvDataCollector();
        dataCollectors.add(csvDataCollector);
        //dataCollectors.add(new TextAreaCollector(logArea));


        dataCollectors.add(new RecordCountCollector());
        dataCollectionWriters.add(new CsvDataCollectionWriter(csvDataCollector, "c:\\tmp\\myo"));

        hub = new Hub(this.getClass().getCanonicalName());
        LOGGER.info("Initialized");
    }


    private void statusUpdate(final String status) {
        myoStatus.setText(status);
    }

    public void okAction() {
        collecting = false;
        toggleButtons();
        dataCollectionWriters.stream().forEach(DataCollectionWriter::writeOkData);


    }

    public void badAction() {
        collecting = false;
        toggleButtons();
        dataCollectionWriters.stream().forEach(DataCollectionWriter::writeBadData);
    }

    public void startAction() {

        collecting = true;
        dataCollectors.stream().forEach(DataCollector::reset);
        toggleButtons();
        new Thread(() -> {
            while (collecting) {
                hub.run(100);
            }
        }).start();
    }

    private void toggleButtons() {
        startButton.setDisable(collecting);
        okButton.setDisable(!collecting);
        badButton.setDisable(!collecting);
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
            DeviceListenerImpl deviceListener = new DeviceListenerImpl();
            deviceListener.addListener(dataCollectors);
            hub.addListener(new RawDataCollector());
            hub.addListener(deviceListener);
            LOGGER.info("Listener added");

        } catch (Exception e) {
            updateMyoStatus("Error: " + e.getMessage());
        }
    }

    public void connectAnalyzer() {

    }

    private void updateMyoStatus(final String msg) {
        myoStatus.setText("Myo: " + msg);
    }

}
