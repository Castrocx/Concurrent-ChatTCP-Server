package chat.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServerTest {

    private Server server;

    @Mock
    private ClientConnection mockClient1;

    @Mock
    private ClientConnection mockClient2;

    @BeforeEach
    void setUp() {
        server = new Server();
        when(mockClient1.getName()).thenReturn("User1");
        when(mockClient2.getName()).thenReturn("User2");
    }

    @Test
    void testAddClientSuccess() {
        assertTrue(server.addClient(mockClient1));
    }

    @Test
    void testAddClientWhenMaximumReached() {
        // Preencher até o limite máximo
        for (int i = 0; i < Server.MAXIMUM_CLIENTS; i++) {
            server.addClient(new ClientConnection(null, server));
        }
        assertFalse(server.addClient(mockClient1));
    }

    @Test
    void testBroadcast() {
        server.addClient(mockClient1);
        server.addClient(mockClient2);

        String message = "Test message";
        server.broadcast(message);

        verify(mockClient1).send(message);
        verify(mockClient2).send(message);
    }

    @Test
    void testRemoveClient() {
        server.addClient(mockClient1);
        server.remove(mockClient1);

        // Verifica se o cliente foi removido tentando fazer broadcast
        server.broadcast("test");
        verify(mockClient1, never()).send(anyString());
    }

    @Test
    void testListClients() {
        server.addClient(mockClient1);
        server.addClient(mockClient2);

        String clientList = server.listClients();

        assertTrue(clientList.contains("User1"));
        assertTrue(clientList.contains("User2"));
    }
}
