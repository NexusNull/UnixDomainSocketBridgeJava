import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Welp implements Runnable {
    public static Charset charset = Charset.forName("UTF-8");
    public static CharsetEncoder encoder = charset.newEncoder();
    public static CharsetDecoder decoder = charset.newDecoder();
    SocketChannel socket;
    public Welp(SocketChannel socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                this.socket.write(encoder.encode(CharBuffer.wrap("asd")));
                System.out.println("asdasd");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
