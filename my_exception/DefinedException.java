package my_exception;

public class DefinedException extends RuntimeException{
    //自定义异常 定义步骤
        //1 定义异常类，名称要见名知义
        //2 写继承关系
        //3 空参构造
        //4 带参构造
    public DefinedException(){
    }
    public DefinedException(String message){
        super(message);
    }
}
