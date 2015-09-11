package nl.ordina.jtech.bigdata.myo.cmdline;

import com.thalmic.myo.*;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.WarmupResult;
import com.thalmic.myo.enums.WarmupState;
import com.thalmic.myo.enums.XDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by pieter on 9/11/2015.
 */
public class DeviceListenerImpl implements DeviceListener {

    public static final Logger LOGGER = LogManager.getLogger(DeviceListenerImpl.class);
    private final DataCollector dataCollector;

    public DeviceListenerImpl(DataCollector dataCollector) {
        this.dataCollector = dataCollector;
    }

    @Override
    public void onPair(Myo myo, long l, FirmwareVersion firmwareVersion) {
        LOGGER.info("Paired {}", myo.toString());
    }

    @Override
    public void onUnpair(Myo myo, long l) {
        LOGGER.info("Unpaired {}", myo);
    }

    @Override
    public void onConnect(Myo myo, long l, FirmwareVersion firmwareVersion) {
        LOGGER.info("Connected {}", myo);
    }

    @Override
    public void onDisconnect(Myo myo, long l) {
        LOGGER.info("Disconneced {}", myo);
    }

    @Override
    public void onArmSync(Myo myo, long l, Arm arm, XDirection xDirection, WarmupState warmupState) {
        LOGGER.info("Arm synced: Myo:{}; arm:{}; xDirection:{}; warmupState:{}", myo, arm, xDirection, warmupState);
    }

    @Override
    public void onArmUnsync(Myo myo, long l) {
        LOGGER.info("Arm unsync {}", myo);
    }

    @Override
    public void onUnlock(Myo myo, long l) {
    LOGGER.info("Unlocked {}", myo);
    }

    @Override
    public void onLock(Myo myo, long l) {
        LOGGER.info("Locked {}", myo);
    }

    @Override
    public void onPose(Myo myo, long l, Pose pose) {
        LOGGER.info("Pose: myo:{}; Pose:{}", myo, pose);
    }

    @Override
    public void onOrientationData(Myo myo, long l, Quaternion quaternion) {
        LOGGER.trace("{}:Orientation:{}", l, quaternion);
        LOGGER.trace("{}:{}", l, new EulerAngles(quaternion));
        dataCollector.onOrientation(l, quaternion);
    }

    @Override
    public void onAccelerometerData(Myo myo, long l, Vector3 vector3) {
        LOGGER.trace("{}:Accelerometer:{}", l, vector3);
        dataCollector.onAcceleroMeter(l, vector3);
    }

    @Override
    public void onGyroscopeData(Myo myo, long l, Vector3 vector3) {
        LOGGER.trace("{}:Gyro:{}", l, vector3);
        dataCollector.onGyro(l, vector3);
    }

    @Override
    public void onRssi(Myo myo, long l, int i) {
        LOGGER.info("RSSI {};{}", myo, i);
    }

    @Override
    public void onBatteryLevelReceived(Myo myo, long l, int i) {
        LOGGER.info("Battery level {};{}", myo, i);
    }

    @Override
    public void onEmgData(Myo myo, long l, byte[] bytes) {
        LOGGER.trace("{}:EMG:{}", l, bytes);
    }

    @Override
    public void onWarmupCompleted(Myo myo, long l, WarmupResult warmupResult) {
        LOGGER.info("Warmup complete {}", warmupResult);
    }


}
