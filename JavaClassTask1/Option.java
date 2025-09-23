package JavaClassTask1;

public class Option extends Course{
    private int maxchoosenum;
    public Option(){
        
    }
    public Option(String name, String teacher, int choosenum, int maxchoosenum) {
        super(name, teacher, choosenum);
        this.maxchoosenum = maxchoosenum;
    }
    public int getMaxchoosenum() {
        return maxchoosenum;
    }
    public void setMaxchoosenum(int maxchoosenum) {
        this.maxchoosenum = maxchoosenum;
    }
    @Override
    public void showAll(){
        System.out.println("课程名称：" + getName() + " 授课老师：" + getTeacher() + " 选课人数：" + getChoosenum()
            + " 最大选课人数：" + maxchoosenum);
    }
}
