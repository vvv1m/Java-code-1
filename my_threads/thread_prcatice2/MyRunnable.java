package my_threads.thread_prcatice2;

import java.util.ArrayList;
import java.util.Random;

public class MyRunnable implements Runnable {
    // public static int[] money = {10,5,20,100,200,500,800,2,80,300,700};
    public static ArrayList<Integer> money = new ArrayList<>();
    public static int count;

    @Override
    public void run() {
        //这里可能会疑惑为什么直接写在run方法中就可以达到效果
        //需要知道每个线程对应的栈都是独立的，称为线程栈
        //ArrayList存储在各自的栈中，也就相互独立
        //线程在执行过程中，抢夺CPU的执行权，抢到后会继续运行run方法，而不是重新运行run方法，线程只运行run方法一次
        //可以将run方法简单的理解为各自线程的 main方法
        //所以这里的boxlist在while外定义，就可以达到想要的结果
        ArrayList<Integer> boxlist = new ArrayList<>();
        while (true) {
            Random r = new Random();
            synchronized (MyRunnable.class) {
                Thread t = Thread.currentThread();
                if (count == 0) {
                    // 如果奖池中没有奖了，就停止抽奖
                    System.out.println(t.getName() + boxlist);
                    break;
                } else {
                    // 执行抽奖逻辑
                    int index = r.nextInt(count);
                    // System.out.println(t.getName() + "又产生了一个" + money.get(index) + "大奖");
                    boxlist.add(money.get(index));
                    money.remove(index);
                    count--;

                }
            }
            //让结果更加平均
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
