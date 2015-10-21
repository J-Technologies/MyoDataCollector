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

package nl.ordina.jtech.bigdata.myo.core.collectors;

import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter on 10/11/2015.
 */
public interface RecordObserver {
    List<RecordListener> listeners = new ArrayList();

    default void addObserver(final RecordListener listener) {
        listeners.add(listener);
    }

    default void removeObserver(final RecordListener listener) {
        listeners.remove(listener);
    }

    default List<RecordListener> getListeners() {
        return listeners;
    }

    default void emit(final MyoDataRecord record) {
        listeners.stream().forEach(s -> s.newRecord(record));
    }
}
