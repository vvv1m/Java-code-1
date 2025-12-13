package Internet_Program;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GetData {
    public static void main(String[] args) throws IOException {
        //接受数据
        // 1 创建DatagramSocket（快递公司）
        //细节：
        //  接受的时候一定要绑定端口
        //  要与发送的端口一致
         DatagramSocket ds1 = new DatagramSocket(10086);
        // 2 接收数据包
        byte[] bytes1 = new byte[1024];
        DatagramPacket dp1 = new DatagramPacket(bytes1, bytes1.length);
        //该方法时阻塞的，当程序执行到这里后会死等，等发送端发送消息
        ds1.receive(dp1);
        // 3 解析数据包
        byte[] data = dp1.getData();
        int len = dp1.getLength();
        InetAddress getaddress = dp1.getAddress();
        int getport = dp1.getPort();
        System.out.println(new String(data,0,len) + " 长度为" + len + " 来自于" + getaddress + " " + getport);
        // 4 释放资源
        ds1.close();
    }
}
