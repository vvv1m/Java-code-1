package Internet_Program.Practice1_send_more_times;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        //send data many times 
        Socket socket = new Socket("127.0.0.1", 10000);
        OutputStream os = socket.getOutputStream();
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("write your words, please(write \"exit\" for exiting)");
            String str = sc.nextLine();
            if("exit".equals(str)){
                break;
            }else{
                os.write((str+"\r\n").getBytes());
                os.flush();
            }

        }
        sc.close();
        socket.close();
    }       
}
