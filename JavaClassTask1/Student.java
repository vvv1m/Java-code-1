package JavaClassTask1;

import java.util.Vector;

public class Student extends Person {
    private String major;           // 专业
    private String grade;           // 年级
    private Vector<Course> selectedCourses; // 已选课程列表
    // 默认构造方法
    public Student() {
        super();
        this.selectedCourses = new Vector<>();
    }
    // 带参构造方法
    public Student(String name, String id, String major, String grade) {
        super(name, id, "123456");// 默认密码为123456
        this.major = major;
        this.grade = grade;
        this.selectedCourses = new Vector<>();
    }
    // Getter和Setter方法
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public Vector<Course> getSelectedCourses() {
        return selectedCourses;
    }
    public void setSelectedCourses(Vector<Course> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }
    // 选课
    public boolean selectCourse(Course course) {
        if (course == null) {
            System.out.println("课程不存在！");
            return false;
        }
        // 检查是否已经选过这门课
        for (Course selectedCourse : selectedCourses) {
            if (selectedCourse.getName().equals(course.getName())) {
                System.out.println("您已经选过这门课程：" + course.getName());
                return false;
            }
        }
        // 对于选修课，检查人数是否已满
        if (course instanceof Option) {
            Option optionCourse = (Option) course;
            if (optionCourse.getChoosenum() >= optionCourse.getMaxchoosenum()) {
                System.out.println("选修课" + course.getName() + "人数已满，无法选课！");
                return false;
            }
        }
        // 选课成功
        selectedCourses.add(course);
        course.addStudents(this); // 将学生添加到课程的学生列表中
        course.setChoosenum(course.getChoosenum() + 1); // 增加选课人数
        return true;
    }
    // 退课
    public boolean dropCourse(Course course) {
        if (course == null) {
            System.out.println("课程不存在！");
            return false;
        }   
        for (int i = 0; i < selectedCourses.size(); i++) {
            if (selectedCourses.get(i).getName().equals(course.getName())) {
                selectedCourses.remove(i);
                course.setChoosenum(course.getChoosenum() - 1); // 减少选课人数
                System.out.println("退课成功：" + course.getName());
                return true;
            }
        } 
        System.out.println("您没有选过这门课程：" + course.getName());
        return false;
    }
    // 根据课程名称退课
    public boolean dropCourseByName(String courseName) {
        for (int i = 0; i < selectedCourses.size(); i++) {
            if (selectedCourses.get(i).getName().equals(courseName)) {
                Course course = selectedCourses.get(i);
                selectedCourses.remove(i);
                course.setChoosenum(course.getChoosenum() - 1);
                System.out.println("退课成功：" + courseName);
                return true;
            }
        }
        System.out.println("您没有选过这门课程：" + courseName);
        return false;
    }
    // 显示已选课程
    public void showSelectedCourses() {
        System.out.println("========== " + getName() + " 的选课情况 ==========");
        if (selectedCourses.isEmpty()) {
            System.out.println("暂未选择任何课程");
            return;
        }
        
        System.out.println("必修课：");
        for (Course course : selectedCourses) {
            if (course instanceof CoreCourse) {
                System.out.println("- " + course.getName() + " | 教师：" + course.getTeacher() + 
                                 " | 学分：" + ((CoreCourse)course).getCredit());
            }
        }
        
        System.out.println("选修课：");
        for (Course course : selectedCourses) {
            if (course instanceof Option) {
                Option opt = (Option) course;
                System.out.println("- " + course.getName() + " | 教师：" + course.getTeacher() + 
                                 " | 选课人数：" + course.getChoosenum() + "/" + opt.getMaxchoosenum());
            }
        }
    }
    // 计算总学分（仅必修课）
    public int getTotalCredits() {
        int totalCredits = 0;
        for (Course course : selectedCourses) {
            if (course instanceof CoreCourse) {
                totalCredits += ((CoreCourse) course).getCredit();
            }
        }
        return totalCredits;
    }
    // 获取选修课数量
    public int getOptionalCourseCount() {
        int count = 0;
        for (Course course : selectedCourses) {
            if (course instanceof Option) {
                count++;
            }
        }
        return count;
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + getName() + '\'' +
                ", id='" + getId() + '\'' +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                ", selectedCourses=" + selectedCourses.size() +
                '}';
    }
}