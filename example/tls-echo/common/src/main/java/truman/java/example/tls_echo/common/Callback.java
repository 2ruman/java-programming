package truman.java.example.tls_echo.common;

import java.net.Socket;

public interface Callback {
    void onConnected(Socket socket);
    void onError(Exception e);
    void onMessage(Message m);
}
