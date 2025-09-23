package JavaClassTask1;

import java.util.Vector;

public class UserList {
    // 管理员用户对象（唯一一个）
    private static User administrator;
    
    // 学生列表对象
    private static Vector<Student> studentList = new Vector<>();
    
    // 教师列表对象
    private static Vector<Teacher> teacherList = new Vector<>();
    
    // 静态初始化块，创建默认管理员
    static {
        administrator = new User("admin", "admin001", "123456");
    }
    
    // 获取管理员对象
    public static User getAdministrator() {
        return administrator;
    }
    
    // 设置管理员对象
    public static void setAdministrator(User admin) {
        administrator = admin;
    }
    
    // ========== 学生管理方法 ==========
    
    // 添加学生
    public static boolean addStudent(Student student) {
        if (student == null) {
            return false;
        }
        // 检查学生ID是否已存在
        for (Student s : studentList) {
            if (s.getId().equals(student.getId())) {
                System.out.println("学生ID已存在：" + student.getId());
                return false;
            }
        }
        return studentList.add(student);
    }
    
    // 删除学生
    public static boolean removeStudent(String studentId) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId().equals(studentId)) {
                studentList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // 根据ID查找学生
    public static Student findStudentById(String id) {
        for (Student student : studentList) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    
    // 根据姓名查找学生
    public static Student findStudentByName(String name) {
        for (Student student : studentList) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }
    
    // 获取所有学生
    public static Vector<Student> getAllStudents() {
        return new Vector<>(studentList); // 返回副本，保护原数据
    }
    
    // ========== 教师管理方法 ==========
    
    // 添加教师
    public static boolean addTeacher(Teacher teacher) {
        if (teacher == null) {
            return false;
        }
        // 检查教师ID是否已存在
        for (Teacher t : teacherList) {
            if (t.getId().equals(teacher.getId())) {
                System.out.println("教师ID已存在：" + teacher.getId());
                return false;
            }
        }
        return teacherList.add(teacher);
    }
    
    // 删除教师
    public static boolean removeTeacher(String teacherId) {
        for (int i = 0; i < teacherList.size(); i++) {
            if (teacherList.get(i).getId().equals(teacherId)) {
                teacherList.remove(i);
                return true;
            }
        }
        return false;
    }
    
    // 根据ID查找教师
    public static Teacher findTeacherById(String id) {
        for (Teacher teacher : teacherList) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }
    
    // 根据姓名查找教师
    public static Teacher findTeacherByName(String name) {
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equals(name)) {
                return teacher;
            }
        }
        return null;
    }
    
    // 获取所有教师
    public static Vector<Teacher> getAllTeachers() {
        return new Vector<>(teacherList); // 返回副本，保护原数据
    }
    
    // ========== 通用用户管理方法 ==========
    
    // 用户登录验证
    public static Person login(String id, String password) {
        // 检查管理员
        if (administrator.getId().equals(id) && administrator.getPassword().equals(password)) {
            return administrator;
        }
        
        // 检查学生
        for (Student student : studentList) {
            if (student.getId().equals(id) && student.getPassword().equals(password)) {
                return student;
            }
        }
        
        // 检查教师
        for (Teacher teacher : teacherList) {
            if (teacher.getId().equals(id) && teacher.getPassword().equals(password)) {
                return teacher;
            }
        }
        
        return null; // 登录失败
    }
    
    // 修改用户密码
    public static boolean changePassword(String id, String oldPassword, String newPassword) {
        Person user = findUserById(id);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }
    
    // 根据ID查找任意类型用户
    public static Person findUserById(String id) {
        // 检查管理员
        if (administrator.getId().equals(id)) {
            return administrator;
        }
        
        // 检查学生
        Student student = findStudentById(id);
        if (student != null) {
            return student;
        }
        
        // 检查教师
        Teacher teacher = findTeacherById(id);
        if (teacher != null) {
            return teacher;
        }
        
        return null;
    }
    
    // ========== 显示方法 ==========
    
    // 显示所有学生信息
    public static void showAllStudents() {
        System.out.println("========== 所有学生信息 ==========");
        if (studentList.isEmpty()) {
            System.out.println("暂无学生信息");
            return;
        }
        for (int i = 0; i < studentList.size(); i++) {
            System.out.println((i + 1) + ". " + studentList.get(i).toString());
        }
    }
    
    // 显示所有教师信息
    public static void showAllTeachers() {
        System.out.println("========== 所有教师信息 ==========");
        if (teacherList.isEmpty()) {
            System.out.println("暂无教师信息");
            return;
        }
        for (int i = 0; i < teacherList.size(); i++) {
            System.out.println((i + 1) + ". " + teacherList.get(i).toString());
        }
    }
    
    // 显示所有用户统计信息
    public static void showUserStatistics() {
        System.out.println("========== 用户统计信息 ==========");
        System.out.println("管理员数量：1");
        System.out.println("学生数量：" + studentList.size());
        System.out.println("教师数量：" + teacherList.size());
        System.out.println("用户总数：" + (1 + studentList.size() + teacherList.size()));
    }
    
    // ========== 初始化测试数据方法 ==========
    
    // 初始化一些测试用户数据
    public static void initTestData() {
        // 添加测试学生
        addStudent(new Student("张三", "2021001", "123456", "计算机科学与技术", "2021级"));
        addStudent(new Student("李四", "2021002", "123456", "软件工程", "2021级"));
        addStudent(new Student("王五", "2021003", "123456", "网络工程", "2021级"));
        
        // 添加测试教师
        addTeacher(new Teacher("刘教授", "T001", "123456", "计算机学院", "教授"));
        addTeacher(new Teacher("陈老师", "T002", "123456", "软件学院", "副教授"));
        addTeacher(new Teacher("赵讲师", "T003", "123456", "网络学院", "讲师"));
    }
}