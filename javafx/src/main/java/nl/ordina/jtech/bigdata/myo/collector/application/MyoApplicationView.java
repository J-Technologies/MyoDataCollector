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

package nl.ordina.jtech.bigdata.myo.collector.application;

import com.airhacks.afterburner.views.FXMLView;

import javax.annotation.PostConstruct;

/**
 * Created by pieter on 11/11/2015.
 */
public class MyoApplicationView extends FXMLView {

    @PostConstruct
    public void init() {
        System.out.println("Tower.init()");
    }
}
