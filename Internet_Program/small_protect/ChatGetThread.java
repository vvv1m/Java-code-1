package Internet_Program.small_protect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatGetThread extends Thread{
    Socket socket;
    public ChatGetThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        while(true){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                if((line = br.readLine()) != null){
                    if(line.equals("EXIT")) break;
                    System.out.println(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
