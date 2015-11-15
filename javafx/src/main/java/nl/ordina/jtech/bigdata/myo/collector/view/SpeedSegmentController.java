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

import eu.hansolo.enzo.sixteensegment.SixteenSegment;
import javafx.application.Platform;

import java.io.IOException;

/**
 * Listen on port for connection and send recieved data to a set of SixteenSegment display elements
 */
public class SpeedSegmentController {

    private SixteenSegment[] segments;

    public SpeedSegmentController(SixteenSegment... segments) {
        this.segments = segments;
        startSpeedSegment();
        setSegment("NOT.CON.");
    }

    private void startSpeedSegment() {
        try {
            FeedbackDataStream feedbackDataStream = new FeedbackDataStream();
            feedbackDataStream.speedProperty().addListener((obs, oldSpeed, newSpeed) -> {
                Platform.runLater(() -> setSegment(newSpeed));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String setSegment(final String charData) {
        String processData = charData;
        if (charData.length() < segments.length) {
            processData = charData + "         ";
        }
        for (int i = 0, y = 0; i < segments.length; i++, y++) {
            boolean hasDot = false;
            if (y + 2 <= charData.length()) {
                hasDot = processData.substring(y + 1, y + 2).equals(".");
            }
            segments[i].setCharacter(processData.substring(y, y + 1));

            if (hasDot) {
                segments[i].setDotOn(true);
                y++;
            } else {
                segments[i].setDotOn(false);
            }
        }

        return charData;
    }

}
