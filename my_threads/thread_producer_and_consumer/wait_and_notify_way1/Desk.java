package my_threads.thread_producer_and_consumer.wait_and_notify_way1;

public class Desk {
    //控制生产者和消费者执行
    //表示桌子上是否有食物 0-无 1-有
    public static int foodflag = 0;
    //总个数
    //顾客最多吃十次
    public static int count = 10;

    //创建一个锁对象
    //注意：这里不能用Lock，因为Lock不能直接调用wait和notify，只能自建锁对象和同步代码块
    public static Object lock = new Object();
}
