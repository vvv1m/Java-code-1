package my_DynamicProxy.BaseDemo;

public class Demo {
    public static void main(String[] args) {
        //外界想要找大明星唱歌
        // 1 创建代理对象
        Bigstar bigstar = new Bigstar("鸡哥");
        Star proxy = ProxyUtil.createProxy(bigstar);
        // 2 调用唱歌的方法
        String result = proxy.sing("11111");
        System.out.println(result);
        // 3 调用跳舞的方法
        proxy.dance();
    }
}
