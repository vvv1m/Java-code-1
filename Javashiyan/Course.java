package Javashiyan;

public class Course {
    String name;
    String teacher;
    int choosenum;
    public Course() {
    }
    public Course(String name, String teacher, int choosenum) {
        this.name = name;
        this.teacher = teacher;
        this.choosenum = choosenum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public int getChoosenum() {
        return choosenum;
    }
    public void setChoosenum(int choosenum) {
        this.choosenum = choosenum;
    }
    @Override
    public String toString() {
        return "Course [name=" + name + ", teacher=" + teacher + ", choosenum=" + choosenum + "]";
    }
    
}
