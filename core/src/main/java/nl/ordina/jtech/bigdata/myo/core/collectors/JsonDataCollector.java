/*
 *
 *  * Copyright (c) 2014 Pieter van der Meer (pieter_at_elucidator_nl)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package nl.ordina.jtech.bigdata.myo.core.collectors;

import com.thalmic.myo.*;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.WarmupResult;
import com.thalmic.myo.enums.WarmupState;
import com.thalmic.myo.enums.XDirection;
import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

/**
 * Created by pieter on 10/2/2015.
 */
public class JsonDataCollector implements DeviceListener, RecordObserver {

    private long lastTimestamp = 0;
    private MyoDataRecord dataRecord = new MyoDataRecord(-1, null);

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
        synchronized (dataRecord) {
            if (lastTimestamp == 0) {
                dataRecord = new MyoDataRecord(l, bytes);
            }
            if (l != lastTimestamp) {
                lastTimestamp = l;
                dataRecord.setEmg(bytes);
                emit(dataRecord);
                dataRecord = new MyoDataRecord(l, bytes);
            }
        }
    }


    @Override
    public void onWarmupCompleted(Myo myo, long l, WarmupResult warmupResult) {
    }
}
