package nl.ordina.jtech.bigdata.myo.collector.collectors;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import javafx.scene.control.TextArea;
import nl.ordina.jtech.bigdata.myo.core.AbstractDataCollector;
import nl.ordina.jtech.bigdata.myo.core.DataCollector;
import nl.ordina.jtech.bigdata.myo.core.EulerAngles;

/**
 * Created by pieter on 10/2/2015.
 */
public class TextAreaCollector extends AbstractDataCollector implements DataCollector {

    private long stamp = -1;

    @Override
    public void reset() {
        //Void
    }


    @Override
    public void dataAvailable(long timestamp, Quaternion quaternion, EulerAngles eulerAngles, Vector3 acceleratorMeter, Vector3 gyro) {
        String logLine = createLogLine(timestamp, quaternion, eulerAngles, acceleratorMeter, gyro);
    }

    private String createLogLine(long timestamp, Quaternion quaternion, EulerAngles eulerAngles, Vector3 acceleratorMeter, Vector3 gyro) {
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
        return builder.toString();
    }
}
