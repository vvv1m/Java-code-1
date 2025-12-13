package Internet_Program;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class GetDataByGroup1 {
    public static void main(String[] args) throws IOException {
        //创建对象
        MulticastSocket ms = new MulticastSocket(10000);

        //将本机添加到224.0.0.1组中
        InetAddress ia = InetAddress.getByName("224.0.0.1");
        ms.joinGroup(ia);
        
        //创建数据包
        byte[] bytes = new byte[1024];
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length);
        //接受数据
        ms.receive(dp);
        //解析数据
        byte[] data = dp.getData();
        int len = dp.getLength();
        String ip = dp.getAddress().getHostAddress();
        String name = dp.getAddress().getHostName();
        System.out.println(ip + " " + name + " " + new String(data, 0, len));

        //正常组播接收端有多个，这里太麻烦就不演示了，运行时先运行接收端再运行发送端
        //广播就是将单播的发送地址改为255.255.255.255，就是广播
        


    }
}
