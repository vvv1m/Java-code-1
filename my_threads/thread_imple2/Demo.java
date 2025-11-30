package my_threads.thread_imple2;

public class Demo {
    public static void main(String[] args) {
        //多线程的第二种实现方法
        //利用Runnable接口实现
        // 1 自己定义一个类实现Runnable接口
        // 2 重写里面的run方法
        // 3 创建自己类的对象
        // 4 创建一个Thread类对象，开启线程

        //表示多线程要执行的任务
        MyRun mr = new MyRun();
        //创建线程对象,将要执行的任务传递给Thread对象
        Thread t1 = new Thread(mr);
        Thread t2 = new Thread(mr);
        //设置名字
        t1.setName("1.");
        t2.setName("2.");
        t1.start();
        t2.start();


    }
}
