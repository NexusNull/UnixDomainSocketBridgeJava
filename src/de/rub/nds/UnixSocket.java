package de.rub.nds;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


public class UnixSocket extends Thread {
    SocketChannel channel;
    String message;
    boolean ready;

    public UnixSocket(SocketChannel channel) {
        this.channel = channel;
        this.start();

    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            int bytesRead = 0;
            try {
                bytesRead = this.channel.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }

            if (bytesRead == -1) {
                try {
                    this.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.receive(buffer, bytesRead);
        }
    }

    private synchronized void receive(ByteBuffer buffer, int bytesRead) {
        byte[] bytes = new byte[bytesRead];
        buffer.flip();
        buffer.get(bytes);
        message = new String(bytes);
        ready = true;
        this.notify();
    }

    public synchronized String send(String msg) throws IOException {
        System.out.println(msg);
        ready = false;
        channel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        while (!ready) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }

        return message;
    }

    public synchronized void close() throws IOException, InterruptedException {
        channel.close();
        this.join();
    }

}
