package Internet_Program.Practice4_add_threads;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static void main(String[] args) throws IOException {
        //get data from InterIO and save at local by FileIO
        ServerSocket ss = new ServerSocket(10000);
        //attention : if send dictory more times, it will have the same name
        //      so we need a class named UUID to get a random str to name my dictory
        

        // when many clients want to connect with server at the same time 
        // the program is not good enough
        // so we can use threads
        while(true){
            Socket socket = ss.accept();
            Thread thread = new Thread(new MyRunnable(socket));
            thread.start();
        }

    }
}
