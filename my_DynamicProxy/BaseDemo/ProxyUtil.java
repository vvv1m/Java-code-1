package my_DynamicProxy.BaseDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//类的作用：创建一个代理

public class ProxyUtil {
    
    //作用：给一个明星对象创建一个代理
    //参数：被代理的明星对象
    //返回值：给明星创建的代理

    //使用时 
    // 1 获取代理的对象
    //      代理对象 = ProxyUtil.createProxy(要代理的对象)
    // 2 在调用代理的方法 


    public static Star createProxy(Bigstar bigstar){
        //public static Object newProxyInstance(ClassLoader loader, Class<?>[] interface, InvocationHandler h)
        //参数一 --- 用于指定用哪个类加载器，去加载生成的代理类，即将字节码文件加载到内存中
        //参数二 --- 指定接口，这些接口用于指定生成的代理长什么样，即有哪些方法
        //参数三 --- 用来指定生成的代理对象要干什么事情，InvocationHandler是一个接口，利用匿名内部类实现
        
        //参数1：先找到是谁把当前的类加载到内存的 让他再干一件事---加载我们当前的代理
        //参数2：接口
        Star star = (Star)Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                        new Class[]{Star.class},
                        new InvocationHandler() {

                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                //参数1：代理的对象
                                //参数2：要运行的方法
                                //参数3：调用sing方法时传递的实参

                                if("sing".equals(method.getName())){
                                    System.out.println("准备话筒，收钱");
                                }else if("dance".equals(method.getName())){
                                    System.out.println("准备场地，收钱");
                                }
                                //去找大明星唱歌或跳舞
                                //代码表现形式：调用大明星里面唱歌或跳舞的方法
                                return method.invoke(bigstar, args);
                            }
                            
                        }
                        );

        return star;
    }
}
