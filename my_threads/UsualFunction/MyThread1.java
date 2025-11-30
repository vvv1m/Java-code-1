package my_threads.UsualFunction;

public class MyThread1 extends Thread{

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.println(getName() + "@" + i);
            //添加礼让,让两个线程均匀执行（尽可能）
            // Thread.yield();
        }
    }
    
}
