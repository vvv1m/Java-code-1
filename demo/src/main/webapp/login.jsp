<!-- filepath: d:\Java\Javacode\demo\src\main\webapp\login.jsp -->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 400px;
            animation: slideIn 0.5s ease-out;
        }
        
        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        label {
            display: block;
            color: #555;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 14px;
        }
        
        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
        }
        
        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        button {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        button:active {
            transform: translateY(0);
        }
        
        .error-message,
        .success-message {
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            animation: slideDown 0.3s ease-out;
        }
        
        .error-message {
            background: #fee;
            color: #c33;
            border: 1px solid #fcc;
        }
        
        .success-message {
            background: #efe;
            color: #3c3;
            border: 1px solid #cfc;
        }
        
        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .hint {
            margin-top: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-left: 4px solid #667eea;
            border-radius: 4px;
        }
        
        .hint p {
            color: #666;
            font-size: 13px;
            line-height: 1.6;
            margin: 5px 0;
        }
        
        .hint strong {
            color: #667eea;
        }
        
        .register-link {
            text-align: center;
            margin-top: 20px;
            color: #666;
            font-size: 14px;
        }
        
        .register-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }
        
        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>🎨 用户相册</h1>
        
        <!-- 显示注销成功消息 -->
        <c:if test="${param.logout == 'success'}">
            <div class="success-message">
                ✅ 您已成功注销
            </div>
        </c:if>
        
        <!-- 显示错误消息 -->
        <c:if test="${not empty param.error}">
            <div class="error-message">
                ❌ ${param.error}
            </div>
        </c:if>
        
        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="username">用户名：</label>
                <input 
                    type="text" 
                    id="username" 
                    name="username" 
                    placeholder="请输入用户名"
                    value="${param.username}"
                    required
                    autofocus
                    minlength="2"
                    maxlength="50"
                />
            </div>
            
            <div class="form-group">
                <label for="password">密码：</label>
                <input 
                    type="password" 
                    id="password" 
                    name="password" 
                    placeholder="请输入密码"
                    required
                    minlength="6"
                />
            </div>
            
            <button type="submit">登录</button>
        </form>
        
        <div class="hint">
            <p><strong>💡 测试账户：</strong></p>
            <p>• 用户名：<strong>admin</strong> 密码：<strong>123456</strong></p>
            <p>• 用户名：<strong>user1</strong> 密码：<strong>123456</strong></p>
            <p>• 用户名：<strong>guest</strong> 密码：<strong>123456</strong></p>
        </div>
        
        <div class="register-link">
            还没有账户？<a href="${pageContext.request.contextPath}/register.jsp">立即注册</a>
        </div>
    </div>
</body>
</html>