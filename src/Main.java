import java.io.IOException;

public class Main {

    public void run() throws InterruptedException, IOException {
        UnixSocketServer server = new UnixSocketServer("/tmp/asd");
        while (true) {
            if (server.test() > 0) {
                UnixSocket socket = server.getSocket(0);
                socket.send("asd");
            }
            Thread.sleep(1000);
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        Main main = new Main();
        main.run();
    }
}
