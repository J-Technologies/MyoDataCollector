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

package nl.ordina.jtech.bigdata.myo.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by pieter on 10/21/2015.
 */
public class SimpleSocketClient {

    public static void main(String... args) {
        SimpleSocketClient listener = new SimpleSocketClient();
        listener.start();
    }

    private void start() {
        try (Socket socket = new Socket("localhost", 5555)) {
            InputStream inputStream = socket.getInputStream();
            System.out.println("serverSocket = " + socket.toString());
            byte[] buffer = new byte[1014];
            while (socket.isConnected()) {
                int read = inputStream.read(buffer);
                if (read > 0) {
                    System.out.print(new String(buffer, 0, read));
                } else {
                    Thread.sleep(5);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
