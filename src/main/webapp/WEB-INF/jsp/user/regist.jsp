<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户注册</title>
</head>
<body>
<form  action="${pageContext.request.contextPath }/user/regist">
    <table border="1">
        <input type="hidden" name="id"/>
        请输入个人信息：
        <tr>
            <td>用户名</td>
            <td><input type="text" id="username"name="username"></td>
        </tr>
        <tr>
            <td>生日</td>
            <td><input type="text" id="birthday"name="birthday" /></td>
        </tr>
        <tr>
            <td>性别</td>
            <td><input type="text" id="sex"name="sex"></td>
        </tr><tr>
        <td>地址</td>
        <td><input type="text"id="address" name="address"></td>
    </tr><tr>
        <td>密码</td>
        <td><input type="password" id="password"name="password"></td>
    </tr>
        <tr>
            <td><input type="submit" value="注册"id="regist"></td>
        </tr>
    </table>
</form>
</body>
</html>