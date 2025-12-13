package Internet_Program.Practice1_send_more_times;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10000);
        Socket socket = ss.accept();
        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        int b = 0;
        while((b = bis.read()) != -1){
            System.out.print((char)b);
        }
        socket.close();
        ss.close();
    }
}
