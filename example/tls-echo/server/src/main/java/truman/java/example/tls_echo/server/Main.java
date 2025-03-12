package truman.java.example.tls_echo.server;

import truman.java.example.tls_echo.common.Callback;
import truman.java.example.tls_echo.common.Message;

import java.net.Socket;

import static java.lang.System.out;

public class Main {

    private void openServer(int port, String password) {
        Server server = new Server(port, password, new Callback() {
            @Override
            public void onConnected(Socket socket) {
                out.println("Client connected: " + socket.getRemoteSocketAddress());
            }

            @Override
            public void onError(Exception e) {
                out.println("Error detected: " + e);
            }

            @Override
            public void onMessage(Message m) {
                out.println("Message received: " + m.get());
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(server::close));

        server.open();

        out.println("Server terminated... bye!");
    }

    private static final int DEFAULT_PORT = 12345;
    private static final String DEFAULT_PASSWORD = "spassword";

    private Main usage() {
        out.println("Usage: Press Ctrl + C to terminate the server");
        return this;
    }

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        String password = args.length > 1 ? args[1] : DEFAULT_PASSWORD;
        new Main().usage().openServer(port, password);
    }
}
