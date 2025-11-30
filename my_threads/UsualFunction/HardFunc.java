package my_threads.UsualFunction;

public class HardFunc {
    public static void main(String[] args) throws InterruptedException {
        //setPriority(int newPriorite)  设置线程的优先级
        //final int getPriority()   获取线程的优先级
        //CPU调度方式---抢占式调度，优先级就和这种调度方式有关
        MyRunnable mr = new MyRunnable();
        // Thread t1 = new Thread(mr,"飞机");
        // Thread t2 = new Thread(mr, "坦克");
        // t1.setPriority(10);
        // t2.setPriority(1);
        // t1.start();
        // t2.start();
        //注：优先级不是绝对的，只是概率问题

        //final void setDaemon(boolean on)  设置为守护线程
        //细节：当其他非守护线程结束后，守护线程会陆续结束

        // MyThread1 t1 = new MyThread1();
        // MyThread2 t2 = new MyThread2();
        // t1.setName("女神");
        // t2.setName("备胎");
        //把第二个线程设置为守护线程/备胎线程
        // t2.setDaemon(true);

        // t1.start();
        // t2.start();

        //public static void yield()    出让线程/礼让线程
        
        MyThread1 t1 = new MyThread1();
        MyThread1 t2 = new MyThread1();

        t1.setName("飞机");
        // t2.setName("坦克");
        // t1.start();
        // t2.start();
        t1.start();
        //public static void join() 插入线程/插队线程
        //表示把t1这个线程插入到当前线程之前
        //当前线程：main
        //注意 join方法的使用要在调用线程开始之后，其他线程开始之前
        t1.join();

        for(int i = 0; i < 10; i++){
            System.out.println("main@" + i);
        }

        

    }
}
