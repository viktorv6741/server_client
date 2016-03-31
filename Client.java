package server_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Callable<Boolean> {

    private Socket socket;
    private int port;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private BufferedReader consoleReader;
    private Boolean isAuthenticationFailed;


    public Client(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    public Boolean call() throws Exception {
        printWriter = new PrintWriter(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        isAuthenticationFailed = socket.isConnected();

        while (isAuthenticationFailed) {

            String msg = consoleReader.readLine();
            printWriter.println(msg);
            printWriter.flush();

            String msgFromServer = bufferedReader.readLine();
            System.out.println(msgFromServer);
        }
        return isAuthenticationFailed;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8080);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Client(socket, 8080)).get();
    }
}
