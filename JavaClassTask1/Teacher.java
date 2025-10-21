package JavaClassTask1;

import java.util.Vector;

public class Teacher extends Person {
    private String department;      // 所属学院/系
    private String title;          // 职称（教授、副教授、讲师等）
    private Vector<Course> teachingCourses; // 任教课程列表
    
    // 默认构造方法
    public Teacher() {
        super();
        this.teachingCourses = new Vector<>();
    }
    
    // 带参构造方法
    public Teacher(String name, String id, String department, String title) {
        super(name, id, "123456"); // 默认密码为123456
        this.department = department;
        this.title = title;
        this.teachingCourses = new Vector<>();
    }
    
    // Getter和Setter方法
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Vector<Course> getTeachingCourses() {
        return teachingCourses;
    }
    
    public void setTeachingCourses(Vector<Course> teachingCourses) {
        this.teachingCourses = teachingCourses;
    }
    
    // 添加任教课程
    public boolean addTeachingCourse(Course course) {
        if (course == null) {
            System.out.println("课程不存在！");
            return false;
        }
        // 检查是否已经在任教这门课
        for (Course teachingCourse : teachingCourses) {
            if (teachingCourse.getName().equals(course.getName())) {
                System.out.println("您已经在任教这门课程：" + course.getName());
                return false;
            }
        }
        // 设置课程的任课教师
        course.setTeacher(getName());
        teachingCourses.add(course);
        System.out.println("成功添加任教课程：" + course.getName());
        return true;
    }
    // 移除任教课程
    public boolean removeTeachingCourse(String courseName) {
        for (int i = 0; i < teachingCourses.size(); i++) {
            if (teachingCourses.get(i).getName().equals(courseName)) {
                Course course = teachingCourses.get(i);
                teachingCourses.remove(i);
                // 可以选择是否清空课程的任课教师
                // course.setTeacher("");
                System.out.println("成功移除任教课程：" + courseName);
                return true;
            }
        }
        System.out.println("您没有任教课程：" + courseName);
        return false;
    }
    // 显示任教课程
    public void showTeachingCourses() {
        System.out.println("========== " + getName() + " 的任教课程 ==========");
        if (teachingCourses.isEmpty()) {
            System.out.println("暂无任教课程");
            return;
        }
        
        System.out.println("必修课：");
        for (Course course : teachingCourses) {
            if (course instanceof CoreCourse) {
                CoreCourse cc = (CoreCourse) course;
                System.out.println("- " + course.getName() + " | 选课人数：" + course.getChoosenum() + 
                                 " | 学分：" + cc.getCredit());
            }
        }
        System.out.println("选修课：");
        for (Course course : teachingCourses) {
            if (course instanceof Option) {
                Option opt = (Option) course;
                System.out.println("- " + course.getName() + " | 选课人数：" + course.getChoosenum() + 
                                 "/" + opt.getMaxchoosenum());
            }
        }
    }
    // 查看某门课程的选课学生（需要与UserList配合使用）
    public void viewCourseStudents(String courseName) {
        Course course = null;
        for (Course c : teachingCourses) {
            if (c.getName().equals(courseName)) {
                course = c;
                break;
            }
        }
        if (course == null) {
            System.out.println("您没有任教课程：" + courseName);
            return;
        }
        System.out.println("========== 课程《" + courseName + "》选课学生 ==========");
        System.out.println("当前选课人数：" + course.getChoosenum());
        course.showCourseStudents();
    }
    
    // 获取任教的必修课数量
    public int getCoreCoursesCount() {
        int count = 0;
        for (Course course : teachingCourses) {
            if (course instanceof CoreCourse) {
                count++;
            }
        }
        return count;
    }
    
    // 获取任教的选修课数量
    public int getOptionalCoursesCount() {
        int count = 0;
        for (Course course : teachingCourses) {
            if (course instanceof Option) {
                count++;
            }
        }
        return count;
    }
    
    // 获取所有任教课程的总选课人数
    public int getTotalStudentCount() {
        int total = 0;
        for (Course course : teachingCourses) {
            total += course.getChoosenum();
        }
        return total;
    }
    
    // 修改课程信息（仅限自己任教的课程）
    public boolean updateCourseInfo(String courseName, String newTeacher, int newChooseNum) {
        for (Course course : teachingCourses) {
            if (course.getName().equals(courseName)) {
                if (newTeacher != null && !newTeacher.trim().isEmpty()) {
                    course.setTeacher(newTeacher);
                }
                if (newChooseNum >= 0) {
                    course.setChoosenum(newChooseNum);
                }
                System.out.println("课程信息更新成功：" + courseName);
                return true;
            }
        }
        System.out.println("您没有任教课程：" + courseName);
        return false;
    }
    
    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + getName() + '\'' +
                ", id='" + getId() + '\'' +
                ", department='" + department + '\'' +
                ", title='" + title + '\'' +
                ", teachingCourses=" + teachingCourses.size() +
                '}';
    }
}