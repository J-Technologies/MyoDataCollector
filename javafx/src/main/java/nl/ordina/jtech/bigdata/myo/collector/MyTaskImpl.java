package nl.ordina.jtech.bigdata.myo.collector;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import nl.ordina.jtech.bigdata.myo.core.DataCollector;
import nl.ordina.jtech.bigdata.myo.core.EulerAngles;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by pieter on 10/9/2015.
 */
public class MyTaskImpl extends Task<String> implements DataCollector{



    AtomicLong count;

    @Override
    protected String call() throws Exception {
        //updateMessage("" + count.get());
        for (int i = 0; i < 10; i++) {
            Thread.sleep(200);
            //updateMessage("" + count.get());

        }
        return "" + count.get();
    }

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
    }

    @Override
    public void onGyro(long timestamp, Vector3 gyro) {
        this.gyro = gyro;
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

    private void dataAvailable(long timestamp, Quaternion quaternion, EulerAngles eulerAngles, Vector3 acceleratorMeter, Vector3 gyro) {
        count.incrementAndGet();
    }


    @Override
    public void reset() {
        count.set(0);
    }
}
