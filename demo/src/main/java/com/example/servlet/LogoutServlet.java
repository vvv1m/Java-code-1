package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 注销 Servlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 获取会话
        HttpSession session = request.getSession(false);
        
        // 记录注销信息（可选）
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                System.out.println("👤 用户 " + username + " 已注销");
            }
            
            // 销毁会话
            session.invalidate();
        }
        
        // 清除"记住我"的 Cookie（如果存在）
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberedUser".equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        
        // 重定向到登录页，显示注销成功消息
        response.sendRedirect("login.jsp?logout=success");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}