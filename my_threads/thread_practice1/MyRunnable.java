package my_threads.thread_practice1;

import java.math.BigDecimal;//进行四舍五入
import java.math.RoundingMode;
import java.util.Random;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        synchronized (MyRunnable.class) {
            // 如果不是前三个人，则不获得金钱
            if (Money.count < 3) {
                System.out.println(t.getName() + "没抢到");
                Money.count--;
            } else {
                // 如果是第三个人，获得剩下的所有钱
                if (Money.count == 3) {
                    System.out.println(t.getName() + "抢到了" + Money.money + "元");
                    Money.count--;
                } else {
                    Random r = new Random();
                    BigDecimal getmoney = BigDecimal.valueOf(r.nextDouble(Money.money.subtract(BigDecimal.valueOf(Money.count-3).multiply(Money.MIN_MONEY)).doubleValue()));
                    getmoney = getmoney.setScale(2, RoundingMode.HALF_UP);
                    System.out.println(t.getName() + "抢到了" + getmoney + "元");
                    Money.money = Money.money.subtract(getmoney);
                    Money.count--;
                }
            }
        }
    }

}
