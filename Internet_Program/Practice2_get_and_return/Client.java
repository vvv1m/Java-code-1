package Internet_Program.Practice2_get_and_return;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        //Client:send a message, get the return message from Server and print

        //send message by port 10000
        Socket socket = new Socket("127.0.0.1", 10000);
        OutputStream os = socket.getOutputStream();



        //send data
        os.write("this is some words".getBytes());

        //so we need a end flag
        socket.shutdownOutput();

        //get return message
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        int b = 0;
        while((b = isr.read()) != -1){
            System.out.print((char)b);
        }
        socket.close();
    }
}
