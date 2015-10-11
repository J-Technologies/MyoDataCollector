package nl.ordina.jtech.bigdata.myo.collector.collectors;

import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import nl.ordina.jtech.bigdata.myo.core.AbstractDataCollector;
import nl.ordina.jtech.bigdata.myo.core.DataCollector;
import nl.ordina.jtech.bigdata.myo.core.EulerAngles;

import java.util.concurrent.atomic.AtomicLong;

public class RecordCountCollector extends AbstractDataCollector implements DataCollector {

    AtomicLong count = new AtomicLong(0);

    public RecordCountCollector() {
        count.set(0);
    }

    @Override
    public void reset() {
        count.set(0);
    }

    @Override
    public void dataAvailable(long timestamp, Quaternion quaternion, EulerAngles eulerAngles, Vector3 acceleratorMeter, Vector3 gyro) {
        long current = count.addAndGet(1);
        if ((current % 25) == 0) {
            System.out.println("count = " + count);
        }
    }
}
