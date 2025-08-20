package my_interface.interface_demo2;

public class Test {
    public static void main(String[] args) {
        Bplayer bp = new Bplayer("zhangsan", 20);
        System.out.println(bp.getName() + ", " + bp.getAge());
        bp.study();

        Ttplayer ttp = new Ttplayer("lisi", 20);
        System.out.println(ttp.getName() + ", " + ttp.getAge());
        ttp.study();
        ttp.speakEnglish();

        Bcoach bc = new Bcoach("wangwu", 20);
        System.out.println(bc.getName() + ", " + bc.getAge());
        bc.teach();

        Ttcoach ttc = new Ttcoach("asan", 20);
        System.out.println(ttc.getName() + ", " + ttc.getAge());
        ttc.teach();
        ttc.speakEnglish();
    }
}
