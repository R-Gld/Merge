package fr.rgld_.merge;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer implements Runnable {

    private final ServerSocket serverSocket;
    private final Merge merge;

    private boolean running = true;

    public TCPServer(Merge merge, Server server){
        this.merge = merge;
        this.serverSocket = server.getServerSocket();
    }

    @Override
    public void run() {
        while(running) {
            try {
                Socket socket = serverSocket.accept();

                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(merge.getProxy().getOnlineCount());
                pw.flush();

                socket.close();
            } catch(SocketException ignored){
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void stop(){
        this.running = false;
    }
}
