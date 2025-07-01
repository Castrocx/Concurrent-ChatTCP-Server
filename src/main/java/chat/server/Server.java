package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int MAXIMUM_CLIENTS = 306;
    private final List<ClientConnection> clients;
    private final ServerSocket socket;
    private final ExecutorService service;

    public Server(int port) throws IOException{
        socket= new ServerSocket(port);
        clients= Collections.synchronizedList(new LinkedList<>());
        service = Executors.newCachedThreadPool();

    }

    public void start() {

        while (true) {
            waitConnection();
        }
    }

    private void waitConnection() {
        try {
            Socket clientSocket = socket.accept();

            System.out.println(Messages.NEW_CONNECTION);

            ClientConnection connection = new ClientConnection(clientSocket, this);

            service.submit(connection);

        } catch (IOException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
        }
    }

    public boolean addClient(ClientConnection client) {
        synchronized (clients) {

            if (clients.size() >= MAXIMUM_CLIENTS) {
                return false;
            }

            clients.add(client);
            return true;
        }
    }

    public void broadcast(String message) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                client.send(message);
            }
        }
    }

    public void remove(ClientConnection client) {
        clients.remove(client);
    }

    public String listClients() {
        StringBuilder list = new StringBuilder ("\nConnected Clients:\n");

        synchronized (clients) {
            for (ClientConnection client : clients) {
                list.append(client.getName()).append("\n");
            }
        }

        return list.toString();
    }

    public ClientConnection getClientByName(String name) {
        synchronized (clients) {
            for (ClientConnection client : clients) {
                if (client.getName().equals(name)) {
                    return client;
                }
            }
        }

        return null;
    }
}
