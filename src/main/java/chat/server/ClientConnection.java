package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientConnection implements Runnable {

    private final Socket socket;
    private final Server server;
    private String name;
    private PrintWriter out ;

    public ClientCoonection(Socket socket, Server server) {
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
                listen.(in);
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
            server.remove(this);
        }
    }

    private void listen(BufferedReader in) throws IOException {
        String message = in.readLine();
        Command
    }
}
