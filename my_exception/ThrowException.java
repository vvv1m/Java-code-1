package my_exception;

public class ThrowException {
    public static void main(String[] args) {
        //throws 写在方法定义处，表示声明一个异常，告诉调用者，使用本方法可能会有哪些异常
        //   public void 方法() throws 异常类1，异常类2，...{}
        //   编译型异常必须要写，运行时异常可以不写
        //throw 写在方法内，结束方法，手动抛出异常对象，交给调用者，方法中下面的代码不再执行
        //public void function(){
        //   throw new 异常类();
        //}
    }
}
