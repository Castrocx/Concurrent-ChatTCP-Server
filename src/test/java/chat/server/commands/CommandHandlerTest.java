package chat.server.commands;

import chat.server.ClientConnection;
import chat.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandHandlerTest {

    private CommandHandler commandHandler;

    @Mock
    private Server server;

    @Mock
    private ClientConnection clientConnection;

    @BeforeEach
    void setUp() {
        commandHandler = new CommandHandler(server);
        when(clientConnection.getName()).thenReturn("TestUser");
    }

    @Test
    void testHelpCommand() {
        commandHandler.execute(clientConnection, "/help");
        verify(clientConnection).send(contains("Available commands:"));
    }

    @Test
    void testListCommand() {
        commandHandler.execute(clientConnection, "/list");
        verify(server).listClients();
    }

    @Test
    void testWhisperCommand() {
        String message = "/whisper User2 Hello there!";
        commandHandler.execute(clientConnection, message);
        verify(server).whisper(eq(clientConnection), eq("User2"), contains("Hello there!"));
    }

    @Test
    void testNameCommand() {
        String newName = "NewName";
        commandHandler.execute(clientConnection, "/name " + newName);
        verify(clientConnection).setName(newName);
    }

    @Test
    void testQuitCommand() {
        commandHandler.execute(clientConnection, "/quit");
        verify(clientConnection).close();
    }

    @Test
    void testInvalidCommand() {
        commandHandler.execute(clientConnection, "/invalidcommand");
        verify(clientConnection).send(contains("Invalid command"));
    }

    @Test
    void testRegularMessage() {
        String message = "Hello everyone!";
        commandHandler.execute(clientConnection, message);
        verify(server).broadcast(contains("TestUser: Hello everyone!"));
    }
}
