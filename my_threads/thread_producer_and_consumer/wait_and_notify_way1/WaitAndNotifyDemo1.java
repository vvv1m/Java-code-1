package my_threads.thread_producer_and_consumer.wait_and_notify_way1;

public class WaitAndNotifyDemo1 {
    public static void main(String[] args) {
        //完成生产者消费者机制的代码
        //创建线程对象
        Cook c = new Cook();
        Foodie f = new Foodie();

        c.setName("厨师");
        f.setName("顾客");
        //开启线程
        c.start();
        f.start();
    }
}
