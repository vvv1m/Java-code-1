package Internet_Program.small_protect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("127.0.0.1", 10001);
        Scanner sc = new Scanner(System.in);
        while (true) {
            // print suggestion
            System.out.println("Welcome to chat room!!!");
            System.out.println("Input number to choose your action: 1-login 2-register 3-exit");
            int funcnum = sc.nextInt();
            if (funcnum == 1) {
                sc.nextLine();
                // send username and password to Server and get message
                System.out.println("Please input your username");
                String username = sc.nextLine();
                System.out.println("Please input your password");
                String password = sc.nextLine();
                if(login(username, password, socket)){
                    
                }
            }else if(funcnum == 2){

            }else if(funcnum == 3){
                break;
            }else{
                System.out.println("Input Error function number !!!");
            }
        }
        sc.close();
    }
    public static boolean login(String username, String password, Socket socket) throws IOException{
        OutputStream os = socket.getOutputStream();
        //use "FUNCTION1" to tell server 
        os.write(("FUNCTION1"+"="+username+"="+password).getBytes());
        socket.shutdownOutput();
        //get the return message
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int b = 0;
        String result = "";
        while((b = br.read()) != -1){
            result = result + (char)b;
        }
        if("PASS".equals(result)){
            System.out.println("Login successfully!!!");
            return true;
        }else if("NULL".equals(result)){
            System.out.println("no such username, please register first!");
        }else if("WRONG".equals(result)){
            System.out.println("wrong password, please try again");
        }else{
            System.out.println("error of login");
        }
        return false;
    }
}
