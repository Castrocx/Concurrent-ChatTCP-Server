# Concurrent TCP Chat Server

A concurrent TCP chat server implemented in Java that allows multiple simultaneous client connections and real-time communication.

## Features

- Multi-client support with concurrent connections
- Command system with '/' prefix
- Private messaging (whisper) functionality
- Username management
- Online users list
- Built-in help system

## Available Commands

- `/help` - Shows list of available commands
- `/name <new_name>` - Changes your username
- `/list` - Lists all connected users
- `/whisper <user> <message>` - Sends a private message
- `/quit` - Disconnects from the server

## Requirements

- Java JDK 8 or higher
- Maven (for building)

## How to Run

### Server

1. Build the project:
```bash
mvn clean package
```

2. Start the server:
```bash
java -cp target/classes chat.server.ServerLauncher [port]
```
If no port is specified, the server will use the default port 8090.

### Client

To connect a client to the server:
```bash
java -cp target/classes chat.client.ClientLauncher <host> <port>
```
Example:
```bash
java -cp target/classes chat.client.ClientLauncher localhost 8090
```

## Project Structure

```
src/
├── main/
    └── java/
        └── chat/
            ├── client/          # Client implementation
            └── server/          # Server core classes
                └── commands/    # Chat command system
```

## Technical Details

- Built with Java
- Uses TCP/IP for network communication
- Implements concurrent programming for handling multiple clients
- Command pattern for processing user inputs
- Thread-safe broadcasting system

## Getting Started

1. Clone the repository
2. Build using Maven
3. Start the server
4. Connect multiple clients
5. Use /help to see available commands


