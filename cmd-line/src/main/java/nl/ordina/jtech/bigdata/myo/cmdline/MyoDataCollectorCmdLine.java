package nl.ordina.jtech.bigdata.myo.cmdline;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.enums.StreamEmgType;
import nl.ordina.jtech.bigdata.myo.core.csv.CsvDataCollectionWriter;
import nl.ordina.jtech.bigdata.myo.core.csv.CsvDataCollector;
import nl.ordina.jtech.bigdata.myo.core.DataCollectionWriter;
import nl.ordina.jtech.bigdata.myo.core.DeviceListenerImpl;

import java.io.IOException;

/**
 * Created by pieter on 9/11/2015.
 */
public class MyoDataCollectorCmdLine {

    private CsvDataCollector dataCollector;
    DataCollectionWriter dataCollectionWriter;

    public MyoDataCollectorCmdLine() throws IOException {
        dataCollector = new CsvDataCollector();
        dataCollectionWriter = new CsvDataCollectionWriter(dataCollector, "c:\\tmp\\myo");
    }


    public static void main(String[] args) throws IOException {
        MyoDataCollectorCmdLine myoDataCollectorCmdLine = new MyoDataCollectorCmdLine();
        myoDataCollectorCmdLine.start();

    }

    private void start() {
        try {
            Hub hub = new Hub("com.example.hello-myo");

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }
            myo.setStreamEmg(StreamEmgType.STREAM_EMG_ENABLED);

            System.out.println("Connected to a Myo armband!");
            DeviceListener dataCollector = new DeviceListenerImpl(this.dataCollector);
            hub.addListener(dataCollector);

            while (true) {
                startRun(hub);
            }

        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private  void startRun(Hub hub) throws IOException {
        System.out.println("Press any key to start (again to stop)");
        waitForEnter();
        clearInBuffer();

        while (System.in.available() <= 0) {
            hub.run(50);
        }

        clearInBuffer();
        System.out.println("Press \"O\" for good data, \"B\"  for bad");
        int read = System.in.read();
        boolean done = false;
        while(!done) {
            if ((char) read == 'O' || (char) read == 'o') {
                doOk();
                return;
            }
            if ((char) read == 'B' || (char) read == 'b') {
                doBad();
                return;

            }
        }
    }

    private  void doBad() {
        System.out.println("Bad");
        dataCollectionWriter.writeBadData();
        clearInBuffer();
    }

    private  void doOk() {
        System.out.println("Ok");
        dataCollectionWriter.writeOkData();
        clearInBuffer();
    }

    private  void clearInBuffer()  {
        try {
            while(System.in.available() > 0
                    ) {
                System.in.read();
            }
        } catch (IOException e) {
        }
    }

    private  void waitForEnter() throws IOException {
        try {
            while (System.in.available() == 0) {
                try {
                     Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        } catch (IOException e) {
        }
    }
}
