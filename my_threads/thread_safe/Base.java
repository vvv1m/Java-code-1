package my_threads.thread_safe;

public class Base {
    public static void main(String[] args) {
        //线程安全
        //小例子：三个窗口卖一百张电影票，很显然会出现一些问题
        //  1 相同的票出现了许多次
        //      线程在执行的时候具有随机性，CPU的控制权随时有可能被其他线程抢走
        //  2 出现了超出范围的票
        //      该问题与问题一产生的原因相似
        //解决办法：
        //  利用同步代码块将操作共享数据的代码锁起来
        //  会用到关键字
        // synchronized(锁){
        //      操作共享数据的代码
        //      
        //  }
        //锁的特点：1 锁默认打开，有一个线程进去了，锁自动关闭
        //          2 里面的代码全部执行完毕，线程出来，锁自动打开
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread();
        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");
        t1.start();
        t2.start();
        t3.start();
        //同步代码块的一些细节
        //  同步代码块不能写在循环的外面
        //  synchronized的锁对象一定要是唯一的，不唯一相当于没有锁
        //  一般我们会将存在锁的类的字节码文件作为锁对象

        //如果想要把一个方法中的代码全部锁起来，那就没有必要使用同步代码块，可以使用同步方法
        //同步方法---就是把synchronized关键字加到方法上
        //格式：修饰符 synchronized 返回值类型 方法名 (方法参数){......}
        //同步方法有两个小特点：1 同步方法锁住方法中所有代码
        //                      2 锁对象不能自己指定 非静态：this，也就是调用者 静态：当前类的字节码文件对象


        //学完同步方法后可以回答之前StringBuilder和StringBuffer的区别
        //观察源码可以发现StringBuffer的源码中的成员方法全都是同步方法，所以在多线程时使用StringBuffer比较安全

        //在JDK5以后提供了一个新的锁对象Lock
        //Lock实现提供比使用synchronized方法和语句可以获得广泛的锁定操作
        //Lock中提供了获得锁和释放锁的方法
        //void lock()：获得锁
        //void unlock()：释放锁
        //Lock是接口，不能直接实例化，这里采用它的实现类Reentrantlock来实例化
    }
}
