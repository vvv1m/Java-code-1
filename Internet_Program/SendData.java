package Internet_Program;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendData {
    public static void main(String[] args) throws IOException {
        //发送数据
        // 1 创建DatagramSocket对象(快递公司)
        //细节：
        //  绑定端口，以后就通过这个端口向外发送数据
        //  空参---从所有的可用端口中随机选一个使用
        //  有参---指定端口号进行绑定
        DatagramSocket ds = new DatagramSocket();

        // 2 打包数据
        InetAddress ia = InetAddress.getByName("127.0.0.1");
        String str = "Hello World";
        byte[] bytes = str.getBytes();
        int port = 10086;
        //四个参数依次对应 要发送的数据对应的字节数组 数组长度 目标主机 要发送到的端口号
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length,ia,port);

        // 3 发送数据
        ds.send(dp); 

        // 4 释放资源
        ds.close();

    }
}
