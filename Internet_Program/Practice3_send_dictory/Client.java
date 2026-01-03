package Internet_Program.Practice3_send_dictory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {

        //get data by FileIO from local and send data by InterIO to Server 
        Socket socket = new Socket("127.0.0.1", 10000);
        String cfileadd = "Internet_Program\\Practice3_send_dictory\\cdir\\微信图片_20250407082218.png";
        OutputStream os = socket.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(cfileadd));
        byte[] bytes = new byte[1024*1024];
        int len = 0;
        //get and send
        while((len = bis.read(bytes)) != -1){
            os.write(bytes, 0, len);
        }
        bis.close();
        socket.shutdownOutput();
        //get successful message and print
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int c = 0;
        while((c = br.read()) != -1){
            System.out.print((char)c);
        }
        socket.close();
    }
}
