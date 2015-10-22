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

package nl.ordina.jtech.bigdata.myo.core.collectors.impl;

import nl.ordina.jtech.bigdata.myo.core.collectors.RecordListener;
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