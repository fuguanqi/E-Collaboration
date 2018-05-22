<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>项目主页</title>
</head>
<body>

<form action="${pageContent.requset.contextPath }/user/project.action" menthod="post">

    项目信息
    <table width="100%" border=1>
        <tr>
            <td>项目id</td>
            <td>${project.id}</td>
        </tr>
        <tr>
            <td>项目名</td>
            <td>${project.name}</td>
        </tr>
        <tr>
            <td>项目负责人id</td>
            <td>${project.userid}</td>
        </tr>
        <tr>
            <td>项目创建时间</td>
            <td><fmt:formatDate value="${project.createtime}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
        </tr>
        <tr>
            <td>项目简介</td>
            <td>${project.detail}</td>
        </tr>
        <td><a href="${pageContent.request.contextPath }/user/editProjects.action?id=${project.id}">修改</a></td>


    </table>
</form>
</body>
</html>