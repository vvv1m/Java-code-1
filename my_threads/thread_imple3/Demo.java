package my_threads.thread_imple3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //第三种实现方式 利用Callable接口和Future接口方式实现
        //特点：可以获取到多线程运行的结果
        // 1 创建一个类MyCallable实现Callable接口
        // 2 重写call（有返回值，表示多线程运行的结果）
        // 3 创建MyCallable的对象（表示多线程要执行的任务）
        // 4 创建Future的实现类FutureTask的对象，用来管理多线程运行的结果
        // 5 创建Thread类的对象，并启动（表示线程）

        MyCallable mc = new MyCallable();
        //mc被FutureTask代理
        FutureTask<Integer> ft = new FutureTask<>(mc);
        //注意这里需要传递FutureTask的对象
        Thread t1 = new Thread(ft);
        t1.start();
        //获取到多线程运行的结果
        Integer a = ft.get();
        System.out.println(a);
        
    }
}
