package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

/**
 * 登录处理 Servlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 设置请求和响应编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember"); // 记住我功能
        
        // 验证参数
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=" + 
                java.net.URLEncoder.encode("用户名和密码不能为空", "UTF-8"));
            return;
        }
        
        try {
            // 查询用户
            User user = userDAO.findByUsername(username.trim());
            
            // ✅ 修复：使用正确的方法名 verifyPassword
            if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
                // 检查账户状态
                if (!"active".equals(user.getStatus())) {
                    response.sendRedirect("login.jsp?error=" + 
                        java.net.URLEncoder.encode("账户已被禁用，请联系管理员", "UTF-8") +
                        "&username=" + username);
                    return;
                }
                
                // 登录成功，创建 Session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", username);
                session.setAttribute("loginTime", new Date());
                
                // 设置 Session 超时时间（30分钟）
                session.setMaxInactiveInterval(30 * 60);
                
                // 如果选择了"记住我"，设置 Cookie（可选功能）
                if ("on".equals(remember)) {
                    jakarta.servlet.http.Cookie userCookie = new jakarta.servlet.http.Cookie("rememberedUser", username);
                    userCookie.setMaxAge(7 * 24 * 60 * 60); // 7天
                    userCookie.setPath(request.getContextPath());
                    response.addCookie(userCookie);
                }
                
                // 重定向到画廊页面
                response.sendRedirect("gallery.jsp");
            } else {
                // 登录失败
                response.sendRedirect("login.jsp?error=" + 
                    java.net.URLEncoder.encode("用户名或密码错误", "UTF-8") +
                    "&username=" + username);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=" + 
                java.net.URLEncoder.encode("系统错误，请稍后重试", "UTF-8"));
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // GET 请求重定向到登录页
        response.sendRedirect("login.jsp");
    }
}