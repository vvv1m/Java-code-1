package Internet_Program.small_protect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("127.0.0.1", 10086);
        try {
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
                    if (login(username, password, socket)) {
                        // enter chat room
                        chat(username, socket);
                        break;
                    }
                } else if (funcnum == 2) {
                    sc.nextLine();
                    // check username valid or not
                    System.out
                            .println("Please input your username(6-18 letters without numbers or special characters)");
                    String username = sc.nextLine();
                    while (!checkUsername(username, socket)) {
                        System.out.println("Please input again");
                        username = sc.nextLine();
                    }
                    System.out.println("Please input your password(one must letter and 2-7 numbers)");
                    String password = sc.nextLine();
                    while (!checkPassword(password)) {
                        System.out.println("please input again");
                        password = sc.nextLine();
                    }
                    sendRegisterMessage(username, password, socket);
                } else if (funcnum == 3) {
                    break;
                } else {
                    System.out.println("Input Error function number !!!");
                }
            }
        } finally {
            // close sc and socket
        }
    }
    //send valid register data to Server for save
    public static void sendRegisterMessage(String username, String password, Socket socket) throws IOException{
        OutputStream os = socket.getOutputStream();
        os.write(("FUNCTION3-2" + username + "=" + password + "\n").getBytes());
    }
    public static boolean checkPassword(String password) {
        String regex = "[a-zA-Z][0-9]{2,7}";
        if (!password.matches(regex)) {
            System.out.println("invaild password, please use one must letter and 2-7 numbers");
            return false;
        }
        return true;
    }
    public static boolean checkUsername(String username, Socket socket) throws IOException {
        String regex1 = "[a-zA-Z]{6,18}";
        if (!username.matches(regex1)) {
            System.out.println("invalid username, please use 6-18 letters without numbers or special characters");
            return false;
        } else {
            // send username to Server, check username unique or not
            OutputStream os = socket.getOutputStream();
            os.write(("FUNCTION3-1" + username + "\n").getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String len;
            if ((len = br.readLine()) != null) {
                if ("NOEXIST".equals(len)) {
                    return true;
                } else if ("EXIST".equals(len)) {
                    System.out.println("The username is exist, please choose another");
                    return false;
                } else {
                    System.out.println("error of check username");
                    return false;
                }
            } else {
                System.out.println("Get Server message error");
                return false;
            }
        }
    }

    public static void chat(String username, Socket socket) throws IOException {
        // two threads --- send and get
        // to get message immediately and send message at any time
        ChatSendThread sendmessage = new ChatSendThread(socket, username);
        sendmessage.setDaemon(true);
        ChatGetThread getmessage = new ChatGetThread(socket);
        sendmessage.start();
        getmessage.start();
    }

    public static boolean login(String username, String password, Socket socket) throws IOException {
        OutputStream os = socket.getOutputStream();
        // use "FUNCTION1" to tell server
        // dont use shutdown because it will make stream stop forever
        os.write(("FUNCTION1" + "=" + username + "=" + password + "\n").getBytes());
        // socket.shutdownOutput();
        // get the return message
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = "";
        if ((result = br.readLine()) != null) {
            if ("PASS".equals(result)) {
                System.out.println("Login successfully!!!");

                // socket.shutdownInput();
                return true;
            } else if ("NULL".equals(result)) {
                System.out.println("no such username, please register first!");
            } else if ("WRONG".equals(result)) {
                System.out.println("wrong password, please try again");
            } else {
                System.out.println("error of login");
            }
        }
        return false;
    }
}
