<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建项目</title>
</head>
<body>
<form  action="${pageContext.request.contextPath }/user/create">
    <table border="1">
        <input type="hidden" name="id"/>
        请输入项目信息：
        <tr>
            <td>项目名</td>
            <td><input type="text" id="name"name="name"></td>
        </tr>
        <tr>
            <td>项目负责人id</td>
            <td><input type="text" id="userid"name="userid" /></td>
        </tr>
        <tr>
            <td>项目创建时间</td>
            <td><input type="text" id="createtime"name="createtime" /></td>
        </tr>
        <tr>
            <td>简介</td>
            <td><input type="text" id="detail"name="detail"></td>
        </tr>

        <tr>
            <td><input type="submit" value="创建"id="create"></td>
        </tr>
    </table>
</form>
</body>
</html>