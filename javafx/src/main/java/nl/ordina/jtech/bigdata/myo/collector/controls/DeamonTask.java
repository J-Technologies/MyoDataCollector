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

import javafx.concurrent.Task;

import java.util.function.Supplier;

/**
 * Created by pieter on 11/12/2015.
 */
public class DeamonTask<T> extends Task<T> {

    private final Supplier<T> supplier;
    private Thread thread;

    public DeamonTask(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public DeamonTask<T> start() {
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        return this;
    }

    public DeamonTask<T> stop() {
        thread.interrupt();
        return this;
    }

    @Override
    protected T call() throws Exception {
        while (true) {
            try {
                updateValue(supplier.get());
                Thread.sleep(25);
            } catch (InterruptedException e) {
                System.out.println("Stopped?");
                return null;
            }
        }
    }
}
