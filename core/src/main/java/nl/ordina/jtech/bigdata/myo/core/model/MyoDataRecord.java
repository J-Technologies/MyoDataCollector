

package nl.ordina.jtech.bigdata.myo.core.model;

import com.google.gson.Gson;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;

/**
 * Created by pieter on 10/11/2015.
 */
public class MyoDataRecord {

    public static final Gson gson = new Gson();
    private long timestamp;
    private byte[] emg;
    private Quaternion quaternion;
    private Quaternion normalizedQuaternion;
    private EulerAngles eulerAngles;
    private EulerAngles normalizedEulerAngles;
    private Vector3 acceleratorMeter;
    private Vector3 gyro;
    private Vector3 normalizedAcceleratorMeter;
    private Vector3 normalizedGyro;

    public MyoDataRecord(long timestamp, byte[] emg) {
        this.timestamp = timestamp;
        this.emg = emg;
    }

    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
        normalizedQuaternion = quaternion.normalized();
        eulerAngles = new EulerAngles(quaternion);
        normalizedEulerAngles = new EulerAngles(normalizedQuaternion);
    }

    public void setAcceleratorMeter(Vector3 acceleratorMeter) {
        this.acceleratorMeter = acceleratorMeter;
        this.normalizedAcceleratorMeter = acceleratorMeter.normalized();
    }


    public void setGyro(Vector3 gyro) {
        this.gyro = gyro;
        this.normalizedGyro = gyro.normalized();
    }

    public void setEmg(byte[] emg) {
        this.emg = emg;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    public void reset(final long timestamp, byte[] emgData) {
        quaternion = null;
        normalizedQuaternion = null;
        eulerAngles = null;
        normalizedEulerAngles = null;
        acceleratorMeter= null;
        normalizedAcceleratorMeter = null;
        gyro = null;
        normalizedGyro = null;
        emg = emgData;
        this.timestamp = timestamp;
    }
}
