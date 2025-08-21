package my_interface.interface_demo3;

// public class InterImpl implements Inter{
//     //现在我只想使用Inter中的第五个方法，但由于接口的特性，必须重写全部十个方法，太过繁琐
//     //这时候就可以应用适配器设计模式，建立InterAdapter
// }
//不再应用接口Inter，而是改为继承InterAdapter
public class InterImpl extends InterAdapter{
    @Override
    public void method5(){
        
    }
}
