package my_static.a01demo3;

import java.util.ArrayList;

public class StudentUtil {
    private StudentUtil(){}
    public static int getMaxAge(ArrayList<Student> sl){
        int atemp = 0;
        for(int i = 0; i < sl.size(); i++){
            if(sl.get(i).getAge() > atemp){
                atemp = sl.get(i).getAge();
            }
        }
        return atemp;
    }
}
