package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientConnection implements Runnable {

    private final Socket socket;
    private final Server server;
    private String name;
    private PrintWriter out ;

    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            BufferedReader in = openStreams();

            chooseUsername(in);

            send(Messages.WELCOME);
            send(Messages.HELP);

            if (!server.addClient(this)) {
                send(Messages.SERVER_FULL);
                close();
            }
            while(!socket.isClosed()) {
                listen(in);
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            server.remove(this);
        }
    }

    private void listen(BufferedReader in) throws IOException {
        String message = in.readLine();
        Command.getFromString(message).getHandler().handle(server, this, message);
    }

    private BufferedReader openStreams() throws IOException {
        out = new PrintWriter(socket.getOutputStream(),true);
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void chooseUsername(BufferedReader in) throws IOException {
        while (this.name == null) {
            send(Messages.PICK_NAME);
            String name = in.readLine();
            Command.NEW_USER.getHandler().handle(server, this, name);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }

    public void send(String message) {
        out.println(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
