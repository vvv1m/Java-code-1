package Internet_Program.TCP;

import java.net.Socket;

public class Base {
    public static void main(String[] args) {
        //可靠的传输协议
        //建立Socket 通过Socket产生IO流进行网络通信
        
        //书写步骤
        //  客户端
        // 1 创建客户端的Socket对象（Socket）与指定服务端连接
        //      Socket(String host, int port)
        // 2 获取输出流 写数据
        //      OutputStream getOutputStream()
        // 3 释放资源
        //      void close()

        //  服务端
        // 1 创建服务端的Socket对象（ServerSocket）
        //      ServerSocket(int port) //与服务器端口保持一致
        // 2 监听客户端连接，返回一个Socket对象
        //      Socket accept()
        // 3 获取输入流，读数据，并把数据显示在控制台
        //      InputStream getInputStream()
        // 4 释放资源
        //      void close();
    }
}
