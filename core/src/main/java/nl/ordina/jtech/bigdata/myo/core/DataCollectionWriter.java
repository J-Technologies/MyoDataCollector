package nl.ordina.jtech.bigdata.myo.core;

/**
 * Created by pieter on 10/2/2015.
 */
@FunctionalInterface
public interface DataCollectionWriter {

    void write(final String data);
}
