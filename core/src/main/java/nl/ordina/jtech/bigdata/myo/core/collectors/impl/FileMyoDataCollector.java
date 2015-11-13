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

package nl.ordina.jtech.bigdata.myo.core.collectors.impl;

import nl.ordina.jtech.bigdata.myo.core.collectors.RecordListener;
import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Collector for writing the data to a file
 */
public class FileMyoDataCollector implements RecordListener {

    public static final String FILE_PREFIX = "MyoData-";
    private List<MyoDataRecord> collected = new ArrayList<>();
    private Path path;
    private boolean collecting = false;

    /**
     * Constructor
     * @param basePath base directory
     */
    public FileMyoDataCollector(final String basePath) {
        path = Paths.get(basePath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(Paths.get(basePath));
            } catch (IOException e) {
                throw new RuntimeException("Unable create output", e);
            }
        }
    }

    @Override
    public void newRecord(MyoDataRecord dataRecord) {
        if (collecting) {
            collected.add(dataRecord);
        }
    }

    @Override
    public void start() {
        collected.clear();
        collecting = true;
    }

    @Override
    public void stop() {
        collecting = false;
    }

    @Override
    public void dump() {
        try {
            Path tempFile = Files.createTempFile(path, FILE_PREFIX, ".json");
            List<String> tmp = new ArrayList<>(collected.size());
            collected.forEach(s -> tmp.add(s.toString()));
            Files.write(tempFile, tmp, Charset.defaultCharset(), StandardOpenOption.WRITE);
            collected.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
