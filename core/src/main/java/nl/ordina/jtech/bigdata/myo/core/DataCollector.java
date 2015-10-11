package nl.ordina.jtech.bigdata.myo.core;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;

/**
 * Created by pieter on 10/2/2015.
 */
public interface DataCollector {

    void onOrientation(long timestamp, Quaternion quaternion);

    void onAcceleratorMeter(long timestamp, Vector3 vector3);

    void onGyro(long timestamp, Vector3 gyro);

    void reset();
}
