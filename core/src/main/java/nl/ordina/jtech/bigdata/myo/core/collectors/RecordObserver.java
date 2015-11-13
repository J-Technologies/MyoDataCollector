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

package nl.ordina.jtech.bigdata.myo.core.collectors;

import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer
 */
public interface RecordObserver {
    /**
     * All registered listeners
     */
    List<RecordListener> listeners = new ArrayList();

    /**
     * Add a Observer
     * @param listener subject
     */
    default void addListener(final RecordListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove listener
     * @param listener subject
     */
    default void removeListener(final RecordListener listener) {
        listeners.remove(listener);
    }

    /**
     * Get All listeners
     * @return
     */
    default List<RecordListener> getListeners() {
        return listeners;
    }

    /**
     * Emit a event
     * @param record event data
     */
    default void emit(final MyoDataRecord record) {
        listeners.stream().forEach(s -> s.newRecord(record));
    }
}
