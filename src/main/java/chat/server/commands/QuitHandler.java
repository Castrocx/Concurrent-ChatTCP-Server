package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Messages;
import chat.server.Server;

public class QuitHandler implements CommandHandler {


    @Override
    public void handle(Server server, ClientConnection sender, String message) {
        server.remove(sender);
        server.broadcast(sender.getName() + " " + Messages.LEAVE);
        sender.close();
    }
}
