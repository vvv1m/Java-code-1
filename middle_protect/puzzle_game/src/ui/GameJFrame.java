package middle_protect.puzzle_game.src.ui;
import java.util.Random;

import javax.swing.*;
import java.awt.event.*;
//游戏界面
import javax.swing.border.BevelBorder;

public class GameJFrame extends JFrame implements KeyListener, ActionListener{
    //创建二维数组，管理数据，用于加载图片
    int[][] data = new int[4][4];
    //0---空白块的坐标
    int x;
    int y;
    String path = "middle_protect\\puzzle_game\\image\\animal\\animal3\\";
    //统计步数
    int step = 0;
    int[][] win = {
        {1,2,3,4},
        {5,6,7,8},
        {9,10,11,12},
        {13,14,15,0}
    };
    int girlNum = 1;
    int animalNum = 1;
    int sportNum = 1;

    JMenuItem replayItem = new JMenuItem("重新开始");
    JMenuItem reloginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountItem = new JMenuItem("个人");

    JMenuItem payItem = new JMenuItem("充值");

    JMenuItem beautifulgirlItem = new JMenuItem("美女");
    JMenuItem animalItem = new JMenuItem("动物");
    JMenuItem sportItem = new JMenuItem("运动");

    public GameJFrame(){
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据（打乱）
        initData();


        //初始化图片
        initImage();
        
        //让界面显示
        this.setVisible(true);
    }
    //初始化数据
    private void initData(){
        int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r = new Random();
        //获得随机索引
        for(int i = 0; i < tempArr.length; i++){
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        for(int i = 0; i < tempArr.length; i++){
            data[i / 4][i % 4] = tempArr[i];
            if(tempArr[i] == 0){
                x = i / 4;
                y = i % 4;
            }
        }
    }
    //初始化图片
    private void initImage(){
        //清空所有图片
        this.getContentPane().removeAll();
        
        //细节：先加载的图片在上方，后加载的图片塞在下面，比较反直觉
        //相对路径是相对当前项目而言的
        //如果胜利则要显示胜利图标
        if(vectory()){
            JLabel vec = new JLabel(new ImageIcon("middle_protect\\puzzle_game\\image\\win.png"));
            vec.setBounds(203, 283, 197, 73);
            this.getContentPane().add(vec);
        }
        
        //显示步数
        JLabel stepJLabel = new JLabel("步数：" + step);
        stepJLabel.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepJLabel);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                
                //创建一个图片ImageIcon的对象
                //创建一个JLabel的对象（管理容器）
                JLabel jLabel = new JLabel(new ImageIcon(path + data[i][j] +".jpg"));
                //指定图片位置
                jLabel.setBounds(105*j + 83, 105*i + 134, 105, 105);
                //给图片添加边框
                //0：让图片凸起来   1：让图片凹下去
                jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);
            }
        }
        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("middle_protect\\puzzle_game\\image\\background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);
        //刷新界面
        this.getContentPane().repaint();
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
        //取消默认的居中放置位置，取消了才会按xy轴的形式添加组件
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this); 
    }
    
    private void initJMenuBar(){
        //创建整个的菜单对象
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");
        JMenu payJMenu = new JMenu("充值入口");
        JMenu changeImageJMenu = new JMenu("更换图片");

        //创建选项下面的条目对象
        

        //将每一个选项的条目添加到选项当中
        functionJMenu.add(replayItem);
        functionJMenu.add(reloginItem);
        functionJMenu.add(closeItem);

        functionJMenu.add(changeImageJMenu);

        aboutJMenu.add(accountItem);

        payJMenu.add(payItem);

        changeImageJMenu.add(beautifulgirlItem);
        changeImageJMenu.add(animalItem);
        changeImageJMenu.add(sportItem);

        //给条目绑定事件
        replayItem.addActionListener(this);
        reloginItem.addActionListener(this);
        closeItem.addActionListener(this);

        accountItem.addActionListener(this);

        payItem.addActionListener(this);

        beautifulgirlItem.addActionListener(this);
        animalItem.addActionListener(this);
        sportItem.addActionListener(this);

        //将菜单的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);
        jMenuBar.add(payJMenu);
        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);



    }
    //按住不松时会调用此方法
    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        if(code == 65){
            //把界面中的图片全删除
            this.getContentPane().removeAll();
            //加载第一张完整的图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);

            //加载背景图片
            //添加背景图片
            JLabel background = new JLabel(new ImageIcon("middle_protect\\puzzle_game\\image\\background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);


            //刷新界面
            this.getContentPane().repaint();
        }
    }
    //松开时会调用此方法
    @Override
    public void keyReleased(KeyEvent e) {
        //如果胜利，此方法直接结束，不执行下列动作
        if(vectory()){
            return;
        }
        //对上下左右进行判断
        //左 37 上 38 右 39 下 40
        int code = e.getKeyCode();
        System.out.println(code);
        if(code == 37){
            //System.out.println("向左移动");
            if(y + 1 < 4){
                data[x][y] = data[x][y+1];
                data[x][y+1]= 0;
                y++;
            }
            step++;
            initImage(); 
        }else if(code == 38){
            //System.out.println("向上移动");
            if(x + 1 < 4){
                data[x][y] = data[x+1][y];
                data[x+1][y] = 0;
                x++;
            }
            step++;
            initImage(); 
        }else if(code == 39){
            //System.out.println("向右移动");
            if(y - 1 >= 0){
                data[x][y] = data[x][y-1];
                data[x][y-1] = 0;
                y--;
            }
            step++;
            initImage(); 
        }else if(code == 40){
            //System.out.println("向下移动");
            if(x - 1 >= 0){
                data[x][y] = data[x-1][y];
                data[x-1][y] = 0;
                x--;
            }
            step++;
            initImage(); 
        }else if(code == 65){
            initImage();
        }else if(code == 87){
            data = new int[][]{
                {1,2,3,4},
                {5,6,7,8},
                {9,10,11,12},
                {13,14,15,0}
            };
            initImage();
            x = y = 3;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }
    
    //判断胜利
    public boolean vectory(){
        for(int i = 0; i < data.length; i++){
            for(int j = 0; j < data[i].length; j++){
                if(data[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
   
   
   
    //编写菜单功能逻辑
    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        Object obj = e.getSource();
        if(obj == replayItem){
            System.out.println("重新游戏");
            //计步清零
            step = 0;
            //再次打乱数据
            initData();
            //初始化图片
            initImage();

        }else if(obj == reloginItem){
            System.out.println("重新登录");
            //关闭当前游戏界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();

        }else if(obj == closeItem){
            System.out.println("关闭游戏");
            System.exit(0);

        }else if(obj == accountItem){
            System.out.println("关于我们");
            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            //创建一个管理容器的弹窗对象
            JLabel jLabel = new JLabel(new ImageIcon("middle_protect\\puzzle_game\\image\\about.jpg"));
            //设置图片位置和宽高（相对弹框
            jLabel.setBounds(0, 0, 500, 682);
            //添加到弹窗中
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(700, 700);
            //置顶
            jDialog.setAlwaysOnTop(true);
            //居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭则无法操作下面的界面
            jDialog.setModal(true);
            //让弹框显示
            jDialog.setVisible(true);


        }else if(obj == payItem){
            System.out.println("充值入口");
            //创建一个弹框对象
            JDialog jDialog = new JDialog();
            //创建一个管理容器的弹窗对象
            JLabel jLabel = new JLabel(new ImageIcon("middle_protect\\puzzle_game\\image\\pay.png"));
            //设置图片位置和宽高（相对弹框
            jLabel.setBounds(0, 0, 468, 318);
            //添加到弹窗中
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(500, 500);
            //置顶
            jDialog.setAlwaysOnTop(true);
            //居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭则无法操作下面的界面
            jDialog.setModal(true);
            //让弹框显示
            jDialog.setVisible(true);
        }else if(obj == beautifulgirlItem){
            System.out.println("图片更新为美女");
            path = "middle_protect\\puzzle_game\\image\\girl\\girl" + girlNum + "\\";
            if(girlNum + 1 > 13) {
                girlNum = 1;
            }else{
                girlNum++;
            }
            initImage();
        }else if(obj == animalItem){
            System.out.println("图片更新为动物");
            path = "middle_protect\\puzzle_game\\image\\animal\\animal" + animalNum + "\\";
            if(animalNum + 1 > 8) {
                animalNum = 1;
            }else{
                animalNum++;
            }
            initImage();
        }else if(obj == sportItem){
            System.out.println("图片更新为运动");
            path = "middle_protect\\puzzle_game\\image\\sport\\sport" + sportNum + "\\";
            if(sportNum + 1 > 10) {
                sportNum = 1;
            }else{
                sportNum++;
            }
            initImage();
        }
        
    } 
    
}
