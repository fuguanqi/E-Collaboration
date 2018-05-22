<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>个人主页</title>
</head>
<body>

<form action="${pageContent.requset.contextPath }/user/profile.action" menthod="post">
    <input type="hidden" name="id" value="${user.id}"/>
   个人信息
    <table width="100%" border=1>
        <tr>
            <td>用户id</td>
            <td>${user.id}</td>
        </tr>
        <tr>
            <td>用户名</td>
            <td>${user.username}</td>
        </tr>
        <tr>
            <td>密码</td>
            <td>${user.password}</td>
        </tr>
        <tr>
            <td>性别</td>
            <td>${user.sex}</td>
        </tr>
        <tr>
            <td>生日</td>
            <td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
        </tr>
        <tr>
            <td>地址</td>
            <td>${user.address}</td>
        </tr>
        <td><a href="${pageContent.request.contextPath }/user/editUsers.action?id=${user.id}">修改</a></td>
        <td><a href="${pageContent.request.contextPath }/user/create.action">新建项目</a></td>

    </table>
</form>
</body>
</html>