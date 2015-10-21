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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter on 10/11/2015.
 */
public class FileMyoDataCollector implements RecordListener {

    public static final String SEPARATOR = "-";
    private List<MyoDataRecord> collected = new ArrayList<>();
    private Path path;
    private boolean collecting = false;

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
    public void newRecord(MyoDataRecord record) {
        if (collecting) {
            collected.add(record);
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
    public void dump(String key) {
        try {
            Path tempFile = Files.createTempFile(path, key + SEPARATOR, ".json");
            List<String> tmp = new ArrayList<>(collected.size());
            collected.forEach(s -> tmp.add(s.toString()));
            Files.write(tempFile, tmp, Charset.defaultCharset(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
