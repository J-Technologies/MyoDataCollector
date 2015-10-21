/*
 *
 *  * Copyright (c) 2014 Pieter van der Meer (pieter_at_elucidator_nl)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package nl.ordina.jtech.bigdata.myo.collector.myo;

/**
 * Created by pieter on 10/2/2015.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

public class TaskTest extends Application {
    public static void main(String[] args) throws Exception { launch(args); }
    public void start(final Stage stage) throws Exception {
        final Label statusLabel = new Label("Status");
        final Button runButton = new Button("Run");
        final ListView<String> peopleView = new ListView<String>();
        peopleView.setPrefSize(220, 162);
        final ProgressBar progressBar = new ProgressBar();
        progressBar.prefWidthProperty().bind(peopleView.prefWidthProperty());

        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                final Task task = new Task<ObservableList<String>>() {
                    @Override protected ObservableList<String> call() throws InterruptedException {
                        updateMessage("Finding friends . . .");
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(200);
                            updateProgress(i+1, 10);
                        }
                        updateMessage("Finished.");
                        return FXCollections.observableArrayList("John", "Jim", "Geoff", "Jill", "Suki");
                    }
//          @Override protected void done() {
//            super.done();
//            System.out.println("This is bad, do not do this, this thread " + Thread.currentThread() + " is not the FXApplication thread.");
//            runButton.setText("Voila!");
//          }
                };

                statusLabel.textProperty().bind(task.messageProperty());
                runButton.disableProperty().bind(task.runningProperty());
                peopleView.itemsProperty().bind(task.valueProperty());
                progressBar.progressProperty().bind(task.progressProperty());
                task.stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            System.out.println("This is ok, this thread " + Thread.currentThread() + " is the JavaFX Application thread.");
                            runButton.setText("Voila!");
                        }
                    }
                });

                new Thread(task).start();
            }
        });

        final VBox layout =
                VBoxBuilder.create().spacing(8).children(
                        VBoxBuilder.create().spacing(5).children(
                                HBoxBuilder.create().spacing(10).children(
                                        runButton,
                                        statusLabel).build(),
                                progressBar
                        ).build(),
                        peopleView
                ).build();
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding:10; -fx-font-size: 16;");
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }
}