package nl.ordina.jtech.bigdata.myo.core;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;

/**
 * Created by pieter on 10/2/2015.
 */
public abstract class AbstractDataCollector implements DataCollector {

    private Quaternion quaternion;
    private EulerAngles eulerAngles;
    private Vector3 acceleratorMeter;
    private Vector3 gyro;
    private long currentTimestamp;


    @Override
    public void onOrientation(long timestamp, Quaternion quaternion) {
        this.quaternion = quaternion;
        eulerAngles = new EulerAngles(quaternion);
        doWrite(timestamp);
    }

    @Override
    public void onAcceleratorMeter(long timestamp, Vector3 vector3) {
        this.acceleratorMeter = vector3;
//        doWrite(timestamp);
    }

    @Override
    public void onGyro(long timestamp, Vector3 gyro) {
        this.gyro = gyro;
//        doWrite(timestamp);
    }

    private void doWrite(long timestamp) {
        if (currentTimestamp == 0) {
            currentTimestamp = timestamp;
            return;
        }
        if (currentTimestamp < timestamp) {
            dataAvailable(timestamp, quaternion,eulerAngles, acceleratorMeter, gyro);
            quaternion = null;
            eulerAngles = null;
            acceleratorMeter = null;
            gyro = null;
            currentTimestamp = timestamp;
        }
    }

    @Override
    public abstract void reset();

    public abstract void dataAvailable(final long timestamp, final Quaternion quaternion, final EulerAngles eulerAngles, final Vector3 acceleratorMeter, final Vector3 gyro);
}
