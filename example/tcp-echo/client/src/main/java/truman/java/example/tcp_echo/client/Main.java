package truman.java.example.tcp_echo.client;

import truman.java.example.tcp_echo.common.Callback;
import truman.java.example.tcp_echo.common.Message;
import truman.java.example.tcp_echo.common.Prompt;
import truman.java.util.hot_console.HotConsole;

import java.net.Socket;

public class Main {

    private void openClient(String ip, int port) {
        Client client = new Client(ip, port, new Callback() {
            @Override
            public void onConnected(Socket socket) {
                out.insertln("Connected to server: " + socket.getRemoteSocketAddress());
            }

            @Override
            public void onError(Exception e) {
                out.insertln("Error detected: " + e);
            }

            @Override
            public void onMessage(Message m) {
                out.insertln("Message echoed: " + m);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(client::close));
        Prompt.create("Message : ", client::send, client::close, "exit"::equals).start();

        client.open();

        out.println("Client terminated... bye!");
    }

    private static final HotConsole out = HotConsole.getInstance();
    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 12345;

    private Main usage() {
        out.println("Usage: enter 'exit' to terminate the client");
        return this;
    }

    public static void main(String[] args) {
        String ip = args.length > 0 ? args[0] : DEFAULT_IP;
        int port = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_PORT;
        new Main().usage().openClient(ip, port);
    }
}