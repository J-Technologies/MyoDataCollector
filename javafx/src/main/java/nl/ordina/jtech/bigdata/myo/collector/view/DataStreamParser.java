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

package nl.ordina.jtech.bigdata.myo.collector.view;

/**
 * Created by pieter on 11/15/2015.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataStreamParser {

    public static final int DEFAULT_PORT = 5556;

    private final int port;

    private final ExecutorService exec;
    private final Logger logger = LogManager.getLogger(DataStreamParser.class);

    private final StringProperty speed = new SimpleStringProperty(this,
            "speed", "NOT.CON.");

    public DataStreamParser(int port) throws IOException {
        this.port = port;

        this.exec = Executors.newCachedThreadPool(runnable -> {
            // run thread as daemon:
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        try {
            startListening();
        } catch (IOException exc) {
            exc.printStackTrace();
            throw exc;
        }
    }

    public DataStreamParser() throws IOException {
        this(DEFAULT_PORT);
    }

    public final StringProperty speedProperty() {
        return this.speed;
    }

    public void startListening() throws IOException {
        Callable<Void> connectionListener = () -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                logger.info(
                        "Server listening on " + serverSocket.getInetAddress()
                                + ":" + serverSocket.getLocalPort());
                while (true) {
                    logger.info("Waiting for connection:");
                    Socket socket = serverSocket.accept();
                    logger.info("Connection accepted from " + socket.getInetAddress());
                    handleConnection(socket);
                }
            } catch (Exception exc) {
                logger.error("Exception in connection handler", exc);
            }
            return null;
        };
        exec.submit(connectionListener);
    }

    public void shutdown() {
        exec.shutdownNow();
    }

    private void handleConnection(Socket socket) {
        Callable<Void> connectionHandler = () -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    logger.info("Received: " + line);
                    processLine(line);
                }
                System.out.println("Connection closed from " + socket.getInetAddress());
            }
            return null;
        };
        exec.submit(connectionHandler);
    }

    private void processLine(String line) {
        speed.set(line);
    }

}