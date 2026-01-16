package Internet_Program.small_protect;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(10086);
        while (true) {
            Socket socket = ss.accept();
            Thread thread = new Thread(new MyRunnable(socket));
            thread.start();
        }
    }
}
