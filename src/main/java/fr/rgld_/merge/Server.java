package fr.rgld_.merge;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Romain Galland
 * Contact on romain.galland@rgld.fr
 */
public class Server {

    private final int port;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public int getPort(){
        return port;
    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

}
