package my_interclass.interclass_demo1;

public class Test {
    public static void main(String[] args) {
        Outer.Inner oi = new Outer().new Inner(); 
        oi.show();
    }
}
