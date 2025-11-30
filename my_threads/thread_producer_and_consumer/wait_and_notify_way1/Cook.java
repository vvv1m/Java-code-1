package my_threads.thread_producer_and_consumer.wait_and_notify_way1;

public class Cook extends Thread{

    @Override
    public void run() {
        while(true){
            synchronized(Desk.lock){
                if(Desk.count == 0){
                    break;
                }else{
                    if(Desk.foodflag == 1){
                        try {
                            Desk.lock.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("做了一些食物");
                        Desk.foodflag = 1;
                        Desk.lock.notifyAll();
                    }
                }
            }
        }
    }
    
}
