<!-- filepath: d:\Java\Javacode\demo\src\main\webapp\register.jsp -->
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户注册</title>
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
        
        .register-container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 500px;
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
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            color: #555;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 14px;
        }
        
        label .required {
            color: #e74c3c;
            margin-left: 2px;
        }
        
        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="tel"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
        }
        
        input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }
        
        input.error {
            border-color: #e74c3c;
        }
        
        input.success {
            border-color: #27ae60;
        }
        
        .field-error {
            color: #e74c3c;
            font-size: 12px;
            margin-top: 5px;
            display: none;
        }
        
        .field-error.show {
            display: block;
        }
        
        .field-success {
            color: #27ae60;
            font-size: 12px;
            margin-top: 5px;
            display: none;
        }
        
        .field-success.show {
            display: block;
        }
        
        .password-strength {
            margin-top: 8px;
            height: 4px;
            background: #e0e0e0;
            border-radius: 2px;
            overflow: hidden;
            display: none;
        }
        
        .password-strength.show {
            display: block;
        }
        
        .password-strength-bar {
            height: 100%;
            transition: all 0.3s ease;
        }
        
        .password-strength-bar.weak {
            width: 33.33%;
            background: #e74c3c;
        }
        
        .password-strength-bar.medium {
            width: 66.66%;
            background: #f39c12;
        }
        
        .password-strength-bar.strong {
            width: 100%;
            background: #27ae60;
        }
        
        .password-hint {
            font-size: 12px;
            color: #666;
            margin-top: 5px;
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
            margin-top: 10px;
        }
        
        button:hover:not(:disabled) {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        button:active:not(:disabled) {
            transform: translateY(0);
        }
        
        button:disabled {
            opacity: 0.6;
            cursor: not-allowed;
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
        
        .login-link {
            text-align: center;
            margin-top: 20px;
            color: #666;
            font-size: 14px;
        }
        
        .login-link a {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }
        
        .login-link a:hover {
            text-decoration: underline;
        }
        
        .checkbox-group {
            display: flex;
            align-items: flex-start;
            margin-bottom: 20px;
        }
        
        .checkbox-group input[type="checkbox"] {
            width: auto;
            margin-right: 8px;
            margin-top: 2px;
        }
        
        .checkbox-group label {
            margin-bottom: 0;
            font-size: 13px;
            font-weight: normal;
        }
        
        .checkbox-group label a {
            color: #667eea;
            text-decoration: none;
        }
        
        .checkbox-group label a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h1>🎨 用户注册</h1>
        
        <!-- 显示错误消息 -->
        <c:if test="${not empty param.error}">
            <div class="error-message">
                ❌ ${param.error}
            </div>
        </c:if>
        
        <!-- 显示成功消息 -->
        <c:if test="${param.success == 'true'}">
            <div class="success-message">
                ✅ 注册成功！正在跳转到登录页面...
            </div>
        </c:if>
        
        <form id="registerForm" action="${pageContext.request.contextPath}/RegisterServlet" method="post" onsubmit="return validateForm()">
            <!-- 用户名 -->
            <div class="form-group">
                <label for="username">
                    用户名 <span class="required">*</span>
                </label>
                <input 
                    type="text" 
                    id="username" 
                    name="username" 
                    placeholder="请输入用户名（2-20个字符）"
                    value="${param.username}"
                    required
                    autofocus
                    minlength="2"
                    maxlength="20"
                    onblur="validateUsername()"
                    oninput="checkUsernameAvailability()"
                />
                <div class="field-error" id="usernameError"></div>
                <div class="field-success" id="usernameSuccess"></div>
            </div>
            
            <!-- 密码 -->
            <div class="form-group">
                <label for="password">
                    密码 <span class="required">*</span>
                </label>
                <input 
                    type="password" 
                    id="password" 
                    name="password" 
                    placeholder="请输入密码（至少6个字符）"
                    required
                    minlength="6"
                    maxlength="50"
                    onblur="validatePassword()"
                    oninput="checkPasswordStrength()"
                />
                <div class="password-strength" id="passwordStrength">
                    <div class="password-strength-bar" id="passwordStrengthBar"></div>
                </div>
                <div class="password-hint">密码长度至少6位，包含字母和数字更安全</div>
                <div class="field-error" id="passwordError"></div>
            </div>
            
            <!-- 确认密码 -->
            <div class="form-group">
                <label for="confirmPassword">
                    确认密码 <span class="required">*</span>
                </label>
                <input 
                    type="password" 
                    id="confirmPassword" 
                    name="confirmPassword" 
                    placeholder="请再次输入密码"
                    required
                    minlength="6"
                    maxlength="50"
                    onblur="validateConfirmPassword()"
                    oninput="validateConfirmPassword()"
                />
                <div class="field-error" id="confirmPasswordError"></div>
                <div class="field-success" id="confirmPasswordSuccess"></div>
            </div>
            
            <!-- 邮箱 -->
            <div class="form-group">
                <label for="email">
                    邮箱 <span class="required">*</span>
                </label>
                <input 
                    type="email" 
                    id="email" 
                    name="email" 
                    placeholder="请输入邮箱地址"
                    value="${param.email}"
                    required
                    onblur="validateEmail()"
                />
                <div class="field-error" id="emailError"></div>
                <div class="field-success" id="emailSuccess"></div>
            </div>
            
            <!-- 手机号（可选） -->
            <div class="form-group">
                <label for="phone">手机号</label>
                <input 
                    type="tel" 
                    id="phone" 
                    name="phone" 
                    placeholder="请输入手机号（可选）"
                    value="${param.phone}"
                    pattern="^1[3-9]\d{9}$"
                    onblur="validatePhone()"
                />
                <div class="field-error" id="phoneError"></div>
                <div class="field-success" id="phoneSuccess"></div>
            </div>
            
            <!-- 用户协议 -->
            <div class="checkbox-group">
                <input 
                    type="checkbox" 
                    id="agreement" 
                    name="agreement" 
                    required
                />
                <label for="agreement">
                    我已阅读并同意 <a href="#" onclick="showAgreement(); return false;">用户协议</a> 和 <a href="#" onclick="showPrivacy(); return false;">隐私政策</a>
                </label>
            </div>
            
            <button type="submit" id="submitBtn">立即注册</button>
        </form>
        
        <div class="login-link">
            已有账户？<a href="${pageContext.request.contextPath}/login.jsp">立即登录</a>
        </div>
    </div>
    
    <script>
         // ✅ 获取上下文路径
        const contextPath = '<%= request.getContextPath() %>';
        console.log('上下文路径:', contextPath);
        // 表单验证状态
        const validationState = {
            username: false,
            password: false,
            confirmPassword: false,
            email: false,
            phone: true // 手机号可选，默认为 true
        };
        
        // 验证用户名
        function validateUsername() {
            const username = document.getElementById('username').value.trim();
            const usernameError = document.getElementById('usernameError');
            const usernameSuccess = document.getElementById('usernameSuccess');
            const usernameInput = document.getElementById('username');
            
            // 清除之前的状态
            usernameError.classList.remove('show');
            usernameSuccess.classList.remove('show');
            usernameInput.classList.remove('error', 'success');
            
            if (username === '') {
                usernameError.textContent = '用户名不能为空';
                usernameError.classList.add('show');
                usernameInput.classList.add('error');
                validationState.username = false;
                return false;
            }
            
            if (username.length < 2 || username.length > 20) {
                usernameError.textContent = '用户名长度必须在2-20个字符之间';
                usernameError.classList.add('show');
                usernameInput.classList.add('error');
                validationState.username = false;
                return false;
            }
            
            // 只允许字母、数字、下划线
            const usernamePattern = /^[a-zA-Z0-9_]+$/;
            if (!usernamePattern.test(username)) {
                usernameError.textContent = '用户名只能包含字母、数字和下划线';
                usernameError.classList.add('show');
                usernameInput.classList.add('error');
                validationState.username = false;
                return false;
            }
            
            usernameSuccess.textContent = '✓ 用户名格式正确';
            usernameSuccess.classList.add('show');
            usernameInput.classList.add('success');
            validationState.username = true;
            return true;
        }
        
        // 检查用户名是否可用（异步）
        let usernameCheckTimeout;
        // ✅ 修复：检查用户名可用性
        function checkUsernameAvailability() {
            const username = document.getElementById('username').value.trim();
            
            if (username.length < 2) {
                return;
            }
            
            clearTimeout(usernameCheckTimeout);
            usernameCheckTimeout = setTimeout(() => {
                // ✅ 使用正确的路径
                fetch(contextPath + '/CheckUsernameServlet?username=' + encodeURIComponent(username))
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('HTTP ' + response.status);
                        }
                        return response.json();
                    })
                    .then(data => {
                        const usernameError = document.getElementById('usernameError');
                        const usernameSuccess = document.getElementById('usernameSuccess');
                        const usernameInput = document.getElementById('username');
                        
                        if (!data.available) {
                            usernameError.textContent = '该用户名已被使用';
                            usernameError.classList.add('show');
                            usernameSuccess.classList.remove('show');
                            usernameInput.classList.add('error');
                            usernameInput.classList.remove('success');
                            validationState.username = false;
                        } else {
                            usernameSuccess.textContent = '✓ 用户名可用';
                            usernameSuccess.classList.add('show');
                            usernameError.classList.remove('show');
                            usernameInput.classList.add('success');
                            usernameInput.classList.remove('error');
                            validationState.username = true;
                        }
                    })
                    .catch(error => {
                        console.error('检查用户名失败:', error);
                    });
            }, 500);
        }
        
        // 检查密码强度
        function checkPasswordStrength() {
            const password = document.getElementById('password').value;
            const strengthDiv = document.getElementById('passwordStrength');
            const strengthBar = document.getElementById('passwordStrengthBar');
            
            if (password === '') {
                strengthDiv.classList.remove('show');
                return;
            }
            
            strengthDiv.classList.add('show');
            
            let strength = 0;
            
            // 长度
            if (password.length >= 6) strength++;
            if (password.length >= 10) strength++;
            
            // 包含数字
            if (/\d/.test(password)) strength++;
            
            // 包含小写字母
            if (/[a-z]/.test(password)) strength++;
            
            // 包含大写字母
            if (/[A-Z]/.test(password)) strength++;
            
            // 包含特殊字符
            if (/[^a-zA-Z0-9]/.test(password)) strength++;
            
            // 设置强度条
            strengthBar.className = 'password-strength-bar';
            if (strength <= 2) {
                strengthBar.classList.add('weak');
            } else if (strength <= 4) {
                strengthBar.classList.add('medium');
            } else {
                strengthBar.classList.add('strong');
            }
        }
        
        // 验证密码
        function validatePassword() {
            const password = document.getElementById('password').value;
            const passwordError = document.getElementById('passwordError');
            const passwordInput = document.getElementById('password');
            
            passwordError.classList.remove('show');
            passwordInput.classList.remove('error', 'success');
            
            if (password === '') {
                passwordError.textContent = '密码不能为空';
                passwordError.classList.add('show');
                passwordInput.classList.add('error');
                validationState.password = false;
                return false;
            }
            
            if (password.length < 6) {
                passwordError.textContent = '密码长度至少6个字符';
                passwordError.classList.add('show');
                passwordInput.classList.add('error');
                validationState.password = false;
                return false;
            }
            
            passwordInput.classList.add('success');
            validationState.password = true;
            
            // 重新验证确认密码
            if (document.getElementById('confirmPassword').value !== '') {
                validateConfirmPassword();
            }
            
            return true;
        }
        
        // 验证确认密码
        function validateConfirmPassword() {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const confirmPasswordError = document.getElementById('confirmPasswordError');
            const confirmPasswordSuccess = document.getElementById('confirmPasswordSuccess');
            const confirmPasswordInput = document.getElementById('confirmPassword');
            
            confirmPasswordError.classList.remove('show');
            confirmPasswordSuccess.classList.remove('show');
            confirmPasswordInput.classList.remove('error', 'success');
            
            if (confirmPassword === '') {
                confirmPasswordError.textContent = '请再次输入密码';
                confirmPasswordError.classList.add('show');
                confirmPasswordInput.classList.add('error');
                validationState.confirmPassword = false;
                return false;
            }
            
            if (password !== confirmPassword) {
                confirmPasswordError.textContent = '两次输入的密码不一致';
                confirmPasswordError.classList.add('show');
                confirmPasswordInput.classList.add('error');
                validationState.confirmPassword = false;
                return false;
            }
            
            confirmPasswordSuccess.textContent = '✓ 密码一致';
            confirmPasswordSuccess.classList.add('show');
            confirmPasswordInput.classList.add('success');
            validationState.confirmPassword = true;
            return true;
        }
        
        // 验证邮箱
        function validateEmail() {
            const email = document.getElementById('email').value.trim();
            const emailError = document.getElementById('emailError');
            const emailSuccess = document.getElementById('emailSuccess');
            const emailInput = document.getElementById('email');
            
            emailError.classList.remove('show');
            emailSuccess.classList.remove('show');
            emailInput.classList.remove('error', 'success');
            
            if (email === '') {
                emailError.textContent = '邮箱不能为空';
                emailError.classList.add('show');
                emailInput.classList.add('error');
                validationState.email = false;
                return false;
            }
            
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(email)) {
                emailError.textContent = '请输入有效的邮箱地址';
                emailError.classList.add('show');
                emailInput.classList.add('error');
                validationState.email = false;
                return false;
            }
            
            emailSuccess.textContent = '✓ 邮箱格式正确';
            emailSuccess.classList.add('show');
            emailInput.classList.add('success');
            validationState.email = true;
            return true;
        }
        
        // 验证手机号
        function validatePhone() {
            const phone = document.getElementById('phone').value.trim();
            const phoneError = document.getElementById('phoneError');
            const phoneSuccess = document.getElementById('phoneSuccess');
            const phoneInput = document.getElementById('phone');
            
            phoneError.classList.remove('show');
            phoneSuccess.classList.remove('show');
            phoneInput.classList.remove('error', 'success');
            
            // 手机号可选
            if (phone === '') {
                validationState.phone = true;
                return true;
            }
            
            const phonePattern = /^1[3-9]\d{9}$/;
            if (!phonePattern.test(phone)) {
                phoneError.textContent = '请输入有效的手机号';
                phoneError.classList.add('show');
                phoneInput.classList.add('error');
                validationState.phone = false;
                return false;
            }
            
            phoneSuccess.textContent = '✓ 手机号格式正确';
            phoneSuccess.classList.add('show');
            phoneInput.classList.add('success');
            validationState.phone = true;
            return true;
        }
        
        // 表单提交验证
        function validateForm() {
            validateUsername();
            validatePassword();
            validateConfirmPassword();
            validateEmail();
            validatePhone();
            
            // 检查所有字段是否通过验证
            const allValid = Object.values(validationState).every(v => v === true);
            
            if (!allValid) {
                alert('请检查并修正表单中的错误');
                return false;
            }
            
            // 检查用户协议
            const agreement = document.getElementById('agreement').checked;
            if (!agreement) {
                alert('请先阅读并同意用户协议和隐私政策');
                return false;
            }
            
            return true;
        }
        
        // 显示用户协议
        function showAgreement() {
            alert('用户协议内容（示例）\n\n1. 用户注册须使用真实信息\n2. 禁止发布违法违规内容\n3. 保护个人隐私和账户安全\n...');
        }
        
        // 显示隐私政策
        function showPrivacy() {
            alert('隐私政策内容（示例）\n\n1. 我们重视您的隐私保护\n2. 不会泄露您的个人信息\n3. 使用加密技术保护数据\n...');
        }
        
        // ✅ 修复：注册成功后跳转
        (function() {
            const urlParams = new URLSearchParams(window.location.search);
            const success = urlParams.get('success');
            
            if (success === 'true') {
                console.log('✅ 注册成功，2秒后跳转到登录页面...');
                
                setTimeout(function() {
                    // ✅ 使用正确的路径
                    window.location.href = contextPath + '/login.jsp';
                }, 2000);
            }
        })();
        // 页面加载完成后的调试信息
        window.addEventListener('DOMContentLoaded', function() {
            console.log('='.repeat(60));
            console.log('📄 注册页面已加载');
            console.log('当前 URL:', window.location.href);
            console.log('上下文路径:', contextPath);
            console.log('表单提交目标:', document.getElementById('registerForm').action);
            console.log('='.repeat(60));
        });
    </script>
</body>
</html>