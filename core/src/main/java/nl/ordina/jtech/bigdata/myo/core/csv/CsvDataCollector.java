package nl.ordina.jtech.bigdata.myo.core.csv;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import nl.ordina.jtech.bigdata.myo.core.AbstractDataCollector;
import nl.ordina.jtech.bigdata.myo.core.DataCollector;
import nl.ordina.jtech.bigdata.myo.core.EulerAngles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by pieter on 9/11/2015.
 */
public class CsvDataCollector extends AbstractDataCollector implements DataCollector {

    private List<String> collectedData = new ArrayList<>();
    public static final String HEADER_LINE = "timestamp;qx;qy;qz;pitch;roll;yaw;ax;ay;az;gx;gy;gz";

    public CsvDataCollector() {
        collectedData.add(HEADER_LINE);
    }

    public List<String> getCollectedData() {
        return collectedData;
    }

    @Override
    public void reset() {
        synchronized (collectedData) {
            collectedData.clear();
            collectedData.add(HEADER_LINE);
        }
    }

    @Override
    public void dataAvailable(long timestamp, Quaternion quaternion, EulerAngles eulerAngles, Vector3 acceleratorMeter, Vector3 gyro) {
        StringBuilder builder = new StringBuilder();

        builder.append(timestamp).append(";");
        if (quaternion != null) {
            builder.append(quaternion.getX()).append(";")
                    .append(quaternion.getY()).append(";")
                    .append(quaternion.getZ()).append(";")
                    .append(quaternion.getW()).append(";")
                    .append(eulerAngles.getPitch()).append(";")
                    .append(eulerAngles.getRoll()).append(";")
                    .append(eulerAngles.getYaw()).append(";");
        } else {
            builder.append(";;;;;;;");
        }
        if (acceleratorMeter != null) {

            builder.append(acceleratorMeter.getX()).append(";")
                    .append(acceleratorMeter.getY()).append(";")
                    .append(acceleratorMeter.getZ()).append(";");
        } else {
            builder.append(";;;");
        }
        if (gyro != null) {
            builder.append(gyro.getX()).append(";")
                    .append(gyro.getY()).append(";")
                    .append(gyro.getZ()).append(";");
        } else {
            builder.append(";;;");
        }
        synchronized (collectedData) {
            collectedData.add(builder.toString());
        }
    }
}
