package my_reflect.FunctionToGetClassDemo;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        //way 1 
        //Class.forname("whole class name") --- package name and class name
        //the most usual
        Class clazz1 = Class.forName("my_reflect.FunctionToGetDemo.Student");
        System.out.println(clazz1);

        //way 2
        //use to pass on as parameter
        Class clazz2 = Student.class;

        System.out.println(clazz2);

        //way 3
        //when we have a object
        Student s = new Student();

        Class clazz3 = s.getClass();
        System.out.println(clazz3);
    }
}
