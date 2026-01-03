package Internet_Program.Practice3_send_dictory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;


public class Server {
    public static void main(String[] args) throws IOException {
        //get data from InterIO and save at local by FileIO
        ServerSocket ss = new ServerSocket(10000);
        //attention : if send dictory more times, it will have the same name
        //      so we need a class named UUID to get a random str to name my dictory
        String dicname = UUID.randomUUID().toString().replace("-", "");
        String sfileadd = "Internet_Program\\Practice3_send_dictory\\sdir\\picture_" + dicname + ".png";
        Socket socket = ss.accept();
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(sfileadd));
        byte[] bytes = new byte[1024*1024];
        int len = 0;
        while((len = bis.read(bytes)) != -1){
            bos.write(bytes, 0, len);
        }
        bos.close();

        //send successful message to client
        OutputStream os = socket.getOutputStream();
        os.write("succeed to get data!!!".getBytes());
        socket.close();
        ss.close();
    }
}
