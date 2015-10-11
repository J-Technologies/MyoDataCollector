package nl.ordina.jtech.bigdata.myo.core.model;

import com.google.gson.Gson;
import com.thalmic.myo.Quaternion;

/**
 * Created by pieter on 9/11/2015.
 */
public class EulerAngles {
    public static final Gson GSON = new Gson();
    private static final int SCALE = 18;
    private final double roll;
    private final double pitch;
    private final double yaw;

    public EulerAngles(Quaternion rotation) {
        Quaternion normalized = rotation.normalized();

        double roll = Math.atan2(2.0f * (normalized.getW() * normalized.getX() + normalized.getY() * normalized.getZ()), 1.0f - 2.0f * (normalized.getX() * normalized.getX() + normalized.getY() * normalized.getY()));
        double pitch = Math.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
        double yaw = Math.atan2(2.0f * (normalized.getW() * normalized.getZ() + normalized.getX() * normalized.getY()), 1.0f - 2.0f * (normalized.getY() * normalized.getY() + normalized.getZ() * normalized.getZ()));


        this.roll = ((roll + Math.PI) / (Math.PI * 2.0) * SCALE);
        this.pitch = ((pitch + Math.PI / 2.0) / Math.PI * SCALE);
        this.yaw = ((yaw + Math.PI) / (Math.PI * 2.0) * SCALE);
    }

    public double getRoll() {
        return roll;
    }

    public double getPitch() {
        return pitch;
    }

    public double getYaw() {
        return yaw;
    }

    @Override
    public String toString() {
        return GSON.toJson(this);
    }
}
