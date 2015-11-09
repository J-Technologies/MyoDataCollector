/*
 * Copyright (c) 2014 Pieter van der Meer (pieter_at_elucidator_nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package nl.ordina.jtech.bigdata.myo.core.serializers;

import com.google.gson.*;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import nl.ordina.jtech.bigdata.myo.core.model.EulerAngles;
import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

import java.lang.reflect.Type;

/**
 * Created by pieter on 10/30/2015.
 */
public class MyDataRecordSerializer implements JsonSerializer<MyoDataRecord> {


    @Override
    public JsonElement serialize(MyoDataRecord myoDataRecord, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("timestamp", new JsonPrimitive(myoDataRecord.getTimestamp()));
        convertEmg(jsonObject, myoDataRecord.getEmg());
        convertQuaternion(jsonObject, myoDataRecord.getNormalizedQuaternion(), "normalizedQuaternion");
        convertQuaternion(jsonObject, myoDataRecord.getQuaternion(), "quaternion");
        convertVector(jsonObject, myoDataRecord.getAcceleratorMeter(), "acceleratorMeter");
        convertVector(jsonObject, myoDataRecord.getGyro(), "gyro");
        convertVector(jsonObject, myoDataRecord.getNormalizedAcceleratorMeter(), "normalizedAcceleratorMeter");
        convertVector(jsonObject, myoDataRecord.getNormalizedGyro(), "normalizedGyro");
        convertEulerAngels(jsonObject, myoDataRecord.getEulerAngles(), "eulerAngels");
        convertEulerAngels(jsonObject, myoDataRecord.getNormalizedEulerAngles(), "normalizedEulerAngels");

        return jsonObject;
    }

    private void convertQuaternion(JsonObject jsonObject, Quaternion quaternion, String name) {
        jsonObject.add(name + "_x", new JsonPrimitive(quaternion!=null?quaternion.getX():0));
        jsonObject.add(name + "_y", new JsonPrimitive(quaternion!=null?quaternion.getY():0));
        jsonObject.add(name + "_z", new JsonPrimitive(quaternion!=null?quaternion.getZ():0));
        jsonObject.add(name + "_w", new JsonPrimitive(quaternion!=null?quaternion.getW():0));
    }

    private void convertVector(JsonObject jsonObject, Vector3 vector3, String name) {
        jsonObject.add(name + "_x", new JsonPrimitive(vector3!=null?vector3.getX():0));
        jsonObject.add(name + "_y", new JsonPrimitive(vector3!=null?vector3.getY():0));
        jsonObject.add(name + "_z", new JsonPrimitive(vector3!=null?vector3.getZ():0));
    }

    private void convertEulerAngels(JsonObject jsonObject, EulerAngles angels, String name) {
        jsonObject.add(name + "_pitch", new JsonPrimitive(angels!=null?angels.getPitch():0));
        jsonObject.add(name + "_roll",  new JsonPrimitive(angels!=null?angels.getRoll():0));
        jsonObject.add(name + "_yaw",   new JsonPrimitive(angels!=null?angels.getYaw():0));
    }

    private void convertEmg(JsonObject jsonObject, byte[] data) {
        for (int i = 0; i < 8; i++)
            jsonObject.add("emg_" + i, new JsonPrimitive(data[i]));
    }
}
