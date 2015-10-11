package nl.ordina.jtech.bigdata.myo.collector.myo;

import com.thalmic.myo.*;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.WarmupResult;
import com.thalmic.myo.enums.WarmupState;
import com.thalmic.myo.enums.XDirection;
import eu.hansolo.enzo.common.Section;
import eu.hansolo.enzo.common.SectionBuilder;
import eu.hansolo.enzo.vumeter.VuMeter;
import nl.ordina.jtech.bigdata.myo.core.EulerAngles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Timer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by pieter on 10/2/2015.
 */
public class RawDataCollector implements DeviceListener {

    private long lastTimestamp = 0;
    private MyoDataRecord dataRecord = new MyoDataRecord();


    @Override
    public void onPair(Myo myo, long l, FirmwareVersion firmwareVersion) {

    }

    @Override
    public void onUnpair(Myo myo, long l) {

    }

    @Override
    public void onConnect(Myo myo, long l, FirmwareVersion firmwareVersion) {

    }

    @Override
    public void onDisconnect(Myo myo, long l) {

    }

    @Override
    public void onArmSync(Myo myo, long l, Arm arm, XDirection xDirection, WarmupState warmupState) {

    }

    @Override
    public void onArmUnsync(Myo myo, long l) {

    }

    @Override
    public void onUnlock(Myo myo, long l) {

    }

    @Override
    public void onLock(Myo myo, long l) {

    }

    @Override
    public void onPose(Myo myo, long l, Pose pose) {

    }

    @Override
    public void onOrientationData(Myo myo, long l, Quaternion quaternion) {
        dataRecord.setQuaternion(quaternion);
        dataRecord.setEulerAngles(new EulerAngles(quaternion));
    }

    @Override
    public void onAccelerometerData(Myo myo, long l, Vector3 vector3) {
        dataRecord.setAcceleratorMeter(vector3);
    }

    @Override
    public void onGyroscopeData(Myo myo, long l, Vector3 vector3) {
        dataRecord.setGyro(vector3);
    }

    @Override
    public void onRssi(Myo myo, long l, int i) {

    }

    @Override
    public void onBatteryLevelReceived(Myo myo, long l, int i) {

    }

    @Override
    public void onEmgData(Myo myo, long l, byte[] bytes) {
        if (l != lastTimestamp) {
            lastTimestamp = l;
            dataRecord.setEmg(bytes);
            System.out.println(dataRecord);
            dataRecord.reset(l, bytes);
        }
    }


    @Override
    public void onWarmupCompleted(Myo myo, long l, WarmupResult warmupResult) {
    }
}
