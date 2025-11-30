package my_threads.thread_imple2;

public class MyRun implements Runnable{

    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            //这里就不能直接调用getName方法
            //那么就需要通过其他方法区分线程
            //获取到当前线程的对象
            Thread t = Thread.currentThread();
            System.out.println(t.getName() + "HelloWorld");
        }
    }
    
}
