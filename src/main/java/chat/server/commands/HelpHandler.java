package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Server;

public class HelpHandler implements CommandHandler {

    public void handle(Server server, ClientConnection sender, String message) {
        sender.send(Command.commandsList());
    }
}
