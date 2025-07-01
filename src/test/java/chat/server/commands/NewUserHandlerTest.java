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
public class NewUserHandlerTest {

    private NewUserHandler handler;

    @Mock
    private Server server;

    @Mock
    private ClientConnection sender;

    @BeforeEach
    void setUp() {
        handler = new NewUserHandler();
    }

    @Test
    void testValidNewUsername() {
        String newName = "ValidUser";
        when(server.getClientByName(newName)).thenReturn(null);

        handler.handle(server, sender, newName);

        verify(sender).setName(newName);
        verify(server).broadcast(contains(newName));
    }

    @Test
    void testInvalidUsername_TooLong() {
        String newName = "VeryLongUsernameThatExceedsLimit";

        handler.handle(server, sender, newName);

        verify(sender).send(contains("inválido"));
        verify(sender, never()).setName(anyString());
    }

    @Test
    void testDuplicateUsername() {
        String existingName = "ExistingUser";
        when(server.getClientByName(existingName)).thenReturn(mock(ClientConnection.class));

        handler.handle(server, sender, existingName);

        verify(sender).send(contains("já está em uso"));
        verify(sender, never()).setName(anyString());
    }

    @Test
    void testUsernameWithSpaces() {
        String newName = "User With Spaces";

        handler.handle(server, sender, newName);

        verify(sender).send(contains("inválido"));
        verify(sender, never()).setName(anyString());
    }
}
