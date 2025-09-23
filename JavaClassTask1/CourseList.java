package JavaClassTask1;
import java.util.Collections;
import java.util.Vector;

public class CourseList {
    private static Vector<CoreCourse> corelist = new Vector<>();
    private static Vector<Option> optionlist = new Vector<>();
    public static boolean addCourse(Course course){
        if(course instanceof CoreCourse){
            return corelist.add((CoreCourse)course);
        }else if(course instanceof Option){
            return optionlist.add((Option)course);
        }
        return false;
    }
    public static boolean removeCourse(Course course){
        if(course instanceof CoreCourse){
            return corelist.remove((CoreCourse)course);
        }else if(course instanceof Option){
            return optionlist.remove((Option)course);
        }
        return false;
    }
    public static void showCourse(){
        System.out.println("必修课课表如下");
        for(int i = 0; i < corelist.size(); i++){
            corelist.get(i).showAll();
        }
        System.out.println("选修课课表如下");
        for(int i = 0; i < corelist.size(); i++){
            optionlist.get(i).showAll();
        }
    }
    public static void sortByChoosePeople(){
        Collections.sort(corelist, (Course o1, Course o2) -> {
            return o1.getChoosenum() - o2.getChoosenum();
        });
        Collections.sort(optionlist, (Course o1, Course o2) -> {
            return o1.getChoosenum() - o2.getChoosenum();
        });
    }
    public static void setTeacher(String coursename, String teachername){
        for(int i = 0; i < corelist.size(); i++){
            if(corelist.get(i).getName().equals(coursename)){
                corelist.get(i).setTeacher(teachername);
                return;
            }
        }
        for(int i = 0; i < optionlist.size(); i++){
            if(optionlist.get(i).getName().equals(coursename)){
                optionlist.get(i).setTeacher(teachername);
            }
        }
    }
    public static void searchCourseByTeacher(String teacher){
        for(int i = 0; i < corelist.size(); i++){
            if(corelist.get(i).getTeacher().equals(teacher)){
                corelist.get(i).showAll();
            }
        }
        for(int i = 0; i < optionlist.size(); i++){
            if(optionlist.get(i).getTeacher().equals(teacher)){
                corelist.get(i).showAll();
            }
        }
    }
    public static Course findCourse(String name){
        for(int i = 0; i < corelist.size(); i++){
            if(corelist.get(i).getName().equals(name)){
                return (Course)corelist.get(i);
            }
            if(optionlist.get(i).getName().equals(name)){
                return (Course)optionlist.get(i);
            }
        }
        return null;
    }
    public static void printCourseByTeacher(String teacher){
        for(int i = 0; i < corelist.size(); i++){
            if(corelist.get(i).getTeacher().equals(teacher)){
                corelist.get(i).showAll();
            }
            if(optionlist.get(i).getTeacher().equals(teacher)){
                optionlist.get(i).showAll();
            }
        }

    }
}
