package my_threads.thread_deadlock;

public class Base {
    public static void main(String[] args) {
        //死锁---在程序当中出现了锁的嵌套
        //例如：两个人吃饭，只有两只筷子
        //a拿到了筷子1，b拿到了筷子2，但只有两个人都拿到一双筷子才能吃一次饭并把筷子放下
        //a在等b放下筷子2，b在等a放下筷子1，双方一直等待，形成死锁
    }
}
