package my_graphics.graphics2D_demo1;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public class Test extends JFrame{
    public Test(){
        setTitle("Java Graphics Example");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 添加绘图面板
        add(new GraphicsPanel());
        setVisible(true);
    }
    // 绘图面板类
    class GraphicsPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;  // ✅ 在paintComponent中获取
            
            // 设置渲染质量
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);
            
            drawShapes(g2d);
        }
        //绘制的方法
        private void drawShapes(Graphics2D g2d) {
            // 绘制矩形
            Rectangle2D rect = new Rectangle2D.Double(50, 50, 120, 80);
            g2d.setColor(new Color(100, 150, 255));
            g2d.fill(rect);
            g2d.setColor(Color.BLACK);
            g2d.draw(rect);
            
            // 绘制椭圆
            Ellipse2D ellipse = new Ellipse2D.Double(200, 50, 100, 100);
            g2d.setColor(new Color(255, 100, 100));
            g2d.fill(ellipse);
            g2d.setColor(Color.BLACK);
            g2d.draw(ellipse);
            
            // 绘制直线
            Line2D line = new Line2D.Double(50, 200, 300, 200);
            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(4));
            g2d.draw(line);
            
            // 绘制点
            Point2D point1 = new Point2D.Double(100, 250);
            Point2D point2 = new Point2D.Double(200, 250);
            Point2D point3 = new Point2D.Double(300, 250);
            
            g2d.setColor(Color.MAGENTA);
            g2d.fillOval((int)point1.getX() - 6, (int)point1.getY() - 6, 12, 12);
            g2d.fillOval((int)point2.getX() - 6, (int)point2.getY() - 6, 12, 12);
            g2d.fillOval((int)point3.getX() - 6, (int)point3.getY() - 6, 12, 12);
            
            // 添加文本标签
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString("Rectangle", 60, 140);
            g2d.drawString("Ellipse", 210, 160);
            g2d.drawString("Line", 170, 190);
            g2d.drawString("Points", 180, 280);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Test());
    }
}
