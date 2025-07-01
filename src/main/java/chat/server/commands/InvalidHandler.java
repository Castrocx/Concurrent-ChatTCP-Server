package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Messages;
import chat.server.Server;

public class InvalidHandler implements CommandHandler{


    @Override
    public void handle(Server server, ClientConnection sender, String message) {
        sender.send(Messages.ERROR + ": " + (message.startsWith(Messages.COMMAND_IDENTIFIER) ? Messages.INVALID_COMMAND : message));
    }
}
