package Internet_Program.TCP.BaseDemo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class Server {
    public static void main(String[] args) throws IOException {
        //TCP get data
        // 1 create ServerSocket
        //      detail: same as client
        ServerSocket ss = new ServerSocket(10000);

        // 2 wait to accept client
        //      return the socket
        Socket socket = ss.accept();

        // 3 get InputStream from socket
        InputStream is = socket.getInputStream();
        //  get data
        // question: can only read English  Chinese is mixed
        //  answer: turn byte stream to char stream, use turn_stream
        //      continue to turn to buffer stream
        
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);
        int b;
        while((b = br.read()) != -1){
            System.out.print((char)b);
        }

        // 4 close
        //      detail: we do not need to close the stream ,because when the socket close,the stream close too
        socket.close();
        ss.close();

    }
}
