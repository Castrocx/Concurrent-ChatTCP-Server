package chat.server.commands;

import chat.server.Messages;

public enum Command {
    INVALID("", new InvalidHandler()),
    MESSAGE("", new MessageHandler()),
    HELP("help", new HelpHandler()),
    NAME("name", new NameHandler()),
    LIST("list", new ListHandler()),
    WHISPER("whisper", new WhisperHandler()),
    NEW_USER("", new NewUserHandler()),
    QUIT("quit", new QuitHandler());

    private final String commandMessage;
    private final CommandHandler handler;

    Command(String commandMessage, CommandHandler handler) {
        this.commandMessage = commandMessage;
        this.handler = handler;
    }

    public static String commandsList() {

        StringBuilder builder = new StringBuilder("\nCommands List:\n");

        for (Command command : values()) {
            if (!command.commandMessage.isEmpty()) {
                builder.append(Messages.COMMAND_IDENTIFIER)
                        .append(command.commandMessage)
                        .append("\n");
            }
        }

        return builder.toString();
    }

    public static Command getFromString(String message) {

        if (message == null) {
            return QUIT;
        }

        if (!message.startsWith(Messages.COMMAND_IDENTIFIER)) {
            return MESSAGE;
        }

        String userCommand = message.split(" ")[0];

        for (Command command : values()) {
            if (userCommand.equals(Messages.COMMAND_IDENTIFIER + command.commandMessage)) {
                return command;
            }
        }

        return INVALID;
    }

    public CommandHandler getHandler() {
        return handler;
    }
}
