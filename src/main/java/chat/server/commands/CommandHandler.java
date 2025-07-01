package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Server;

public interface CommandHandler {
    void handle(Server server, ClientConnection sender, String message);
}
