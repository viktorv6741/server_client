package server_client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        ServerSocket serverSocket = new ServerSocket(8080);
        ExecutorService service = Executors.newWorkStealingPool();

        while (true) {
            service.submit(new ClientHandler(serverSocket.accept(), 8080));
        }
    }
}

