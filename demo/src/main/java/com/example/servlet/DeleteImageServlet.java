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

@WebServlet("/DeleteImageServlet")
public class DeleteImageServlet extends HttpServlet {
    private GalleryDAO galleryDAO = new GalleryDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置编码
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
        
        // 3. 获取要删除的图片ID
        String imageIdStr = request.getParameter("imageId");
        
        // 4. 验证参数
        if (imageIdStr == null || imageIdStr.trim().isEmpty()) {
            response.sendRedirect("gallery.jsp?error=图片ID不能为空");
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
                response.sendRedirect("gallery.jsp?error=您没有权限删除此图片");
                return;
            }
            
            // 6. 删除图片
            boolean success = galleryDAO.deleteImage(imageId, user.getId());
            
            // 7. 重定向回 gallery.jsp 并显示结果
            if (success) {
                response.sendRedirect("gallery.jsp?success=图片删除成功");
            } else {
                response.sendRedirect("gallery.jsp?error=图片删除失败，请稍后重试");
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
        // GET 请求也支持删除（通过 URL 参数）
        doPost(request, response);
    }
}