package truman.java.example.tcp_echo.server;

import truman.java.example.tcp_echo.common.Callback;
import truman.java.example.tcp_echo.common.Packet;
import truman.java.example.tcp_echo.common.Message;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private final int port;
    private final Callback callback;
    private final ExecutorService executorService;
    private final AtomicBoolean isClosed;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Transmitter tx;
    private Receiver rx;

    public Server(int port, Callback callback) {
        this.port = port;
        this.callback = callback;
        this.executorService = Executors.newSingleThreadExecutor();
        this.isClosed = new AtomicBoolean(false);
    }

    public void open() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            onConnected(clientSocket);
        } catch (InterruptedException ignored) {
        } catch (IOException e) {
            onError(e);
        }
    }

    private void onConnected(Socket clientSocket) throws IOException, InterruptedException {
        tx = new Transmitter(clientSocket);
        rx = new Receiver(clientSocket);
        executorService.execute(rx);

        callback.onConnected(clientSocket);

        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public void close() {
        if (isClosed.getAndSet(true)) {
            return;
        }
        closeInternal(tx);
        closeInternal(rx);
        closeInternal(clientSocket);
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
        tx.send(packet); // Send back the packet for echo
    }

    private synchronized void onError(Exception e) {
        callback.onError(e);
        close();
    }

    private class Transmitter implements Closeable {

        ObjectOutputStream oos;

        Transmitter(Socket clientSocket) throws IOException {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
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

        Receiver(Socket clientSocket) throws IOException {
            ois = new ObjectInputStream(clientSocket.getInputStream());
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