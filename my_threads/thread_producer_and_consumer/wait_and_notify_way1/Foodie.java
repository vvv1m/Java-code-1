package my_threads.thread_producer_and_consumer.wait_and_notify_way1;

public class Foodie extends Thread{

    @Override
    public void run() {
        //在编写run代码时一定要按照四步套路来写
        // 1 循环
        // 2 synchronized的同步代码块，后续可以改成同步方法或者改用Lock
        // 3 判断共享数据是否到了末尾，建议先写到了末尾的情况
        // 4 写没有到末尾的情况
        while(true){
            synchronized(Desk.lock){
                if(Desk.count == 0){
                    break;
                }else{
                    //判断桌子上是否有食物
                    if(Desk.foodflag == 0){
                        //没有等待，等待方法需要用锁对象调用
                        try {
                            Desk.lock.wait();//为了让当前线程与锁绑定
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        //后续唤醒的时候就可以通过下列方法
                        //Desk.lock.notifyAll();唤醒所有跟这个锁绑定的线程
                    }else{
                        //将吃的总数-1
                        Desk.count--;
                        //有就开吃
                        System.out.println("吃吃吃，还能吃" + Desk.count + "碗");
                        //吃完唤醒厨师继续做
                        Desk.lock.notifyAll();
                        //修改桌子的状态
                        Desk.foodflag = 0;
                    }
                }
            }
        }
    }

}
