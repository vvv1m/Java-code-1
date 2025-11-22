package my_io.object_stream;

import java.io.Serializable;

public class Base implements Serializable{
    private static final long serialVersionUID = 1L;
    private transient String aexample;
    public static void main(final String[] args) {
       
        //序列化流，是属于字节流的高级流，负责输出数据 ObjectOutputStream
        //与之对应的还有反序列化流，输入流 ObjectInputStream
        //序列化流可以把Java中的对象写到本地文件中，变成我们看不懂的样子
        //  写入的数据可以通过反序列化流读取出来


        //序列化流
        //构造方法
        //public ObjectOutputStream(OutputStream out) 把基本流包装成序列流
        //成员方法
        //public final void writeObject(Object obj) 把对象序列化写出到文件中去

        //小细节
        // 使用对象输出流（序列化流）将对象保存到文件时会出现NotSerializableException异常
        // 需要我们implements Serializable接口
        // Serializable接口中没有抽象方法，专业名称叫做标记型接口
        // 一旦实现了这个接口，就表示当前的类可以被序列化



        //反序列化流/对象操作输入流
        // 可以把序列化到本地的对象读取到程序中来
        //构造方法
        //public ObjectInputStream(InputStream is) 
        //成员方法
        //public Object readObject() 把序列化到本地文件中的对象，读取到程序中来
        //读取之后的对象是Object类型，需要进行强转 

        //细节1
        //  如果一个类可以被序列化（实现了Serializable接口）
        //  那么Java的底层会根据这个类里面所有的内容进行计算，计算出long类型的序列号/版本号
        //  在创建对象的时候和序列化的时候就会把序列号写进去
        //  修改类的代码后，版本号会变，由于文件中的版本号和Javabean中的版本号不匹配，所以会报错
        //  当我们不得不修改Javabean类，又不想更改版本号的时候
        //  可以在最上面，定义Javabean类的时候，手动定义一个版本号
        //  定义方法：private static final long serialVersionUID = 1L;

        //如果你不想让某个变量序列化，那么可以在定义的时候加上瞬态关键字--transient
    }
}
