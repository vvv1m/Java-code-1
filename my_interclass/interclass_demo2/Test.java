package my_interclass.interclass_demo2;

public class Test {
    public static void main(String[] args) {
        //想要调用method方法，该如何做
        //以前的方法为创建一个子类对象继承Animal，再传递给method
        //但如果这个子类对象只用了这一次，我们就没必要创建
        //于是就可以应用匿名内部类

        method(
            new Animal() {
                @Override
                public void eat(){
                    System.out.println("重写了eat方法");
                }
            }
        );
    }
    public static void method(Animal a){
        a.eat();
    }
}
