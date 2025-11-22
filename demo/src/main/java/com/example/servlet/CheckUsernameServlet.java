package com.example.servlet;

import com.example.dao.UserDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/CheckUsernameServlet")
public class CheckUsernameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置响应类型为 JSON
        response.setContentType("application/json;charset=UTF-8");
        
        String username = request.getParameter("username");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (username == null || username.trim().isEmpty()) {
                result.put("available", false);
                result.put("message", "用户名不能为空");
            } else {
                boolean exists = userDAO.isUsernameExists(username);
                result.put("available", !exists);
                result.put("message", exists ? "用户名已被使用" : "用户名可用");
            }
        } catch (Exception e) {
            result.put("available", false);
            result.put("message", "检查失败");
            e.printStackTrace();
        }
        
        // 返回 JSON
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(result));
        out.flush();
    }
}