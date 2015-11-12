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

import eu.hansolo.enzo.simpleindicator.SimpleIndicator;
import eu.hansolo.enzo.sixteensegment.SixteenSegment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.ordina.jtech.bigdata.myo.collector.view.task.DeamonTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by pieter on 11/11/2015.
 */
public class MyoApplicationPresenter implements Initializable {

    public static final Logger LOGGER = LogManager.getLogger(MyoApplicationPresenter.class);
    private final DataCollectManager collectManager = new DataCollectManager();
    @FXML
    private Label statusBar;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonStop;
    @FXML
    private Button buttonMyo;
    @FXML
    private SimpleIndicator indicatorMyo;
    @FXML
    private SimpleIndicator indicatorSpark;
    @FXML
    private SixteenSegment segment0;
    @FXML
    private SixteenSegment segment1;
    @FXML
    private SixteenSegment segment2;
    @FXML
    private SixteenSegment segment3;
    @FXML
    private SixteenSegment segment4;
    @FXML
    private SixteenSegment segment5;
    private SimpleObjectProperty<SimpleIndicator.IndicatorStyle> indicatorStyleSimpleObjectProperty = new SimpleObjectProperty<>(SimpleIndicator.IndicatorStyle.RED);
    private SixteenSegment[] segments = new SixteenSegment[6];
    private List<DeamonTask> tasks = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.debug("Initializing");
        updateStausBar("Staring");
        indicatorMyo.setIndicatorStyle(SimpleIndicator.IndicatorStyle.RED);
        indicatorMyo.setOn(true);
        indicatorSpark.setIndicatorStyle(SimpleIndicator.IndicatorStyle.GREEN);
        indicatorSpark.setOn(true);
        buttonStart.setDisable(true);
        buttonStop.setDisable(true);

        segments[0] = segment0;
        segments[1] = segment1;
        segments[2] = segment2;
        segments[3] = segment3;
        segments[4] = segment4;
        segments[5] = segment5;

        setSegment("DISCON.");

        indicatorSpark.indicatorStyleProperty().bind(indicatorStyleSimpleObjectProperty);
        tasks.add(new DeamonTask<Void>(() -> {
            indicatorStyleSimpleObjectProperty.set(collectManager.connected2Spark().getColor());
            return null;
        }).start());

        updateStausBar("Started correctly");
        LOGGER.debug("Done intitialized");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        tasks.stream().forEach(DeamonTask::stop);

    }

    private void updateStausBar(final String status) {
        statusBar.setText(status);
    }

    private void setSegment(final String charData) {
        for (int i = 0, y = 0; i < 6; i++, y++) {
            boolean hasDot = charData.substring(y + 1, y + 2).equals(".");
            segments[i].setCharacter(charData.substring(y, y + 1));

            if (hasDot) {
                segments[i].setDotOn(true);
                y++;
            } else {
                segments[i].setDotOn(false);
            }
        }
    }


    /**
     * Menu item storage location activated
     */
    public void actionStorageLocation() {
        LOGGER.debug("Menu storage location activated");
    }

    /**
     * Menu item Collect mode activated
     */
    public void actionCollectMode() {
        LOGGER.debug("Menu collect mode activated");
    }

    /**
     * Menu item About activated
     */
    public void actionAbout() {
        LOGGER.debug("Menu about activated");
    }

    /**
     * Button start pressed
     */
    public void actionStart() {
        LOGGER.debug("Start button pressed");
        buttonStart.setDisable(true);
        buttonStop.setDisable(false);
        buttonStop.setDefaultButton(true);
        collectManager.start();
    }

    /**
     * Button stop pressed
     */
    public void actionStop() {
        LOGGER.debug("Stop button pressed");
        buttonStart.setDisable(false);
        buttonStop.setDisable(true);
        buttonStart.setDefaultButton(true);
        collectManager.stop();
    }

    /**
     * Button Myo
     */
    public void actionMyo() {
        if (collectManager.isConnected()) {
            collectManager.disconnect();
            indicatorMyo.setIndicatorStyle(SimpleIndicator.IndicatorStyle.RED);
            indicatorMyo.setOn(true);
            buttonMyo.setText("Connect");
        } else {
            collectManager.connect();
            indicatorMyo.setIndicatorStyle(SimpleIndicator.IndicatorStyle.GREEN);
            indicatorMyo.setOn(true);
            buttonMyo.setText("Disconnect");
            buttonStart.setDisable(false);
            buttonStart.setDefaultButton(true);
            buttonStop.setDisable(true);
        }
    }
}
