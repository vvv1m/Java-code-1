package JavaClassTask1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CoreCourse cc1 = new CoreCourse("高数","张三",200 ,4);
        CoreCourse cc2 = new CoreCourse("大学英语","李四",150 ,3);
        CoreCourse cc3 = new CoreCourse("C语言课程设计","王五",165 ,3);
        CourseList.addCourse(cc1);
        CourseList.addCourse(cc2);
        CourseList.addCourse(cc3);
        Option op1 = new Option("军事理论","张三",200 ,300);
        Option op2 = new Option("xxxx","李四",150 ,200);
        Option op3 = new Option("aaaa","王五",165 ,200);
        CourseList.addCourse(op1);
        CourseList.addCourse(op2);
        CourseList.addCourse(op3);

        printMenu();


    }   
    public static void printMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("菜单");
        System.out.println("1添加课程");
        System.out.println("2课程删除");
        System.out.println("3显示课程列表");
        System.out.println("4按上课人数排序");
        System.out.println("5设置课程教师");
        System.out.println("6查找对应老师课程");
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
                System.out.println("请输入老师姓名");
                String teacher = sc.nextLine();
                CourseList.printCourseByTeacher(teacher);
            }else{
                System.out.println("操作数错误");
            }
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
            }else{
                System.out.println("操作数错误");
            }

        }
    }
}
