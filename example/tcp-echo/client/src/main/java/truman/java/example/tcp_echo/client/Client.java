package truman.java.example.tcp_echo.client;

import truman.java.example.tcp_echo.common.Callback;
import truman.java.example.tcp_echo.common.Packet;
import truman.java.example.tcp_echo.common.Message;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private final String ip;
    private final int port;
    private final Callback callback;
    private final ExecutorService executorService;
    private final AtomicBoolean isClosed;

    private Socket serverSocket;
    private Transmitter tx;
    private Receiver rx;

    public Client(String ip, int port, Callback callback) {
        this.ip = ip;
        this.port = port;
        this.callback = callback;
        this.executorService = Executors.newSingleThreadExecutor();
        this.isClosed = new AtomicBoolean(false);
    }

    public void open() {
        try {
            serverSocket = new Socket(ip, port);
            onConnected(serverSocket);
        } catch (InterruptedException ignored) {
        } catch (IOException e) {
            onError(e);
        }
    }

    private void onConnected(Socket serverSocket) throws IOException, InterruptedException {
        tx = new Transmitter(serverSocket);
        rx = new Receiver(serverSocket);
        executorService.execute(rx);

        callback.onConnected(serverSocket);

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public void send(String message) {
        Objects.requireNonNull(tx)
                .send(new Message(message));
    }

    public void close() {
        if (isClosed.getAndSet(true)) {
            return;
        }
        closeInternal(tx);
        closeInternal(rx);
        closeInternal(serverSocket);
        executorService.shutdown();
    }

    private static void closeInternal(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ignored) {}
    }

    private void onReceive(Packet packet) {
        if (!(packet instanceof Message)) {
            return;
        }
        callback.onMessage((Message) packet);
    }

    private synchronized void onError(Exception e) {
        callback.onError(e);
        close();
    }

    private class Transmitter implements Closeable {

        ObjectOutputStream oos;

        Transmitter(Socket serverSocket) throws IOException {
            oos = new ObjectOutputStream(serverSocket.getOutputStream());
        }

        void send(Packet packet) {
            try {
                oos.writeObject(packet);
            } catch (IOException e) {
                onError(e);
            }
        }

        @Override
        public void close() throws IOException {
            oos.close();
        }
    }

    private class Receiver implements Runnable, Closeable {

        ObjectInputStream ois;

        Receiver(Socket serverSocket) throws IOException {
            ois = new ObjectInputStream(serverSocket.getInputStream());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    onReceive((Packet) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    onError(e);
                    break;
                }
            }
        }

        @Override
        public void close() throws IOException {
            ois.close();
        }
    }
}