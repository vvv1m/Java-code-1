package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        try {
            // 1. 服务器端验证
            if (!validateInput(username, password, confirmPassword, email, request, response)) {
                return;
            }
            
            // 2. 检查用户名是否已存在
            if (userDAO.isUsernameExists(username)) {
                response.sendRedirect("register.jsp?error=" + 
                    java.net.URLEncoder.encode("用户名已存在", "UTF-8") +
                    "&username=" + username + 
                    "&email=" + email + 
                    "&phone=" + (phone != null ? phone : ""));
                return;
            }
            
            // 3. 检查邮箱是否已被使用
            if (userDAO.isEmailExists(email)) {
                response.sendRedirect("register.jsp?error=" + 
                    java.net.URLEncoder.encode("该邮箱已被注册", "UTF-8") +
                    "&username=" + username + 
                    "&email=" + email + 
                    "&phone=" + (phone != null ? phone : ""));
                return;
            }
            
            // 4. 密码加密
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            // 5. 创建用户对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPassword);
            user.setEmail(email);
            user.setPhone(phone);
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setStatus("active");
            
            // 6. 保存到数据库
            boolean success = userDAO.registerUser(user);
            
            if (success) {
                // 注册成功，重定向到登录页面
                response.sendRedirect("register.jsp?success=true");
            } else {
                // 注册失败
                response.sendRedirect("register.jsp?error=" + 
                    java.net.URLEncoder.encode("注册失败，请稍后重试", "UTF-8") +
                    "&username=" + username + 
                    "&email=" + email + 
                    "&phone=" + (phone != null ? phone : ""));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("系统错误：" + e.getMessage(), "UTF-8"));
        }
    }
    
    /**
     * 验证输入参数
     */
    private boolean validateInput(String username, String password, String confirmPassword,
                                  String email, HttpServletRequest request, 
                                  HttpServletResponse response) throws IOException {
        
        // 验证用户名
        if (username == null || username.trim().isEmpty()) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("用户名不能为空", "UTF-8"));
            return false;
        }
        
        if (username.length() < 2 || username.length() > 20) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("用户名长度必须在2-20个字符之间", "UTF-8") +
                "&username=" + username);
            return false;
        }
        
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("用户名只能包含字母、数字和下划线", "UTF-8") +
                "&username=" + username);
            return false;
        }
        
        // 验证密码
        if (password == null || password.isEmpty()) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("密码不能为空", "UTF-8") +
                "&username=" + username);
            return false;
        }
        
        if (password.length() < 6) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("密码长度至少6个字符", "UTF-8") +
                "&username=" + username);
            return false;
        }
        
        // 验证确认密码
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("两次输入的密码不一致", "UTF-8") +
                "&username=" + username);
            return false;
        }
        
        // 验证邮箱
        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("邮箱不能为空", "UTF-8") +
                "&username=" + username);
            return false;
        }
        
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            response.sendRedirect("register.jsp?error=" + 
                java.net.URLEncoder.encode("请输入有效的邮箱地址", "UTF-8") +
                "&username=" + username + 
                "&email=" + email);
            return false;
        }
        
        return true;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 重定向到注册页面
        response.sendRedirect("register.jsp");
    }
}