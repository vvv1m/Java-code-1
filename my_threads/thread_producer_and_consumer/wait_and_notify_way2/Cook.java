package my_threads.thread_producer_and_consumer.wait_and_notify_way2;

import java.util.concurrent.ArrayBlockingQueue;

public class Cook extends Thread{
    ArrayBlockingQueue<String> queue;
    
    public Cook(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            //不断地把食物放到队列中
            //这里是不用加锁的
            //因为在ArrayBlockingQueue底层的put方法中已经加上了锁
            try {
                queue.put("食物");
                System.out.println("厨师放了一碗食物");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
    
}
