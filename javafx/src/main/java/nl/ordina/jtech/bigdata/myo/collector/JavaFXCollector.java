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

package nl.ordina.jtech.bigdata.myo.collector;

import com.airhacks.afterburner.injection.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.ordina.jtech.bigdata.myo.collector.view.MyoApplicationView;

/**
 * Created by pieter on 9/18/2015.
 */
public class JavaFXCollector extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        MyoApplicationView applicationView = new MyoApplicationView();
        Scene scene = new Scene(applicationView.getView());
        stage.setTitle("Myo Data collector");
        final String uri = getClass().getResource("app.css").toExternalForm();
        scene.getStylesheets().add(uri);
        stage.setScene(scene);
        stage.show();


//        MainApplicationView appView = new MainApplicationView();
//        Scene scene = new Scene(appView.getView());
//        stage.setTitle("Myo Data Collector");
//        final String uri = getClass().getResource("app.css").toExternalForm();
//
//        stage.setScene(scene);
//        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
}
