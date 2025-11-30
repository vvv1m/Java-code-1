package my_threads.thread_imple3;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        //求1-100的和
        int sum = 0;
        for(int i = 0; i < 101; i++){
            sum = sum + i;
        }
        return sum;
    }
    
}
