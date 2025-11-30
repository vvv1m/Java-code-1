package com.example.servlet;

import com.example.dao.GalleryDAO;
import com.example.model.Gallery;
import com.example.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/AddImageServlet")
public class AddImageServlet extends HttpServlet {
    private GalleryDAO galleryDAO = new GalleryDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp?error=" + URLEncoder.encode("请先登录", "UTF-8"));
            return;
        }
        
        User user = (User) session.getAttribute("user");
        String imageUrl = request.getParameter("imageUrl");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        // 验证输入
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            response.sendRedirect("gallery.jsp?error=" + URLEncoder.encode("图片路径不能为空", "UTF-8"));
            return;
        }
        
        if (title == null || title.trim().isEmpty()) {
            title = "未命名图片";
        }
        
        if (description == null || description.trim().isEmpty()) {
            description = "暂无描述";
        }
        
        // 创建 Gallery 对象并保存
        Gallery gallery = new Gallery(user.getId(), imageUrl.trim(), title.trim(), description.trim());
        
        if (galleryDAO.addImage(gallery)) {
            response.sendRedirect("gallery.jsp?success=" + URLEncoder.encode("图片添加成功", "UTF-8"));
        } else {
            response.sendRedirect("gallery.jsp?error=" + URLEncoder.encode("图片添加失败，请重试", "UTF-8"));
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("gallery.jsp");
    }
}