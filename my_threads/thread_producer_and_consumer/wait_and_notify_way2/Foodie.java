package my_threads.thread_producer_and_consumer.wait_and_notify_way2;

import java.util.concurrent.ArrayBlockingQueue;

public class Foodie extends Thread{
    ArrayBlockingQueue<String> queue;
    
    public Foodie(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            //不断从阻塞队列中获取面条
            try {
                //take底层也有锁，所以不要再加锁
                String food = queue.take();
                System.out.println("顾客获取到一份" + food);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
