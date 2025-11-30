package my_threads.UsualFunction;

public class SimpleFunc {
        //String getName()  返回此线程的名字
        //void setName()    设置线程的名字（构造方法也可设置）
        //  细节1---如果我们没有给线程设置名字，线程本身也是有默认的名字的
        //          格式：Thread-X（X为序号，从0开始）
        //  细节2---如果我们想设置名字，可以用setName也可以用构造方法
        //static Thread currentThread() 获取当前线程的对象
        //  细节1---当JVM虚拟机启动之后，会自动的启动多条线程
        //          其中有一条叫做main线程
        //          他的作用就是调用main方法，并执行里面的所有代码
        //          
        //static Thread sleep(long time)    让线程休眠指定的时间，单位是毫秒
        //  细节1---哪条线程执行到这个方法，哪个线程就会在这里停留对应的时间
        //  细节2---方法的参数就表示睡眠的时间，单位--毫秒
        //  细节3---当时间到了之后，线程会自动醒来，继续执行下面的其他代码
        public static void main(String[] args) {
            
        }
}
