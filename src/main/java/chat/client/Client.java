package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {

    private final Socket socket;
    private final ExecutorService service;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.service = Executors.newSingleThreadExecutor();

    }

    public void start() {

        service.submit(new KeyboardHandler(socket));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (!socket.isClosed()) {
                waitMessage(reader);
            }

        } catch (IOException e) {
            System.err.println("Error handling socket connection: " + e.getMessage());
        }
    }

    private void waitMessage(BufferedReader reader) throws IOException {
        String message = reader.readLine();

        if (message== null) {
            System.out.println("Connection closed from server side");
            System.exit(0);
        }

        System.out.println(message);

    }

}
