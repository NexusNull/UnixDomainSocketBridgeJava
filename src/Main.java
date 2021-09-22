import java.io.IOException;

public class Main {

    public void run() throws InterruptedException, IOException {
        UnixSocketServer server = new UnixSocketServer("/tmp/asd");
        new InstanceManager("/home/nexus/Development/CLionProjects/UnixDomainSocketBridgeCpp/cmake-build-debug/UnixDomainSocketBridgeCpp", 5);

        while (true) {
            if (server.test() > 0) {
                UnixSocket socket = server.getSocket(0);
                try {
                    socket.send("asd");
                } catch (IOException e) {

                }
            }
            Thread.sleep(1000);
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        Main main = new Main();
        main.run();
    }
}
