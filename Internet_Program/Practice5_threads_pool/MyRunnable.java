package Internet_Program.Practice5_threads_pool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class MyRunnable implements Runnable{
    Socket socket;
    public MyRunnable(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            String dicname = UUID.randomUUID().toString().replace("-", "");
            String sfileadd = "Internet_Program\\Practice4_add_threads\\sdir\\picture_" + dicname + ".png";
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
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
}
