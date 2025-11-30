package my_threads.thread_producer_and_consumer.wait_and_notify_way2;

import java.util.concurrent.ArrayBlockingQueue;

public class WaitAndNotifyDemo2 {
    public static void main(String[] args) {
        //等待唤醒机制的另一种实现方法---阻塞队列方式
        //在厨师和顾客之间有一个管道，厨师将食物放进管道，顾客从管道中获取食物，可以人为规定管道存储数量
        //所谓队列，表示数据在管道之中先进先出
        //所谓阻塞，put数据时，放不进去会等着，也叫做阻塞
        //          take数据时，取出第一个数据，取不到也会等着，也叫做阻塞

        //阻塞队列的继承结构
        //接口
        //Iterable --- Collection --- Queue --- BlockingQueue
        //继承自Iterable说明阻塞队列可以用迭代器或增强for遍历
        //继承Collection说明他是一个集合，Queue是队列，BlockingQueue是阻塞队列

        //阻塞队列是一个接口，不能直接使用
        //其实现类
        //ArrayBlockingQueue --- 底层是数组实现，有界，创建时必须指定队列的长度
        //LinkedBlockingQueue --- 底层是链表，无界，但并非真正无界，最大长度为int最大值


        //接下来利用阻塞队列实现等待唤醒机制
        //细节：生产者和消费者必须使用同一个阻塞队列
        // 1 创建阻塞队列的对象
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        // 2 将该对象传递给Cook和Foodie，这样就可以实现二者共用同一个队列
        //由于没有设置退出条件，这里会无限执行，故不做演示
        //输出结果会出现厨师连续做饭，这是由于打印语句定义在
    }
}
