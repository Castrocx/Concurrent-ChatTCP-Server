package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Messages;
import chat.server.Server;

public class NewUserHandler implements CommandHandler{

    @Override
    public void handle(Server server, ClientConnection sender, String name) {

        if (!isValid(name)) {
            Command.INVALID.getHandler().handle(server, sender, name);
            return;

        }

        if (server.getClientByName(name) !=null) {
            Command.INVALID.getHandler().handle(server, sender, name);
            return;
        }
        sender.setName(name);
        server.broadcast(sender.getName() + " " + Messages.JOIN_ALERT);
    }

    private boolean isValid(String name) {
        return !(name.split(" ").length > 2) && !(name.length() > 12);
    }
}
