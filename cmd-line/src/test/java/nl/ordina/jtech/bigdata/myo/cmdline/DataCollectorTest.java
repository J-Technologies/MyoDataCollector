package nl.ordina.jtech.bigdata.myo.cmdline;

import org.junit.Test;

import java.nio.file.FileSystems;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by pieter on 9/11/2015.
 */
public class DataCollectorTest {

    @Test
    public void xx() {
        Set<String> strings = FileSystems.getDefault().supportedFileAttributeViews();
        for (String string : strings) {
            System.out.println("string = " + string);
        }
    }

}