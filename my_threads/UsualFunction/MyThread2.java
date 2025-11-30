package my_threads.UsualFunction;

public class MyThread2 extends Thread{

    @Override
    public void run() {
        for(int i = 0; i < 20; i++){
            System.out.println(getName() + "@" + i);
        }
    }
    
}
