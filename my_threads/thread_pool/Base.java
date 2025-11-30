package my_threads.thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Base {
    //线程池
    //示例
    //a早上吃饭，但是没有碗（线程），所以买了一个碗，吃完饭摔了（线程结束），到晚上吃晚饭，还是没有碗，于是又买了一个碗，吃完饭摔了
    //存在问题
    // 1 每次都要买碗，浪费时间
    // 2 每次都摔碗，浪费资源
    //解决方案
    //  准备一个碗柜，买碗后放到碗柜里
    //  对应到代码就是要准备一个容器用来存放线程---线程池
    //  当提交一个任务时，线程池创建一个线程，任务执行完再把这个线程放回线程池中，有其他任务就不需要再新建线程，而是直接用旧的线程即可
    //  如果线程不够用了，线程池就会创建新的线程
    //  线程池有上限，上限可以手动设置

    //主要核心原理
    // 1 创建一个池子，池子为空 
    // 2 当提交一个任务时，线程池创建一个线程，任务执行完再把这个线程放回线程池中，有其他任务就不需要再新建线程，而是直接用旧的线程即可
    // 3 如果提交任务的时候，池子中没有空闲线程，也无法创建新线程，任务就会排队等待

    public static void main(String[] args) {
        //代码实现
        // 1 创建一个线程池
        // 2 提交任务
        // 3 所有的任务执行完毕，关闭线程池
        //Executors：线程池的工具类通过调用方法返回不同类型的线程池
        //  public static ExecutorService newCachedThreadPool() 创建一个没有上限的线程池，其实上限是21亿多个，不过电脑不支持创建那么多线程
        //  public static ExecutorService newFixedThreadPool(int nThreads) 创建有上限的线程池 

        // // 1 获取线程池对象
        // //ExecutorService pool1 = Executors.newCachedThreadPool();
        // ExecutorService pool1 = Executors.newFixedThreadPool(3);
        // // 2 提交任务
        // pool1.submit(new MyRunnable());
        // pool1.submit(new MyRunnable());
        // pool1.submit(new MyRunnable());
        // pool1.submit(new MyRunnable());
        // pool1.submit(new MyRunnable());
        // // 3 销毁线程池
        // pool1.shutdown();
        //自定义线程池
        //将线程池比作一个饭店，一对一服务，那么就会有以下七个核心元素，与自定义线程池的核心元素对应
        // 1 正式员工数量（不能被辞退，除非饭店倒闭） --- 核心线程数量 不能小于0
        // 2 餐厅最大员工数（最大-正式=临时） --- 线程池中最大线程的数量 大于等于核心线程数量 不能小于0
        // 3 临时员工空闲多长时间被辞退（值） --- 空闲时间（值） 不能为0
        // 4 临时员工空闲多长时间被辞退（单位） --- 空闲时间（单位） 用TimeUnit指定
        // 5 排队的顾客的最大数量 --- 阻塞队列 不能为null
        // 6 从哪里招人 --- 创建线程的方式 不能为null
        // 7 当排队人数过多，超出顾客请下次再来（拒绝服务） --- 要执行的任务过多时的解决方案 不能为null

        //细节
        //  什么时候才创建临时线程：核心线程都在忙，且队伍已经排满
        //  先提交的任务是否一定先执行：不一定，有可能先提交的在排队，后提交的已经通过临时线程先执行了
        //  如果任务数量超过了核心线程数+临时线程数+队伍数，那么多出的任务就会执行任务拒绝策略


        //任务拒绝策略有四种
        //  ThreadPoolExecutor.AbortPolicy --- 默认策略：丢弃任务并抛出RejectedExecutionException
        //  ThreadPoolExecutor.DiscardPolicy --- 丢弃任务，但是不抛出异常，不推荐
        //  ThreadPoolExecutor.DiscardOldestPolicy --- 丢弃队列中等待最久的任务，然后把该任务加入队列
        //  ThreadPoolExecutor.CallerRunsPolicy --- 调用任务的run方法，绕过线程池直接执行 


        //自定义一个线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
            3,                   //核心线程数量
            6,                //最大线程数量
            60,                 //时间值
            TimeUnit.SECONDS,                  //时间单位
            new ArrayBlockingQueue<>(3),//阻塞队列
            Executors.defaultThreadFactory(), //创建线程工厂
            new ThreadPoolExecutor.AbortPolicy()//拒绝策略，是新建了一个ThreadPollExecutor的内部类
        );
        //接下来就跟正常的线程池一样使用即可

        //那么线程池多大才合适呢
        //  在CPU密集型运算中（项目中计算数量比较多，读取本地文件或者数据库较少） --- 最大并行数+1
        //      名词解释--最大并行数
        //          一个4核8线程的CPU 有四个大脑能同时的并行的做四件事情，通过英特尔发明的超线程技术，就可以把原本的四个大脑虚拟成八个，即8线程
        //          其最大并行数就为8
        //          加一是为了保证如果当前线程由于夜缺失故障或者是一些其他原因导致线程暂停，可以用这个加一的线程顶上，保证CPU的时钟周期不被浪费
        //  在IO密集型运算中（读取本地文件或者数据库较多） --- 最大并行数*期望CPU利用率* （总时间（CPU计算时间+等待时间）/CPU计算时间）
    }
}
