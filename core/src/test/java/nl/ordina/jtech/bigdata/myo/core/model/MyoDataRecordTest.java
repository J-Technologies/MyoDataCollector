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

package nl.ordina.jtech.bigdata.myo.core.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.ordina.jtech.bigdata.myo.core.serializers.MyDataRecordSerializer;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by pieter on 10/30/2015.
 */
public class MyoDataRecordTest {

    public static final String JSON_DATA = "" +
            "{\"timestamp\":1445420944132171," +
            "\"emg\":[-2,1,-1,2,3,-1,0,2]," +
            "\"quaternion\":{\"x\":0.42718505859375,\"y\":0.21240234375,\"z\":0.20672607421875,\"w\":0.8541259765625}," +
            "\"normalizedQuaternion\":{\"x\":0.4272131095761943,\"y\":0.2124162910880349,\"z\":0.20673964882619947,\"w\":0.8541820624959655}," +
            "\"eulerAngles\":{\"roll\":11.816403100356881,\"pitch\":10.07334703689563,\"yaw\":10.648507100187603}," +
            "\"normalizedEulerAngles\":{\"roll\":11.816403100356881,\"pitch\":10.07334703689563,\"yaw\":10.648507100187603},\"acceleratorMeter\":{\"x\":-0.181640625,\"y\":0.796875,\"z\":0.53271484375},\"gyro\":{\"x\":2.125,\"y\":1.75,\"z\":1.3125},\"normalizedAcceleratorMeter\":{\"x\":-0.18618420402721278,\"y\":0.8168081208935786,\"z\":0.5460402327787343},\"normalizedGyro\":{\"x\":0.696785684263249,\"y\":0.5738235046873815,\"z\":0.43036762851553617}}";
    public Gson gson;

    @Before
    public void before() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(MyoDataRecord.class, new MyDataRecordSerializer());
        gson = builder.create();
    }

    @Test
    public void testInitialize() throws Exception {
        MyoDataRecord myoDataRecord = gson.fromJson(JSON_DATA, MyoDataRecord.class);
        System.out.println("old = " + myoDataRecord);
        System.out.println("new = " + gson.toJson(myoDataRecord));
    }

}