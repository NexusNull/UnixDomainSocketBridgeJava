package de.rub.nds;

import java.io.File;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.ArrayList;

public class UnixSocketServer extends Thread {
    String path;
    ArrayList<UnixSocket> sockets = new ArrayList<>();
    ServerSocketChannel serverChannel;

    public UnixSocketServer(String path) {
        this.path = path;
        File socketFile = new File(path);
        socketFile.delete();
        this.start();
    }

    @Override
    public void run() {
        Path socketFile = Path.of(path);
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketFile);
        serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
            serverChannel.bind(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (serverChannel.isOpen()) {
            try {
                SocketChannel channel = serverChannel.accept();
                System.out.println("Connected");
                this.addSocket(new UnixSocket(channel));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int test() {
        return this.sockets.size();
    }

    public synchronized void removeSocket(UnixSocket socket) {
        this.sockets.remove(socket);
    }

    public synchronized void addSocket(UnixSocket socket) {
        this.sockets.add(socket);
    }

    public synchronized UnixSocket getSocket(int i) {
        if (i < this.sockets.size()) {
            return this.sockets.get(i);
        }
        return null;
    }
}
