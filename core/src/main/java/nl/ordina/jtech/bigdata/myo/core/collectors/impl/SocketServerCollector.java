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

package nl.ordina.jtech.bigdata.myo.core.collectors.impl;

import nl.ordina.jtech.bigdata.myo.core.collectors.RecordListener;
import nl.ordina.jtech.bigdata.myo.core.model.MyoDataRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Server for clients to recieve events.
 * <p>
 * Allows for multiple connections
 */
public class SocketServerCollector implements RecordListener {

    public static final int SOCKET_PORT = 5555;
    private static final Logger LOGGER = LogManager.getLogger(SocketServerCollector.class);
    public List<AsynchronousSocketChannel> channels = new ArrayList<>();
    boolean stream = false;
    private long recordCount = 0;
    private long droppedCount = 0;
    private String lastReceived = "NOT.CON.";

    public SocketServerCollector() {
        initialize();
    }


    private void initialize() {

        try {
            final AsynchronousServerSocketChannel listener =
                    AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(SOCKET_PORT));

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
    public void newRecord(MyoDataRecord dataRecord) {
        if (stream) {
            recordCount++;
            ByteBuffer wrap = ByteBuffer.wrap((dataRecord.toString() + "\n").getBytes());
            for (AsynchronousSocketChannel channel : channels) {
                try {
                    channel.write(wrap);
                } catch (Exception e) {
                    droppedCount++;
                    if (e instanceof ExecutionException) {
                        if (e.getMessage().contains("closed")) {
                            try {
                                channel.close();
                            } catch (IOException e1) {
                                //
                            }
                        }
                    }
                }

                try {
                    ByteBuffer allocate = ByteBuffer.allocate(24);
                    channel.read(allocate, -1, new CompletionHandler<Integer, Integer>() {
                        @Override
                        public void completed(Integer result, Integer attachment) {
                            lastReceived = new String(allocate.array());
                        }

                        @Override
                        public void failed(Throwable exc, Integer attachment) {
                            System.out.println("exc = " + exc);
                        }
                    });
                } catch (Exception e) {
                }
            }

        }
    }

    @Override
    public void start() {
        stream = true;
        recordCount = 0;
        droppedCount = 0;
        lastReceived = "NO.DATA";
    }

    @Override
    public void stop() {
        stream = false;
        LOGGER.info("Send {} records and dropped {}", recordCount, droppedCount);
        lastReceived = "NO.DATA";
    }

    @Override
    public boolean isActive() {
        return stream;
    }

    public String getRecieved() {
        return lastReceived;
    }
}
