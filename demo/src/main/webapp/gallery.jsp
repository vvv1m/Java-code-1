<!-- filepath: d:\Java\Javacode\demo\src\main\webapp\gallery.jsp -->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.util.Date, java.text.SimpleDateFormat" %>
<%@ page import="com.example.model.User" %>
<%
    // 检查用户是否已登录
    User user = (User) session.getAttribute("user");
    String username = (String) session.getAttribute("username");
    
    if (user == null || username == null) {
        // 未登录，重定向到登录页
        response.sendRedirect("login.jsp?error=" + java.net.URLEncoder.encode("请先登录", "UTF-8"));
        return;
    }
    
    // 记录访问次数
    Integer visitCount = (Integer) session.getAttribute("visitCount");
    if (visitCount == null) {
        visitCount = 0;
    }
    visitCount++;
    session.setAttribute("visitCount", visitCount);
    
    // 获取登录时间
    Date loginTime = (Date) session.getAttribute("loginTime");
    
    // 根据不同账户配置不同的图片
    Map<String, List<Map<String, String>>> userGallery = new HashMap<>();
    
    // admin 账户的图片
    List<Map<String, String>> adminImages = new ArrayList<>();
    adminImages.add(createImage("https://picsum.photos/300/200?random=1", "管理系统仪表盘", "系统监控界面"));
    adminImages.add(createImage("https://picsum.photos/300/200?random=2", "用户管理", "用户数据统计"));
    adminImages.add(createImage("https://picsum.photos/300/200?random=3", "数据分析", "实时数据图表"));
    adminImages.add(createImage("https://picsum.photos/300/200?random=4", "服务器状态", "服务器监控面板"));
    
    // user1 账户的图片
    List<Map<String, String>> user1Images = new ArrayList<>();
    user1Images.add(createImage("https://picsum.photos/300/200?random=5", "我的项目", "最新项目进展"));
    user1Images.add(createImage("https://picsum.photos/300/200?random=6", "团队协作", "团队会议记录"));
    user1Images.add(createImage("https://picsum.photos/300/200?random=7", "任务列表", "待办事项清单"));
    user1Images.add(createImage("https://picsum.photos/300/200?random=8", "文档中心", "项目文档库"));
    
    // guest 账户的图片
    List<Map<String, String>> guestImages = new ArrayList<>();
    guestImages.add(createImage("https://picsum.photos/300/200?random=9", "欢迎页面", "系统介绍"));
    guestImages.add(createImage("https://picsum.photos/300/200?random=10", "功能演示", "功能预览"));
    guestImages.add(createImage("https://picsum.photos/300/200?random=11", "帮助文档", "使用指南"));
    
    userGallery.put("admin", adminImages);
    userGallery.put("user1", user1Images);
    userGallery.put("guest", guestImages);
    
    // 获取当前用户的图片
    List<Map<String, String>> currentImages = userGallery.get(username.toLowerCase());
    if (currentImages == null) {
        currentImages = new ArrayList<>();
        currentImages.add(createImage("https://picsum.photos/300/200?random=12", "默认相册", "暂无专属图片"));
    }
    
    // 格式化时间
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedLoginTime = loginTime != null ? sdf.format(loginTime) : "未知";
    String formattedCurrentTime = sdf.format(new Date());
%>

