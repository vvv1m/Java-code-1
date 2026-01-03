package Internet_Program.Practice5_threads_pool;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class Server {
    public static void main(String[] args) throws IOException {
        //get data from InterIO and save at local by FileIO
        ServerSocket ss = new ServerSocket(10000);
        //attention : if send dictory more times, it will have the same name
        //      so we need a class named UUID to get a random str to name my dictory
        

        // when many clients want to connect with server at the same time 
        // the program is not good enough
        // so we can use threads


        //new question:when we create thread and delete thread , it cost a lot
        //so we can use threadpool
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            3,
            16,
            69,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(3),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
            );
        while(true){
            Socket socket = ss.accept();
            pool.submit(new MyRunnable(socket));
            
        }

    }
}
