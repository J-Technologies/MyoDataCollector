package nl.ordina.jtech.bigdata.myo.core.csv;

import nl.ordina.jtech.bigdata.myo.core.DataCollectionWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by pieter on 9/11/2015.
 */
public class CsvDataCollectionWriter implements DataCollectionWriter {

    public static final Logger LOGGER = LogManager.getLogger(CsvDataCollectionWriter.class);

    public static final String SEPARATOR = "-";
    private final CsvDataCollector dataCollector;
    private final String outputDirectory;

    private enum DateType {OK, BAD}

    public CsvDataCollectionWriter(CsvDataCollector dataCollector, String outputDirectory) {
        this.dataCollector = dataCollector;
        this.outputDirectory = outputDirectory;
        Path path = Paths.get(outputDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(Paths.get(outputDirectory));
            } catch (IOException e) {
                throw new RuntimeException("Unable create output", e);
            }
        }
    }

    @Override
    public void writeOkData() {
        writeData(DateType.OK);
    }

    @Override
    public void writeBadData() {
        writeData(DateType.BAD);
    }

    private void writeData(final CsvDataCollectionWriter.DateType type) {
        try {
            Path path = Paths.get(outputDirectory);
            if (!Files.exists(path)) {
                Files.createDirectories(Paths.get(outputDirectory));
            }
            Path tempFile = Files.createTempFile(path, type + SEPARATOR, ".csv");
            Files.write(tempFile, dataCollector.getCollectedData(), Charset.defaultCharset(), StandardOpenOption.WRITE);
            dataCollector.reset();
            LOGGER.info("created File: {}" + tempFile);
        } catch (IOException e) {
            LOGGER.error("Error writing output file{}", e.getMessage(), e);
        }
    }


}
