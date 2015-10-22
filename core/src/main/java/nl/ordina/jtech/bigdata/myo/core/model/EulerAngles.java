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
import com.thalmic.myo.Quaternion;

/**
 * Extension on the Myo data model.
 */
public class EulerAngles {
    public static final Gson GSON = new Gson();
    private static final int SCALE = 18;
    private final double roll;
    private final double pitch;
    private final double yaw;

    /**
     * Constructor
     * Callculate the Euler angels from a {@link Quaternion}
     * @param rotation Quaternion
     */
    public EulerAngles(Quaternion rotation) {
        Quaternion normalized = rotation.normalized();

        double roll = Math.atan2(2.0f * (normalized.getW() * normalized.getX() + normalized.getY() * normalized.getZ()), 1.0f - 2.0f * (normalized.getX() * normalized.getX() + normalized.getY() * normalized.getY()));
        double pitch = Math.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
        double yaw = Math.atan2(2.0f * (normalized.getW() * normalized.getZ() + normalized.getX() * normalized.getY()), 1.0f - 2.0f * (normalized.getY() * normalized.getY() + normalized.getZ() * normalized.getZ()));

        this.roll = ((roll + Math.PI) / (Math.PI * 2.0) * SCALE);
        this.pitch = ((pitch + Math.PI / 2.0) / Math.PI * SCALE);
        this.yaw = ((yaw + Math.PI) / (Math.PI * 2.0) * SCALE);
    }

    /**
     * Getter
     * @return roll
     */
    public double getRoll() {
        return roll;
    }

    /**
     * Getter
     * @return pitch
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * Getter yaw
     * @return
     */
    public double getYaw() {
        return yaw;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
