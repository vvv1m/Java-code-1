package JavaClassTask1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        // 1. 程序启动时，首先从文件加载数据
        DataManager.loadData();

        // 2. 如果加载后列表为空（首次运行），则添加一些初始数据
        if (CourseList.getCorelist().isEmpty() && CourseList.getOptionlist().isEmpty() &&
            UserList.getAllStudents().isEmpty() && UserList.getAllTeachers().isEmpty()) {
            
            System.out.println("未找到数据文件或文件为空，正在初始化基础数据...");
            CoreCourse cc1 = new CoreCourse("高数","张三",200 ,4);
            CoreCourse cc2 = new CoreCourse("大学英语","李四",150 ,3);
            CoreCourse cc3 = new CoreCourse("C语言课程设计","王五",165 ,3);
            CourseList.addCourse(cc1);
            CourseList.addCourse(cc2);
            CourseList.addCourse(cc3);
            Option op1 = new Option("军事理论","赵六",200 ,300);
            Option op2 = new Option("艺术鉴赏","孙七",150 ,200);
            CourseList.addCourse(op1);
            CourseList.addCourse(op2);

            Student s1 = new Student("小明", "s001", "计算机科学", "2022级");
            UserList.addStudent(s1);
            Teacher t1 = new Teacher("张三", "t001", "信息学院", "教授");
            UserList.addTeacher(t1);
        }
    
        // 3. 运行主逻辑
        printMenu(login());//login返回具体对象

        // 4. 程序结束前，保存所有数据到文件
        DataManager.saveData();


    }
    public static Person login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String id = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        return UserList.login(id, password);
    }
    public static void adminMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("菜单");
        System.out.println("1添加课程");
        System.out.println("2课程删除");
        System.out.println("3显示课程列表");
        System.out.println("4按上课人数排序");
        System.out.println("5设置课程教师");
        //System.out.println("6查找对应老师课程");
        System.out.println("6添加老师或学生");
        System.out.println("7删除老师或学生");
        System.out.println("8显示学生列表");
        System.out.println("9显示教师列表");
        System.out.println("0退出");
        while(true){
            System.out.println("请输入要进行的操作");
            int functionnum = sc.nextInt();
            sc.nextLine();
            if(functionnum == 0) {
                break;
            }else if(functionnum == 1){
                addCourse();
            }else if(functionnum == 2){
                System.out.println("输入要删除的课程名");
                String delecourse = sc.nextLine();
                CourseList.removeCourse(CourseList.findCourse(delecourse));
            }else if(functionnum == 3){
                CourseList.showCourse();
            }else if(functionnum == 4){
                CourseList.sortByChoosePeople();
            }else if(functionnum == 5){
                System.out.println("请输入想要设置的课程");
                String name = sc.nextLine();
                System.out.println("请输入老师的姓名");
                String teacher = sc.nextLine();
                CourseList.setTeacher(name, teacher);
                
            }else if(functionnum == 6){
                System.out.println("添加老师或学生？输入1添加学生，输入2添加老师");
                int addnum = sc.nextInt();
                if(addnum == 1){
                    sc.nextLine();
                    System.out.println("请输入学生姓名");
                    String name = sc.nextLine();
                    System.out.println("请输入ID");
                    String id = sc.nextLine();
                    System.out.println("请输入所属专业全称");
                    String major = sc.nextLine();
                    System.out.println("请输入年级");
                    String grade = sc.nextLine();
                    Student student = new Student(name, id, major, grade);
                    UserList.addStudent(student);
                    CourseList.getCorelist().forEach(student::selectCourse);
                } else if(addnum == 2){
                    sc.nextLine();
                    System.out.println("请输入老师姓名");
                    String name = sc.nextLine();
                    System.out.println("请输入ID");
                    String id = sc.nextLine();
                    System.out.println("请输入所属学院/系全称");
                    String department = sc.nextLine();
                    System.out.println("请输入职称");
                    String title = sc.nextLine();
                    Teacher teacher = new Teacher(name, id, department, title);
                    UserList.addTeacher(teacher);
                }
                
            }else if(functionnum == 7){
                System.out.println("删除老师或学生？输入1删除学生，输入2删除老师");
                int deletenum = sc.nextInt();
                if(deletenum == 1){
                    sc.nextLine();
                    System.out.println("请输入要删除的学生ID");
                    String studentId = sc.nextLine();
                    UserList.removeStudent(studentId);
                } else if(deletenum == 2){
                    sc.nextLine();
                    System.out.println("请输入要删除的教师ID");
                    String teacherId = sc.nextLine();
                    UserList.removeTeacher(teacherId);
                }
            }else if(functionnum == 8){
                UserList.showAllStudents();
            }else if(functionnum == 9){
                UserList.showAllTeachers();
            }else {
                System.out.println("操作数错误");
            }
        }
    }
    public static void teacherMenu(Person person){
        Teacher teacher = (Teacher) person;
        Scanner sc = new Scanner(System.in);
        System.out.println("菜单");
        System.out.println("1显示任教课程");
        System.out.println("2查看课程上课学生名单");
        System.out.println("3修改登录密码");
        System.out.println("0退出");
        while(true){
            System.out.println("请输入要进行的操作");
            int functionnum = sc.nextInt();
            sc.nextLine();
            if(functionnum == 0) {
                break;
            }else if(functionnum == 1){
                teacher.showTeachingCourses();
            }else if(functionnum == 2){
                System.out.println("请输入课程名称");
                String coursename = sc.nextLine();
                teacher.viewCourseStudents(coursename);
            }else if(functionnum == 3){
                System.out.println("请输入新密码");
                String newpassword = sc.nextLine();
                teacher.setPassword(newpassword);
            }else{
                System.out.println("操作数错误");
            }
        }
    }
    public static void studentMenu(Person person){
        Student student = (Student) person;
        Scanner sc = new Scanner(System.in);
        System.out.println("菜单");
        System.out.println("1选课");
        System.out.println("2显示已选课程");
        System.out.println("3修改登录密码");
        System.out.println("0退出");
        while(true){
            System.out.println("请输入要进行的操作");
            int functionnum = sc.nextInt();
            sc.nextLine();
            if(functionnum == 0) {
                break;
            }else if(functionnum == 1){
                System.out.println("请输入要选的课程名");
                String coursename = sc.nextLine();
                student.selectCourse(CourseList.findCourse(coursename));
                System.out.println("选课完成");
            }else if(functionnum == 2){
                student.showSelectedCourses();
            }else if(functionnum == 3){
                System.out.println("请输入新密码");
                String newpassword = sc.nextLine();
                student.setPassword(newpassword);
            }else{
                System.out.println("操作数错误");
            }
        }
    }
    public static void printMenu(Person person){
        if(person instanceof User){
            adminMenu();
        }else if(person instanceof Teacher){
            teacherMenu(person);
        }else if(person instanceof Student){
            studentMenu(person);
        }else{
            System.out.println("身份错误");
        }
        
    }
    public static void addCourse(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("a添加必修课程，b添加选修课程，c退出");
            String addfuncnum = sc.nextLine();
            if(addfuncnum.equals("c")) {
                break;
            }else if(addfuncnum.equals("a")){
                System.out.println("请输入课程名字");
                String name = sc.nextLine();
                System.out.println("请输入授课老师");
                String teacher = sc.nextLine();
                System.out.println("请输入选课人数");
                int choosenum = sc.nextInt();
                System.out.println("请输入学分");
                int credit = sc.nextInt();
                CoreCourse cc = new CoreCourse(name, teacher, choosenum, credit);
                CourseList.addCourse(cc);
                sc.nextLine();
            }else if(addfuncnum.equals("b")){
                System.out.println("请输入课程名字");
                String name = sc.nextLine();
                System.out.println("请输入授课老师");
                String teacher = sc.nextLine();
                System.out.println("请输入选课人数");
                int choosenum = sc.nextInt();
                System.out.println("请输入最大选课人数");
                int maxchoosenum = sc.nextInt();
                Option cc = new Option(name, teacher, choosenum, maxchoosenum);
                CourseList.addCourse(cc);
                sc.nextLine();
            }else{
                System.out.println("操作数错误");
            }

        }
    }
}
