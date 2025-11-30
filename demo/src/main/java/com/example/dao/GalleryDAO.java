package com.example.dao;

import com.example.model.Gallery;
import com.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GalleryDAO {
    
    /**
     * 添加图片
     */
    public boolean addImage(Gallery gallery) {
        String sql = "INSERT INTO gallery (user_id, image_url, title, description) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gallery.getUserId());
            pstmt.setString(2, gallery.getImageUrl());
            pstmt.setString(3, gallery.getTitle());
            pstmt.setString(4, gallery.getDescription());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据用户ID获取所有图片
     */
    public List<Gallery> getImagesByUserId(int userId) {
        List<Gallery> images = new ArrayList<>();
        String sql = "SELECT * FROM gallery WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Gallery gallery = new Gallery();
                gallery.setId(rs.getInt("id"));
                gallery.setUserId(rs.getInt("user_id"));
                gallery.setImageUrl(rs.getString("image_url"));
                gallery.setTitle(rs.getString("title"));
                gallery.setDescription(rs.getString("description"));
                gallery.setCreatedAt(rs.getTimestamp("created_at"));
                images.add(gallery);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return images;
    }
    
    /**
     * 根据图片ID获取单张图片
     */
    public Gallery getImageById(int imageId) {
        String sql = "SELECT * FROM gallery WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, imageId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Gallery gallery = new Gallery();
                gallery.setId(rs.getInt("id"));
                gallery.setUserId(rs.getInt("user_id"));
                gallery.setImageUrl(rs.getString("image_url"));
                gallery.setTitle(rs.getString("title"));
                gallery.setDescription(rs.getString("description"));
                gallery.setCreatedAt(rs.getTimestamp("created_at"));
                return gallery;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 更新图片信息（编辑功能）
     */
    public boolean updateImage(Gallery gallery) {
        String sql = "UPDATE gallery SET title = ?, description = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, gallery.getTitle());
            pstmt.setString(2, gallery.getDescription());
            pstmt.setInt(3, gallery.getId());
            pstmt.setInt(4, gallery.getUserId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除图片（删除功能）
     */
    public boolean deleteImage(int imageId, int userId) {
        String sql = "DELETE FROM gallery WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, imageId);
            pstmt.setInt(2, userId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取用户的图片总数
     */
    public int getImageCount(int userId) {
        String sql = "SELECT COUNT(*) FROM gallery WHERE user_id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}