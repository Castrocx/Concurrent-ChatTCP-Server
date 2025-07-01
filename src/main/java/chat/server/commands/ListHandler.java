package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Server;

public class ListHandler implements CommandHandler {
    @Override
    public void handle(Server server, ClientConnection sender, String message) {
        sender.send(server.listClients());
    }
}
