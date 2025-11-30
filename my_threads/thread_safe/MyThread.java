package my_threads.thread_safe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread extends Thread{
    static int ticket = 0;
    static Lock lock = new ReentrantLock();

    //锁对象一定要是唯一的，一般用当前类的字节码文件
    //static Object object = new Object();
    @Override
    public void run() {
        while(true){
            //同步代码块
            // synchronized(MyThread.class){
            //     if(ticket < 100){
            //         try {
            //             Thread.sleep(10);
            //         } catch (InterruptedException e) {
            //             e.printStackTrace();
            //         }
            //         ticket++;
            //         System.out.println(getName() + "正在卖第" + ticket + "张票");
            //     }else{
            //         break;
            //     }
            // }
            lock.lock();
            try {
                if(ticket < 100){
                        
                        Thread.sleep(10);
                        
                        ticket++;
                        System.out.println(getName() + "正在卖第" + ticket + "张票");
                    }else{
                        break;
                }
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }finally{
                //利用finally中的代码一定会被执行的特性，解决break可能会跳过unlock从而是程序无法停止的bug
                lock.unlock();
            }
        }
    }
}
