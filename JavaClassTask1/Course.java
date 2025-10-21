package JavaClassTask1;

import java.util.Vector;

public class Course {
    private String name;
    private String teacher;
    private int choosenum;
    private  Vector<Student> students = new Vector<>();
    public Course() {
    }

    public Course(String name, String teacher, int choosenum) {
        this.name = name;
        this.teacher = teacher;
        this.choosenum = choosenum;
    }
    public void showAll(){
        System.out.println("课程名称：" + getName() + " 授课老师：" + getTeacher() + " 选课人数：" + getChoosenum());
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
    public void addStudents(Student student){
        if(!students.contains(student)){
            students.add(student);
        }
    }
    public void  showCourseStudents() {
        for (Student student : students) {
            System.out.println("学生姓名：" + student.getName() + " 学号：" + student.getId());
        }
    }
}
