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

@WebServlet("/EditImageServlet")
public class EditImageServlet extends HttpServlet {
    private GalleryDAO galleryDAO = new GalleryDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. 验证用户登录状态
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp?error=请先登录");
            return;
        }
        
        // 2. 获取当前登录用户
        User user = (User) session.getAttribute("user");
        
        // 3. 获取表单参数
        String imageIdStr = request.getParameter("imageId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        // 4. 验证参数
        if (imageIdStr == null || imageIdStr.trim().isEmpty()) {
            response.sendRedirect("gallery.jsp?error=图片ID不能为空");
            return;
        }
        
        if (title == null || title.trim().isEmpty()) {
            response.sendRedirect("gallery.jsp?error=标题不能为空");
            return;
        }
        
        if (title.length() > 100) {
            response.sendRedirect("gallery.jsp?error=标题长度不能超过100个字符");
            return;
        }
        
        if (description != null && description.length() > 255) {
            response.sendRedirect("gallery.jsp?error=描述长度不能超过255个字符");
            return;
        }
        
        try {
            int imageId = Integer.parseInt(imageIdStr);
            
            // 5. 验证图片所有权（安全检查）
            Gallery existingImage = galleryDAO.getImageById(imageId);
            if (existingImage == null) {
                response.sendRedirect("gallery.jsp?error=图片不存在");
                return;
            }
            
            if (existingImage.getUserId() != user.getId()) {
                response.sendRedirect("gallery.jsp?error=您没有权限编辑此图片");
                return;
            }
            
            // 6. 更新图片信息
            Gallery updatedGallery = new Gallery();
            updatedGallery.setId(imageId);
            updatedGallery.setUserId(user.getId());
            updatedGallery.setTitle(title.trim());
            updatedGallery.setDescription(description != null ? description.trim() : "");
            
            boolean success = galleryDAO.updateImage(updatedGallery);
            
            // 7. 重定向回 gallery.jsp 并显示结果
            if (success) {
                response.sendRedirect("gallery.jsp?success=图片编辑成功");
            } else {
                response.sendRedirect("gallery.jsp?error=图片编辑失败，请稍后重试");
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("gallery.jsp?error=图片ID格式错误");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gallery.jsp?error=系统错误，请稍后重试");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("gallery.jsp");
    }
}