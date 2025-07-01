package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Server;

public class MessageHandler implements CommandHandler {
    @Override
    public void handle(Server server, ClientConnection sender, String message) {

        if(!isValid(message)) {
            return;
        }

        server.broadcast(sender.getName() + ": " + message);
    }

    private boolean isValid(String message) {
        return !message.trim().isEmpty();
    }
}
