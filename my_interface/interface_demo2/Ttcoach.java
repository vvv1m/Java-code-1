package my_interface.interface_demo2;

public class Ttcoach extends Coach implements SpeakEnglish{
    public Ttcoach(){}
    public Ttcoach(String name, int age){
        super(name, age);
    }
    @Override
    public void teach(){
        System.out.println("学打乒乓球");
    }
    @Override
    public void speakEnglish(){
        System.out.println("说英语");
    }
}
 