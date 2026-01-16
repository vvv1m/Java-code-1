package my_reflect.UseOfReflectDemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Demo {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //根据配置文件，获取到哪个类就创建哪个类的对象，获取到哪个方法就运行哪个方法

        // 1 get propfile message
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("my_reflect\\UseOfReflectDemo\\prop.properties");
        // read message from prop to collect
        prop.load(fis);

        fis.close();
        System.out.println(prop);
        // 2 get package name and class name and method name
        String classname = (String)prop.get("classname");
        String methodname = (String)prop.get("method");

        // 3 create object and run method
        Class clazz = Class.forName(classname);
        
        //      get constructors
        Constructor con = clazz.getDeclaredConstructor();
        Object obj = con.newInstance();
        //      get method and run 
        Method method = clazz.getDeclaredMethod(methodname);
        method.setAccessible(true);
        method.invoke(obj);
        

    }
}
