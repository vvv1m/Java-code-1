package Internet_Program.Practice2_get_and_return;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        //get data and return message

        //get data
        ServerSocket ss = new ServerSocket(10000);
        Socket socket = ss.accept();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        int b = 0;
        //detail:read function will get data from socket
        //      but it need a end flag to stop while
        //      if else it will stop at the read function 
        while((b = isr.read()) != -1){
            System.out.print((char)b);
        }
        //send return message
        OutputStream os =socket.getOutputStream();
        os.write("succeed to send data".getBytes());

        //close
        socket.close();
        ss.close();
    }
}
