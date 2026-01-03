package Internet_Program.small_protect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10001);
        Socket socket = ss.accept();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        int b = 0;
        String str = "";
        while ((b = isr.read()) != -1) {
            str = str + (char) b;
        }
        String[] strs = str.split("=");
        if ("FUNCTION1".equals(strs[0])) {
            // function1 --- login
            OutputStream os = socket.getOutputStream();
            os.write(login(strs[1], strs[2]).getBytes());
            socket.shutdownOutput();

        }


        ss.close();
    }

    public static String login(String username, String password) throws IOException {

        BufferedReader bis = new BufferedReader(
                new InputStreamReader(new FileInputStream("Internet_Program\\small_protect\\account.txt")));
        String line = "";
        boolean usernameflag = false;
        boolean passwordflag = false;
        while ((line = bis.readLine()) != null) {
            if (username.equals(line.split("=")[0])) {
                usernameflag = true;
                break;
            }
        }
        bis.close();
        if(password.equals(line.split("=")[1])){
            passwordflag = true;
        }
        if(usernameflag && passwordflag){
            return "PASS";
        }else if(!usernameflag){
            return "NULL";
        }else if(usernameflag && !passwordflag){
            return "WRONG";
        }else{
            return "ERROR";
        }
    }
}
