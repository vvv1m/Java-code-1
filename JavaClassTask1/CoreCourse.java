package JavaClassTask1;

public class CoreCourse extends Course{
    private int credit;
    public CoreCourse() {
    }
    public CoreCourse(String name, String teacher, int choosenum, int credit) {
        super(name, teacher, choosenum);
        this.credit = credit;
    }
    
    @Override
    public void showAll(){
        System.out.println("课程名称：" + getName() + " 授课老师：" + getTeacher() + " 选课人数：" + getChoosenum()
            + " 学分：" + credit);
    }
    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }

}