<%!
    // 辅助方法：创建图片信息
    private Map<String, String> createImage(String url, String title, String description) {
        Map<String, String> image = new HashMap<>();
        image.put("url", url);
        image.put("title", title);
        image.put("description", description);
        return image;
    }
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= username %> 的相册</title>
    <style>
        /* ... 保持原有样式 ... */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 30px 20px;
        }
        
        .header {
            text-align: center;
            color: white;
            margin-bottom: 40px;
            animation: fadeIn 0.8s ease-out;
        }
        
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .header h1 {
            font-size: 42px;
            margin-bottom: 10px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }
        
        .header p {
            font-size: 18px;
            opacity: 0.9;
        }
        
        .header .time {
            margin-top: 10px;
            font-size: 14px;
            opacity: 0.8;
        }
        
        .user-info {
            background: rgba(255, 255, 255, 0.2);
            padding: 10px 20px;
            border-radius: 50px;
            display: inline-block;
            margin-top: 15px;
        }
        
        .gallery-container {
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .gallery-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            animation: slideUp 1s ease-out;
        }
        
        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .image-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            transition: all 0.3s ease;
            animation: cardAppear 0.6s ease-out backwards;
        }
        
        .image-card:nth-child(1) { animation-delay: 0.1s; }
        .image-card:nth-child(2) { animation-delay: 0.2s; }
        .image-card:nth-child(3) { animation-delay: 0.3s; }
        .image-card:nth-child(4) { animation-delay: 0.4s; }
        
        @keyframes cardAppear {
            from {
                opacity: 0;
                transform: scale(0.8) translateY(20px);
            }
            to {
                opacity: 1;
                transform: scale(1) translateY(0);
            }
        }
        
        .image-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.3);
        }
        
        .image-wrapper {
            width: 100%;
            height: 200px;
            overflow: hidden;
            position: relative;
        }
        
        .image-wrapper img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.5s ease;
        }
        
        .image-card:hover .image-wrapper img {
            transform: scale(1.1);
        }
        
        .image-overlay {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: linear-gradient(to bottom, transparent, rgba(0,0,0,0.7));
            opacity: 0;
            transition: opacity 0.3s ease;
            display: flex;
            align-items: flex-end;
            padding: 15px;
            color: white;
        }
        
        .image-card:hover .image-overlay {
            opacity: 1;
        }
        
        .image-info {
            padding: 20px;
        }
        
        .image-info h3 {
            color: #333;
            font-size: 20px;
            margin-bottom: 8px;
        }
        
        .image-info p {
            color: #666;
            font-size: 14px;
            line-height: 1.5;
        }
        
        .logout-button {
            position: fixed;
            bottom: 30px;
            left: 30px;
            background: #ff6b6b;
            color: white;
            padding: 15px 30px;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 600;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.3);
            transition: all 0.3s ease;
            z-index: 1000;
        }
        
        .logout-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.4);
            background: #ee5a52;
        }
        
        @media (max-width: 768px) {
            .header h1 {
                font-size: 32px;
            }
            
            .gallery-grid {
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
                gap: 15px;
            }
            
            .logout-button {
                bottom: 20px;
                left: 20px;
                padding: 12px 24px;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>🎨 <%= username %> 的专属相册</h1>
        <p>欢迎回来！这里是您的个人图片库</p>
        <div class="user-info">
            <span>👤 <%= user.getEmail() != null ? user.getEmail() : "未设置邮箱" %></span>
        </div>
        <div class="time">
            登录时间：<%= formattedLoginTime %><br>
            当前时间：<%= formattedCurrentTime %><br>
            访问次数：<%= visitCount %> 次
        </div>
    </div>
    
    <div class="gallery-container">
        <% if (currentImages != null && !currentImages.isEmpty()) { %>
            <div class="gallery-grid">
                <% for (Map<String, String> image : currentImages) { %>
                    <div class="image-card">
                        <div class="image-wrapper">
                            <img src="<%= image.get("url") %>" 
                                 alt="<%= image.get("title") %>"
                                 loading="lazy">
                            <div class="image-overlay">
                                <span><%= image.get("title") %></span>
                            </div>
                        </div>
                        <div class="image-info">
                            <h3><%= image.get("title") %></h3>
                            <p><%= image.get("description") %></p>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } else { %>
            <div class="empty-message">
                <p>暂无图片</p>
            </div>
        <% } %>
    </div>
    
    <a href="LogoutServlet" class="logout-button">🚪 注销</a>
</body>
</html>