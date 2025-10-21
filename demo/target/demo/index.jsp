<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Welcome to the Demo Application</h1>
    <p>
        这是一个动态生成的页面。 <br/>
        服务器当前时间是: <strong><%= new Date() %></strong>
    </p>
</body>
</html>
