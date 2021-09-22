import java.io.File;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.util.Optional;

public class main {
    public static Charset charset = Charset.forName("UTF-8");
    public static CharsetEncoder encoder = charset.newEncoder();
    public static CharsetDecoder decoder = charset.newDecoder();
    private static Optional<String> readMessageFromSocket(
            SocketChannel channel)
            throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = 0;
        try {
            bytesRead = channel.read(buffer);
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        if (bytesRead < 0)
            return Optional.empty();
        byte[] bytes = new byte[bytesRead];
        buffer.flip();
        buffer.get(bytes);
        String message = new String(bytes);
        return Optional.of(message);
    }


    public static void main(String args[]) throws IOException, InterruptedException {
        File myObj = new File("/tmp/asd");
        myObj.delete();

        Path socketFile = Path.of("/tmp/asd");
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketFile);
        ServerSocketChannel serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
        serverChannel.bind(address);
        SocketChannel channel = serverChannel.accept();
        System.out.println("Connected");
        System.out.println("isConnectionPending"+channel.isConnectionPending());
        System.out.println("isOpen"+channel.isOpen());
        System.out.println("isBlocking"+channel.isBlocking());
        System.out.println("isRegistered"+channel.isRegistered());
        while (channel.isConnected()) {
            channel.write(encoder.encode(CharBuffer.wrap("close")));
            Thread.sleep(100);
            System.out.print("run");
            readMessageFromSocket(channel).ifPresent(System.out::println);
        }
    }
}
