package Internet_Program;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendDataByGroup {
    public static void main(String[] args) throws IOException {
        //组播发送数据
        //创建MulticastSocket对象

        //创建对象
        MulticastSocket ms = new MulticastSocket();

        //打包数据
        InetAddress address = InetAddress.getByName("224.0.0.1");
        String str = "HelloWorld";
        byte[] bytes = str.getBytes();
        int port = 10000;
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);

        //发送数据包
        ms.send(dp);

        //释放资源
        ms.close();
    }
}
