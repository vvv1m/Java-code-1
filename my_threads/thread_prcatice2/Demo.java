package my_threads.thread_prcatice2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //一个抽奖池
        //奖项为{10,5,20,100,200,500,800,2,80,300,700}
        //两个抽奖箱线程 抽奖箱1和抽奖箱2
        //随机从抽奖池中获取奖项元素并打印在控制台上

        // Collections.addAll(MyRunnable.money, 10,5,20,100,200,500,800,2,80,300,700);
        // MyRunnable.count = MyRunnable.money.size();
        // MyRunnable mr = new MyRunnable();
        // Thread t1 = new Thread(mr,"抽奖箱1");
        // Thread t2 = new Thread(mr,"抽奖箱2");
        // t1.start();
        // t2.start();

        //追加需求：
        //在每次抽的时候不打印，抽完后一次性随机打印
        //例如
        //在此次抽奖过程中，抽奖箱1共产生了6个奖项，分别为：    最高奖项为  总计额为
        // Collections.addAll(MyRunnable.money, 10,5,20,100,200,500,800,2,80,300,700);
        // MyRunnable.count = MyRunnable.money.size();
        // MyRunnable mr = new MyRunnable();
        // Thread t1 = new Thread(mr,"抽奖箱1");
        // Thread t2 = new Thread(mr,"抽奖箱2");
        // t1.start();
        // t2.start();

        //追加需求
        //抽奖结束后需要比较线程1和2的结果，求出奖金的最大值，并给出这个最大值是在哪个抽奖箱中得到的
        ArrayList<Integer> money = new ArrayList<>();

        Collections.addAll(money, 10,5,20,100,200,500,800,2,80,300,700);
        MyCallable mc = new MyCallable(money);
        FutureTask<ArrayList<Integer>> ft1 = new FutureTask<>(mc);
        FutureTask<ArrayList<Integer>> ft2 = new FutureTask<>(mc);
        Thread t1 = new Thread(ft1, "抽奖箱1");
        Thread t2 = new Thread(ft2, "抽奖箱2");
        t1.start();
        t2.start();
        ArrayList<Integer> list1 = ft1.get();
        ArrayList<Integer> list2 = ft2.get();
        if(Collections.max(list1) < Collections.max(list2)){
            System.out.println("抽奖箱2 " + Collections.max(list2));
        }else{
            System.out.println("抽奖箱1 " + Collections.max(list1));
        }

    }
}
