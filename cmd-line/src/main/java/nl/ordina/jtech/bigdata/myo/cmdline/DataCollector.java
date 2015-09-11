package nl.ordina.jtech.bigdata.myo.cmdline;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by pieter on 9/11/2015.
 */
public class DataCollector {

    private Quaternion quaternion;
    private EulerAngles eulerAngles;
    private Vector3 acceleroMeter;
    private Vector3 gyro;
    private long currentTimestamp;
    private List<String> collectedData = new Vector<>();
    public static final String HEADER_LINE = "timestamp;qx;qy;qz;pitch;roll;yaw;ax;ay;az;gx;gy;gz";

    public DataCollector() {
        collectedData.add(HEADER_LINE);
    }

    public void onOrientation(long timestamp, Quaternion quaternion) {
        this.quaternion = quaternion;
        eulerAngles = new EulerAngles(quaternion);
        doWrite(timestamp);
    }

    private void doWrite(long timestamp) {
        if (currentTimestamp == 0) {
            currentTimestamp = timestamp;
            return;
        }
        if (currentTimestamp < timestamp) {
            buildDataString();
            quaternion = null;
            eulerAngles = null;
            acceleroMeter = null;
            gyro = null;
            currentTimestamp = timestamp;
        }
    }

    private void buildDataString() {
        StringBuilder builder = new StringBuilder();

        builder.append(currentTimestamp).append(";");
        if (quaternion != null) {
            builder.append(quaternion.getX()).append(";")
                    .append(quaternion.getY()).append(";")
                    .append(quaternion.getZ()).append(";")
                    .append(quaternion.getW()).append(";")
                    .append(eulerAngles.getPitch()).append(";")
                    .append(eulerAngles.getRoll()).append(":")
                    .append(eulerAngles.getYaw()).append(";");
        } else {
            builder.append(";;;;;;;");
        }
        if (acceleroMeter != null) {

            builder.append(acceleroMeter.getX()).append(";")
                    .append(acceleroMeter.getY()).append(";")
                    .append(acceleroMeter.getZ()).append(";");
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
        collectedData.add(builder.toString());
    }

    public void onAcceleroMeter(long timestamp, Vector3 vector3) {
        this.acceleroMeter = vector3;
    }

    public void onGyro(long timestamp, Vector3 gyro) {
        this.gyro = gyro;
    }


    public void writeOkData() {
        writeData("c:\\tmp\\myo", "ok");
    }

    public void writeBadData() {
        writeData("c:\\tmp\\myo", "bad");
    }

    private void writeData(final String destPath, final String type) {
        try {

            Path path = Paths.get(destPath);
            if (!Files.exists(path)) {
                Files.createDirectories(Paths.get(destPath));
//

            }


            //Path tempDir = Files.createTempDirectory(destPath);

            Path tempFile = Files.createTempFile(path, type.toUpperCase() + "-", "" + ".csv");
            Files.write(tempFile, collectedData, Charset.defaultCharset(), StandardOpenOption.WRITE);
            collectedData.clear();

            buildDataString();
            System.out.println("created File: " + tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDirectory(final Path newDirectoryPath) {
        if (!Files.exists(newDirectoryPath)) {
            try {
                Files.createDirectory(newDirectoryPath);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
