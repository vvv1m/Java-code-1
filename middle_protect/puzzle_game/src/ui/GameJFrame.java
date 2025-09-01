package middle_protect.puzzle_game.src.ui;
import javax.swing.*;
//游戏界面

public class GameJFrame extends JFrame{
    public GameJFrame(){
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化图片
        initImage();

        //让界面显示
        this.setVisible(true);
    }
    //初始化图片
    private void initImage(){
        //创建一个图片ImageIcon的对象
        ImageIcon icon = new ImageIcon("D:\\Java\\Java code\\middle_protect\\puzzle_game\\image\\animal\\animal3\\3.jpg");
        //创建一个JLabel的对象（管理容器）
        JLabel jLabel = new JLabel(icon);
        //把管理容器添加到界面中
        this.add(jLabel);
    }
    private void initJFrame(){
        //设置界面的宽高
        this.setSize(603,680);
        //设置界面的标题
        this.setTitle("拼图 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private void initJMenuBar(){
        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu payJMenu = new JMenu("充值入口");
        
        //创建选项下面的条目对象
        JMenuItem replayItem = new JMenuItem("重新开始");
        JMenuItem reloginItem = new JMenuItem("重新登录");
        JMenuItem closeItem = new JMenuItem("关闭游戏");

        JMenuItem accountItem = new JMenuItem("个人");

        JMenuItem payItem = new JMenuItem("充值");

        //将每一个选项的条目添加到选项当中
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        payJMenu.add(payItem);
        //将菜单的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        jMenuBar.add(payJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }
}
