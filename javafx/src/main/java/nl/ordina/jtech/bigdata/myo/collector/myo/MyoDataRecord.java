

package nl.ordina.jtech.bigdata.myo.collector.myo;

import com.google.gson.Gson;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import nl.ordina.jtech.bigdata.myo.core.EulerAngles;

/**
 * Created by pieter on 10/11/2015.
 */
public class MyoDataRecord {

    private long timestamp;
    private byte[] emg;
    private Quaternion quaternion;
    private EulerAngles eulerAngles;
    private Vector3 acceleratorMeter;
    private Vector3 gyro;

    public static final Gson gson = new Gson();


    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
        eulerAngles = new EulerAngles(quaternion);
    }

    public void setEulerAngles(EulerAngles eulerAngles) {
        this.eulerAngles = eulerAngles;
    }

    public void setAcceleratorMeter(Vector3 acceleratorMeter) {
        this.acceleratorMeter = acceleratorMeter;
    }

    public void setGyro(Vector3 gyro) {
        this.gyro = gyro;
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
        eulerAngles = null;
        acceleratorMeter= null;
        gyro = null;
        emg = emgData;
        this.timestamp = timestamp;
    }
}
