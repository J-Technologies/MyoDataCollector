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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pieter on 11/11/2015.
 */
public class MyoApplicationPresenter implements Initializable {

    public static final Logger LOGGER = LogManager.getLogger(MyoApplicationPresenter.class);

    @FXML
    Label statusBar;

    @FXML
    Button buttonStart;
    @FXML
    Button buttonStop;
    @FXML
    SimpleIndicator indicatorMyo;
    @FXML
    SimpleIndicator indicatorSpark;
    @FXML
    SixteenSegment segment0;
    @FXML
    SixteenSegment segment1;
    @FXML
    SixteenSegment segment2;
    @FXML
    SixteenSegment segment3;
    @FXML
    SixteenSegment segment4;
    @FXML
    SixteenSegment segment5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    }

    /**
     * Button stop pressed
     */
    public void actionStop() {
        LOGGER.debug("Stop button pressed");
    }
}
