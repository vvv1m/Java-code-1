package my_threads.thread_prcatice2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class MyCallable implements Callable<ArrayList<Integer>>{
    public ArrayList<Integer> money = new ArrayList<>();
    public int count;
    public MyCallable(ArrayList<Integer> money) {
        this.money = money;
        this.count = money.size();
    }

    @Override
    public ArrayList<Integer> call() throws Exception {
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
            Thread.sleep(10);
            
        }
        if(boxlist.size() == 0){
            return null;
        }else{
            return boxlist;
        }
    }
}
