

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

package nl.ordina.jtech.bigdata.myo.core.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import nl.ordina.jtech.bigdata.myo.core.serializers.MyDataRecordSerializer;

/**
 *  Internal representation of a Myo record received.
 *  Its a aggragation of multiple data elements from the Myo
 */
public class MyoDataRecord {

    public static final Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(MyoDataRecord.class, new MyDataRecordSerializer());
        gson = builder.create();
    }
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

    @Override
    public String toString() {

        try {
            return gson.toJson(this);
        } catch (IllegalArgumentException e) {
            return "";
        }
//        StringBuilder builder = new StringBuilder();
//        builder.append("{");
//        builder.append("\"timestamp\":" + timestamp);
//
//        return builder.toString();
    }

    public void reset(final long timestamp, byte[] emgData) {
        quaternion = null;
        normalizedQuaternion = null;
        eulerAngles = null;
        normalizedEulerAngles = null;
        acceleratorMeter = null;
        normalizedAcceleratorMeter = null;
        gyro = null;
        normalizedGyro = null;
        emg = emgData;
        this.timestamp = timestamp;
    }

    public byte[] getEmg() {
        return emg;
    }

    public void setEmg(byte[] emg) {
        this.emg = emg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Quaternion getQuaternion() {
        return quaternion;
    }

    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
        normalizedQuaternion = quaternion.normalized();
        eulerAngles = new EulerAngles(quaternion);
        normalizedEulerAngles = new EulerAngles(normalizedQuaternion);
    }

    public Quaternion getNormalizedQuaternion() {
        return normalizedQuaternion;
    }

    public EulerAngles getEulerAngles() {
        return eulerAngles;
    }

    public EulerAngles getNormalizedEulerAngles() {
        return normalizedEulerAngles;
    }

    public Vector3 getAcceleratorMeter() {
        return acceleratorMeter;
    }

    public void setAcceleratorMeter(Vector3 acceleratorMeter) {
        this.acceleratorMeter = acceleratorMeter;
        this.normalizedAcceleratorMeter = acceleratorMeter.normalized();
    }

    public Vector3 getGyro() {
        return gyro;
    }

    public void setGyro(Vector3 gyro) {
        this.gyro = gyro;
        this.normalizedGyro = gyro.normalized();
    }

    public Vector3 getNormalizedAcceleratorMeter() {
        return normalizedAcceleratorMeter;
    }

    public Vector3 getNormalizedGyro() {
        return normalizedGyro;
    }
}
