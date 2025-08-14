package my_polymorphism.polymorphism_demo4;

public class Test {

    public static void main(String[] args) {
        Person p1 = new Person("老王", 30);
        Person p2 = new Person("老李", 25);
        Dog d = new Dog(2, "黑");
        Cat c = new Cat(3, "灰");
        p1.keepPet(d, "骨头");
        p2.keepPet(c, "鱼");
    }

}
