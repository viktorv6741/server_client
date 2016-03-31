package server_client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ClientHandler implements Callable<Boolean> {

    private Socket socket;
    private int port;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private Boolean isAuthenticationFailed;

    public ClientHandler(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    public Boolean call() throws Exception {
        printWriter = new PrintWriter(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        isAuthenticationFailed = socket.isConnected();

        while (isAuthenticationFailed) {
            String msg = bufferedReader.readLine();
            printWriter.println(msg.toUpperCase());
            printWriter.flush();
        }
        return isAuthenticationFailed;
    }
}
