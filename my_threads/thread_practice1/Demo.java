package my_threads.thread_practice1;

public class Demo {
    
    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();
        Thread t1 = new Thread(mr, "Thread1");
        Thread t2 = new Thread(mr, "Thread2");
        Thread t3 = new Thread(mr, "Thread3");
        Thread t4 = new Thread(mr, "Thread4");
        Thread t5 = new Thread(mr, "Thread5");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
