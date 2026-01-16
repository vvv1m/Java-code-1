package Internet_Program.small_protect;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MyRunnable implements Runnable {
    Socket socket;
    public static ArrayList<Socket> sockets = new ArrayList<>();
    public MyRunnable(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = "";
            while (true) {
                if ((str = br.readLine()) != null) {
                    String[] strs = str.split("=");
                    if ("FUNCTION1".equals(strs[0])) {
                        // function1 --- login
                        OutputStream os = socket.getOutputStream();
                        os.write(login(strs[1], strs[2], socket).getBytes());
                        // socket.shutdownOutput();
                        os.flush();

                    } else if ("FUNCTION2".equals(str.substring(0, 9))) {
                        // function2 --- chat
                        // need to send by group
                        for (Socket asocket : sockets) {
                            OutputStream senddata = asocket.getOutputStream();
                            senddata.write((str.substring(9) + "\n").getBytes());
                            senddata.flush();
                        }
                    } else if ("FUNCTION4".equals(str)) {
                        // the user choose to exit
                        // remove the user from group
                        
                        OutputStream os = socket.getOutputStream();
                        os.write("EXIT\n".getBytes());
                        os.flush();
                        sockets.remove(socket);
                        break;
                    } else if("FUNCTION3-1".equals(str.substring(0, 11))){
                        OutputStream os = socket.getOutputStream();
                        if(checkUsername(str.substring(11))){
                            os.write("NOEXIST\n".getBytes());
                            os.flush();
                        }else{
                            os.write("EXIST\n".getBytes());
                            os.flush();
                        }
                    }else if("FUNCTION3-2".equals(str.substring(0, 11))){
                        FileWriter fw = new FileWriter("Internet_Program\\small_protect\\account.txt", true);
                        fw.write(str.substring(11) + "\n");
                        fw.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean checkUsername(String username) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Internet_Program\\small_protect\\account.txt")));
        String len;
        while((len = br.readLine()) != null){
            if(username.equals(len.split("=")[0])){
                br.close();
                return false;
            }
        }
        br.close();
        return true;
    }
    public String login(String username, String password, Socket socket) throws IOException {

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
        if (password.equals(line.split("=")[1])) {
            passwordflag = true;
        }
        if (usernameflag && passwordflag) {
            //add new member to group for FUNCTION2
            sockets.add(socket);
            return "PASS\n";
        } else if (!usernameflag) {
            return "NULL\n";
        } else if (usernameflag && !passwordflag) {
            return "WRONG\n";
        } else {
            return "ERROR\n";
        }
    }
}
