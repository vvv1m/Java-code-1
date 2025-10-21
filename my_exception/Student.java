package my_exception;
public class Student {
    String name;
    int age;
    
    public Student() {
    }
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if(age < 18 || age > 40){
            //System.out.println("年龄超出范围"); 调用者无法得知判断结果，这时候我们就可以返回一个异常
            throw new RuntimeException("年龄超出范围");
        }else{
            this.age = age;
        }
        
    }
    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }
    
}
