package my_reflect;

public class GetFields {
    public static void main(String[] args) {
        //Class类中获取成员变量的方法
        //Field[] getFields()
        //Field[] getDeclaredFields()
        //Field getField(String name)
        //Field getDeclaredField(String name)

        //Field类中用于创建对象的方法
        //void set(Object obj, Object value) 赋值
        //  两个参数，前者是要修改哪个类中的成员变量，后者是要修改成什么
        //Object get(Object obj) 获取值，参数是实例化的对象

        //获取到成员变量的对象后可以获取他的
        // 1 权限修饰符
        //int modifiers = name.getModifiers();
        // 2 变量名
        //String n = name.getName();
        // 3 变量类型
        //Class<?> type = name.getType();
        // 4 获取值，需要先创建对象
        //  创建对象后通过暴力反射可以获取私有成员变量的值
        // 5 修改值
        
    }
}   
