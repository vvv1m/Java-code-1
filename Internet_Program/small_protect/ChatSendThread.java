package Internet_Program.small_protect;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ChatSendThread extends Thread {
    Socket socket;
    String username;

    public ChatSendThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            while (true) {
                OutputStream os = socket.getOutputStream();
                System.out.println("please input your words(input \"EXIT\" for exiting)");
                String sentence = Client.sc.nextLine();
                if ("EXIT".equals(sentence)) {
                    // need to send message to move socket away from sockets
                    // as function4
                    os.write("FUNCTION4\n".getBytes());
                    break;
                }
                // use str[0-8] as funtion id
                os.write(("FUNCTION2" + username + ":" + sentence + "\n").getBytes());

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
