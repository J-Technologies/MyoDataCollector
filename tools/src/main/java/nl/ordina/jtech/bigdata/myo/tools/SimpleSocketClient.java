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
                    System.out.println("new String(buffer, 0, read) = " + new String(buffer, 0, read));
                }
                Thread.sleep(10);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
