<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.User" %>
<%@ page import="com.example.model.Gallery" %>
<%@ page import="com.example.dao.GalleryDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    // 检查用户是否登录
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp?error=请先登录");
        return;
    }
    
    // 获取用户的所有图片
    GalleryDAO galleryDAO = new GalleryDAO();
    List<Gallery> images = galleryDAO.getImagesByUserId(user.getId());
    
    // 获取消息参数
    String success = request.getParameter("success");
    String error = request.getParameter("error");
    
    // 访问次数统计
    Integer visitCount = (Integer) session.getAttribute("visitCount");
    if (visitCount == null) {
        visitCount = 1;
    } else {
        visitCount++;
    }
    session.setAttribute("visitCount", visitCount);
    
    // 日期格式化
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的图片画廊</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
        }
        
        .header {
            background: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        
        .header h1 {
            color: #667eea;
            font-size: 42px;
            margin-bottom: 15px;
            text-align: center;
        }
        
        .user-info {
            display: flex;
            justify-content: space-around;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 2px solid #f0f0f0;
        }
        
        .info-item {
            text-align: center;
        }
        
        .info-item label {
            display: block;
            color: #666;
            font-size: 14px;
            margin-bottom: 5px;
        }
        
        .info-item span {
            color: #333;
            font-weight: bold;
            font-size: 16px;
        }
        
        /* 消息提示 */
        .message {
            padding: 15px 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            animation: slideDown 0.5s ease;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .message.success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .message.error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .message::before {
            content: "ℹ️";
            font-size: 20px;
        }
        
        @keyframes slideDown {
            from {
                transform: translateY(-20px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
        
        /* 添加图片部分 */
        .add-image-section {
            background: white;
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        }
        
        .add-image-section h2 {
            color: #667eea;
            margin-bottom: 20px;
            font-size: 24px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            color: #333;
            margin-bottom: 8px;
            font-weight: 500;
        }
        
        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }
        
        .form-group input:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        .form-group textarea {
            resize: vertical;
            min-height: 80px;
        }
        
        .btn-add {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 40px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-add:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        /* 图片画廊网格 */
        .gallery-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            margin-top: 20px;
        }
        
        .image-card {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            transition: all 0.3s;
            position: relative;
        }
        
        .image-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 15px 40px rgba(0,0,0,0.2);
        }
        
        .image-wrapper {
            position: relative;
            width: 100%;
            height: 250px;
            overflow: hidden;
            background: #f5f5f5;
        }
        
        .image-wrapper img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s;
        }
        
        .image-card:hover .image-wrapper img {
            transform: scale(1.1);
        }
        
        .image-actions {
            position: absolute;
            top: 10px;
            right: 10px;
            display: flex;
            gap: 10px;
            opacity: 0;
            transition: opacity 0.3s;
        }
        
        .image-card:hover .image-actions {
            opacity: 1;
        }
        
        .btn-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            border: none;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            transition: all 0.3s;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        }
        
        .btn-edit {
            background: #4CAF50;
            color: white;
        }
        
        .btn-edit:hover {
            background: #45a049;
            transform: scale(1.1);
        }
        
        .btn-delete {
            background: #f44336;
            color: white;
        }
        
        .btn-delete:hover {
            background: #da190b;
            transform: scale(1.1);
        }
        
        .image-info {
            padding: 20px;
        }
        
        .image-info h3 {
            color: #333;
            font-size: 20px;
            margin-bottom: 10px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        
        .image-info p {
            color: #666;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 10px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        
        .image-time {
            color: #999;
            font-size: 12px;
            display: flex;
            align-items: center;
            gap: 5px;
        }
        
        .image-time::before {
            content: "🕒";
        }
        
        /* 编辑模态框 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.6);
            animation: fadeIn 0.3s;
        }
        
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
        
        .modal-content {
            background-color: white;
            margin: 5% auto;
            padding: 30px;
            border-radius: 15px;
            width: 90%;
            max-width: 600px;
            box-shadow: 0 10px 50px rgba(0,0,0,0.3);
            animation: slideUp 0.3s;
        }
        
        @keyframes slideUp {
            from {
                transform: translateY(50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }
        
        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }
        
        .modal-header h2 {
            color: #667eea;
            font-size: 24px;
        }
        
        .close {
            color: #aaa;
            font-size: 32px;
            font-weight: bold;
            cursor: pointer;
            transition: color 0.3s;
            line-height: 1;
        }
        
        .close:hover {
            color: #000;
        }
        
        .modal-footer {
            display: flex;
            justify-content: flex-end;
            gap: 15px;
            margin-top: 25px;
            padding-top: 20px;
            border-top: 2px solid #f0f0f0;
        }
        
        .btn-cancel {
            background: #6c757d;
            color: white;
            padding: 10px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-cancel:hover {
            background: #5a6268;
        }
        
        .btn-save {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 10px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-save:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        /* 注销按钮 */
        .logout-button {
            position: fixed;
            bottom: 30px;
            left: 30px;
            background: rgba(255, 255, 255, 0.95);
            color: #667eea;
            padding: 15px 30px;
            border: none;
            border-radius: 50px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            box-shadow: 0 5px 20px rgba(0,0,0,0.2);
            transition: all 0.3s;
            z-index: 100;
        }
        
        .logout-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.3);
            background: white;
        }
        
        /* 空状态 */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 15px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        
        .empty-state h3 {
            color: #667eea;
            font-size: 28px;
            margin-bottom: 15px;
        }
        
        .empty-state p {
            color: #666;
            font-size: 16px;
        }
        
        .empty-state::before {
            content: "🖼️";
            font-size: 80px;
            display: block;
            margin-bottom: 20px;
        }
        
        /* 响应式设计 */
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
        
        @media (max-width: 480px) {
            .header h1 {
                font-size: 24px;
            }
            
            .add-image-section {
                padding: 20px 15px;
            }
            
            .gallery-grid {
                grid-template-columns: 1fr;
                gap: 15px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 头部信息 -->
        <div class="header">
            <h1>🎨 <%= user.getUsername() %> 的专属相册</h1>
            <div class="user-info">
                <div class="info-item">
                    <label>登录时间</label>
                    <span><%= sdf.format(session.getAttribute("loginTime")) %></span>
                </div>
                <div class="info-item">
                    <label>当前时间</label>
                    <span><%= sdf.format(new java.util.Date()) %></span>
                </div>
                <div class="info-item">
                    <label>访问次数</label>
                    <span><%= visitCount %></span>
                </div>
                <div class="info-item">
                    <label>图片数量</label>
                    <span><%= images.size() %></span>
                </div>
            </div>
        </div>
        
        <!-- 消息提示 -->
        <% if (success != null) { %>
            <div class="message success">
                <%= success %>
            </div>
        <% } %>
        
        <% if (error != null) { %>
            <div class="message error">
                <%= error %>
            </div>
        <% } %>
        
        <!-- 添加图片表单 -->
        <div class="add-image-section">
            <h2>📸 添加新图片</h2>
            <form action="AddImageServlet" method="post">
                <div class="form-group">
                    <label for="imageUrl">图片链接 *</label>
                    <input type="url" id="imageUrl" name="imageUrl" 
                           placeholder="https://example.com/image.jpg" required>
                </div>
                <div class="form-group">
                    <label for="title">图片标题 *</label>
                    <input type="text" id="title" name="title" 
                           placeholder="给图片起个好听的名字" maxlength="100" required>
                </div>
                <div class="form-group">
                    <label for="description">图片描述</label>
                    <textarea id="description" name="description" 
                              placeholder="描述一下这张图片的故事..." maxlength="255"></textarea>
                </div>
                <button type="submit" class="btn-add">➕ 添加图片</button>
            </form>
        </div>
        
        <!-- 图片画廊 -->
        <% if (images.isEmpty()) { %>
            <div class="empty-state">
                <h3>暂无图片</h3>
                <p>赶快添加第一张图片吧！</p>
            </div>
        <% } else { %>
            <div class="gallery-grid">
                <% for (Gallery image : images) { %>
                    <div class="image-card">
                        <div class="image-wrapper">
                            <img src="<%= image.getImageUrl() %>" 
                                 alt="<%= image.getTitle() %>"
                                 onerror="this.src='https://via.placeholder.com/300x200?text=图片加载失败'">
                            <div class="image-actions">
                                <button class="btn-icon btn-edit" 
                                        onclick="openEditModal(<%= image.getId() %>, 
                                                '<%= image.getTitle().replace("'", "\\'") %>', 
                                                '<%= image.getDescription() != null ? image.getDescription().replace("'", "\\'") : "" %>')">
                                    ✏️
                                </button>
                                <button class="btn-icon btn-delete" 
                                        onclick="deleteImage(<%= image.getId() %>, '<%= image.getTitle() %>')">
                                    🗑️
                                </button>
                            </div>
                        </div>
                        <div class="image-info">
                            <h3><%= image.getTitle() %></h3>
                            <% if (image.getDescription() != null && !image.getDescription().isEmpty()) { %>
                                <p><%= image.getDescription() %></p>
                            <% } %>
                            <div class="image-time">
                                <%= sdf.format(image.getCreatedAt()) %>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>
    </div>
    
    <!-- 编辑模态框 -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>✏️ 编辑图片</h2>
                <span class="close" onclick="closeEditModal()">&times;</span>
            </div>
            <form action="EditImageServlet" method="post">
                <input type="hidden" id="editImageId" name="imageId">
                <div class="form-group">
                    <label for="editTitle">图片标题 *</label>
                    <input type="text" id="editTitle" name="title" 
                           maxlength="100" required>
                </div>
                <div class="form-group">
                    <label for="editDescription">图片描述</label>
                    <textarea id="editDescription" name="description" 
                              maxlength="255"></textarea>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn-cancel" 
                            onclick="closeEditModal()">取消</button>
                    <button type="submit" class="btn-save">💾 保存更改</button>
                </div>
            </form>
        </div>
    </div>
    
    <!-- 注销按钮 -->
    <button class="logout-button" onclick="logout()">🚪 注销</button>
    
    <script>
        // 打开编辑模态框
        function openEditModal(imageId, title, description) {
            document.getElementById('editImageId').value = imageId;
            document.getElementById('editTitle').value = title;
            document.getElementById('editDescription').value = description;
            document.getElementById('editModal').style.display = 'block';
        }
        
        // 关闭编辑模态框
        function closeEditModal() {
            document.getElementById('editModal').style.display = 'none';
        }
        
        // 点击模态框外部关闭
        window.onclick = function(event) {
            const modal = document.getElementById('editModal');
            if (event.target === modal) {
                closeEditModal();
            }
        }
        
        // 删除图片
        function deleteImage(imageId, title) {
            if (confirm('确定要删除图片"' + title + '"吗？\n此操作不可恢复！')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'DeleteImageServlet';
                
                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'imageId';
                input.value = imageId;
                
                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        }
        
        // 注销
        function logout() {
            if (confirm('确定要退出登录吗？')) {
                window.location.href = 'LogoutServlet';
            }
        }
        
        // 自动隐藏消息提示
        setTimeout(function() {
            const messages = document.querySelectorAll('.message');
            messages.forEach(function(msg) {
                msg.style.transition = 'opacity 0.5s';
                msg.style.opacity = '0';
                setTimeout(function() {
                    msg.remove();
                }, 500);
            });
        }, 5000);
        
        // ESC 键关闭模态框
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                closeEditModal();
            }
        });
    </script>
</body>
</html>