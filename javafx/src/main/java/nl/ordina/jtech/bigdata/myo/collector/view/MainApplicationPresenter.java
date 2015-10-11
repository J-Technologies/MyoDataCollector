package nl.ordina.jtech.bigdata.myo.collector.view;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.StreamEmgType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.ordina.jtech.bigdata.myo.core.collectors.FileMyoDataCollector;
import nl.ordina.jtech.bigdata.myo.core.collectors.JsonDataCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pieter on 9/18/2015.
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
    private boolean collecting = true;
    private Hub hub;
    private FileMyoDataCollector fileMyoDataCollector;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusUpdate("");
        stopButton.setDisable(true);
        startButton.setDisable(true);
        hub = new Hub(this.getClass().getCanonicalName());
        LOGGER.info("Initialized");
    }


    private void statusUpdate(final String status) {
        myoStatus.setText(status);
    }


    public void startAction() {
        fileMyoDataCollector.start();
        collecting = true;
        startButton.setDefaultButton(false);
        stopButton.setDefaultButton(true);
        toggleButtons();
        new Thread(() -> {
            while (collecting) {
                hub.run(100);
            }
        }).start();
    }

    public void stopAction() {
        fileMyoDataCollector.stop();
        collecting = false;
        toggleButtons();
        fileMyoDataCollector.dump("Data");
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
//            DeviceListenerImpl deviceListener = new DeviceListenerImpl(dataCollectors);
//            deviceListener.addListener(dataCollectors);

            JsonDataCollector jsonDataCollector = new JsonDataCollector();
            fileMyoDataCollector = new FileMyoDataCollector("c:\\tmp\\myo");
            jsonDataCollector.addObserver(fileMyoDataCollector);

            hub.addListener(jsonDataCollector);
//            hub.addListener(deviceListener);
            LOGGER.info("Listener added");

            startButton.setDisable(false);
            startButton.setDefaultButton(true);

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
