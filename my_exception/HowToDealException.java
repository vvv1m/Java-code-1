package my_exception;

public class HowToDealException {
    public static void main(String[] args) {
        //异常处理方式
        //1 JVM默认的处理方式
        //      把异常的名称，异常原因及异常出现的位置等信息输出在控制台上
        //      程序停止运行
        //2 自己处理
        //3 抛出异常
        //      格式 
        //          try{
        //              可能出现异常的代码;
        //          }catch(异常类型 变量名){
        //              处理异常的代码;
        //          }
        //目的 当代码出现异常的时候，可以让程序继续往下执行
        int[] arr = {1,2,3,4};
        try{
            System.out.println(arr[10]);//此处出现异常，程序创建对应类型的异常对象（这里是ArrayIndexOutOfBoundsException）
                                        //new ArrayIndexOutOfBoundsException();
                                        //拿着这个对象跟catch的小括号中对比，看括号中的变量能否接收这个对象
                                        //如果能被接受，就表示该异常被捕获，执行catch里面对应的代码
                                        //当catch里代码执行完毕，继续执行后续代码
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("数组越界");
        }
        System.out.println("1111");
        //几个问题


        //1 try中没有问题，该怎么执行
        // try{
        //     System.out.println(arr[2]);//没有问题，直接执行
        // }catch(ArrayIndexOutOfBoundsException e){
        //     System.out.println("数组越界");
        // }
        //    可以发现 当try中的代码没问题时，会执行完try中的所有代码，不执行catch中的代码，然后继续执行下面的代码


        //2 如果try中可能出现多个问题，该如何执行
        try{
            System.out.println(arr[10]);
            System.out.println(2/0);

        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("数组越界");
        }catch(ArithmeticException e){
            System.out.println("除数不能为0");
        }
        System.out.println("1111");
        //3 如果try中的问题没有被捕获，怎么执行
        //4 如果try中遇到了问题，那么try下面的代码还会执行吗
    }
}
