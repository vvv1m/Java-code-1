package JavaClassTask1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.stream.Collectors;

public class DataManager {

    // 定义数据文件的路径
    private static final String CORE_COURSES_FILE = "JavaClassTask1\\Data\\core_courses.txt";
    private static final String OPTION_COURSES_FILE = "JavaClassTask1\\\\Data\\option_courses.txt";
    private static final String STUDENTS_FILE = "JavaClassTask1\\\\Data\\students.txt";
    private static final String TEACHERS_FILE = "JavaClassTask1\\\\Data\\teachers.txt";
    private static final String DELIMITER = ","; // 使用逗号作为数据分隔符

    // 从文件加载所有数据
    public static void loadData() {
        System.out.println("正在从 TXT 文件加载数据...");
        loadCourses();
        loadStudents();
        loadTeachers();
        
        rebuildAssociations();
        System.out.println("数据加载完成！");
    }

    // 保存所有数据到文件
    public static void saveData() {
        System.out.println("正在将数据保存到 TXT 文件...");
        saveCourses();
        saveStudents();
        saveTeachers();
        System.out.println("数据保存成功！");
    }

    private static void loadCourses() {
        // 加载必修课
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(CORE_COURSES_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                // 格式: name,teacher,choosenum,credit
                CoreCourse course = new CoreCourse(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                CourseList.addCourse(course);
            }
        } catch (FileNotFoundException e) {
            // 文件不存在是正常情况，首次运行时会发生
        } catch (IOException e) {
            System.err.println("读取必修课文件失败: " + e.getMessage());
        }

        // 加载选修课
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(OPTION_COURSES_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                // 格式: name,teacher,choosenum,maxchoosenum
                Option course = new Option(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                CourseList.addCourse(course);
            }
        } catch (FileNotFoundException e) {
            // 文件不存在是正常情况
        } catch (IOException e) {
            System.err.println("读取选修课文件失败: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(STUDENTS_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                // 格式: name,id,major,grade,password (密码默认为123456)
                Student student = new Student(data[0], data[1], data[2], data[3]);
                student.setPassword(data[4]);
                UserList.addStudent(student);
            }
        } catch (FileNotFoundException e) {
            // 文件不存在是正常情况
        } catch (IOException e) {
            System.err.println("读取学生文件失败: " + e.getMessage());
        }
    }

    private static void loadTeachers() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TEACHERS_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                // 格式: name,id,department,title,password (密码默认为123456)
                Teacher teacher = new Teacher(data[0], data[1], data[2], data[3]);
                teacher.setPassword(data[4]);
                UserList.addTeacher(teacher);
            }
        } catch (FileNotFoundException e) {
            // 文件不存在是正常情况
        } catch (IOException e) {
            System.err.println("读取教师文件失败: " + e.getMessage());
        }
    }
    // 新增方法：重建关联
    private static void rebuildAssociations() {
        // 重建学生选课关系
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(STUDENTS_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length > 5) { // 如果有选课信息
                    Student student = UserList.findStudentById(data[1]);
                    if (student != null) {
                        for (int i = 5; i < data.length; i++) {
                            Course course = CourseList.findCourseByName(data[i]);
                            if (course != null) {
                                student.selectCourse(course);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("重建学生选课关系失败: " + e.getMessage());
        }

        // 重建教师任教关系
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(TEACHERS_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length > 5) { // 如果有任教信息
                    Teacher teacher = UserList.findTeacherById(data[1]); // 假设UserList有此方法
                    if (teacher != null) {
                        for (int i = 5; i < data.length; i++) {
                            Course course = CourseList.findCourseByName(data[i]);
                            if (course != null) {
                                teacher.addTeachingCourse(course); // 假设Teacher有此方法
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("重建教师任教关系失败: " + e.getMessage());
        }
    }
    private static void saveCourses() {
        // 保存必修课
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CORE_COURSES_FILE, false), StandardCharsets.UTF_8))) { // false表示覆盖写
            for (CoreCourse course : CourseList.getCorelist()) {
                String line = String.join(DELIMITER, course.getName(), course.getTeacher(),
                        String.valueOf(course.getChoosenum()), String.valueOf(course.getCredit()));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("保存必修课文件失败: " + e.getMessage());
        }

        // 保存选修课
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OPTION_COURSES_FILE, false), StandardCharsets.UTF_8))) { // false表示覆盖写
            for (Option course : CourseList.getOptionlist()) {
                String line = String.join(DELIMITER, course.getName(), course.getTeacher(),
                        String.valueOf(course.getChoosenum()), String.valueOf(course.getMaxchoosenum()));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("保存选修课文件失败: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(STUDENTS_FILE, false), StandardCharsets.UTF_8))) {
            for (Student student : UserList.getAllStudents()) {
                // 获取选课列表的课程名称
                String selectedCoursesStr = student.getSelectedCourses().stream()
                        .map(Course::getName)
                        .collect(Collectors.joining(DELIMITER));

                String line = String.join(DELIMITER, student.getName(), student.getId(), student.getMajor(), student.getGrade(), student.getPassword());
                
                if (!selectedCoursesStr.isEmpty()) {
                    line += DELIMITER + selectedCoursesStr;
                }
                
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("保存学生文件失败: " + e.getMessage());
        }
    }
    private static void saveTeachers() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TEACHERS_FILE, false), StandardCharsets.UTF_8))) {
            for (Teacher teacher : UserList.getAllTeachers()) {
                // 获取任教课程列表的课程名称
                String teachingCoursesStr = teacher.getTeachingCourses().stream()
                        .map(Course::getName)
                        .collect(Collectors.joining(DELIMITER));

                String line = String.join(DELIMITER, teacher.getName(), teacher.getId(), teacher.getDepartment(), teacher.getTitle(), teacher.getPassword());

                if (!teachingCoursesStr.isEmpty()) {
                    line += DELIMITER + teachingCoursesStr;
                }

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("保存教师文件失败: " + e.getMessage());
        }
    }
}