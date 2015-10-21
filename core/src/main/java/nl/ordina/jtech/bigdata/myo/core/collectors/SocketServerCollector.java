package nl.ordina.jtech.bigdata.myo.core.collectors;

import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter on 10/21/2015.
 */
public class SocketServerCollector implements RecordListener {

    boolean stream = false;
    List<AsynchronousSocketChannel> channels = new ArrayList<>();

    public SocketServerCollector() {
        initialize();
    }


    private void initialize() {

        try {
            final AsynchronousServerSocketChannel listener =
                    AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5555));

            listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                public void completed(AsynchronousSocketChannel ch, Void att) {
                    // accept the next connection
                    listener.accept(null, this);
                    try {
                        System.out.println("Accepted connection from: " + ch.getRemoteAddress());
                    } catch (IOException e) {
                        //Nothing to do here
                    }
                    channels.add(ch);
                }

                public void failed(Throwable exc, Void att) {
                    System.out.println("Enable to get connection" + exc.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newRecord(MyoDataRecord listener) {
        if (stream) {
            try {
                ByteBuffer wrap = ByteBuffer.wrap((listener.toString() + "\n").getBytes());
                channels.stream().filter(s -> s.isOpen()).forEach(s -> s.write(wrap));
            } catch (Exception e) {
                System.out.println("Consumer is to slow.. dropping data");
            }
        }
    }

    @Override
    public void start() {
        stream = true;
    }

    @Override
    public void stop() {
        stream = false;
    }

    @Override
    public void dump(String key) {
        //Nothing to do
    }
}
