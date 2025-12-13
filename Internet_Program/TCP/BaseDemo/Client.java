package Internet_Program.TCP.BaseDemo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        //TCP send data
        // 1 create a socket
        //      detail: connect to server when create socket
        //          if fail to connect , it will throw a error 
        Socket socket = new Socket("127.0.0.1", 10000);

        // 2 get OutputStream from socket

        OutputStream os = socket.getOutputStream();
        // write data
        // os.write("hello world ".getBytes());
        os.write("你好，世界 ".getBytes(Charset.forName("UTF-8")));
        // close
        os.close();
        socket.close();
    }
}
