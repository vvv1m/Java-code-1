package my_interface.interface_demo2;

public class Ttplayer extends Player implements SpeakEnglish{
    public Ttplayer(){}
    public Ttplayer(String name, int age){
        super(name, age);
    }
    @Override
    public void study(){
        System.out.println("学打乒乓球");
    }
    @Override
    public void speakEnglish(){
        System.out.println("说英语");
    }
}
