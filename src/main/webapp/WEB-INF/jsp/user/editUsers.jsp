<%--
  Created by IntelliJ IDEA.
  User: 10046
  Date: 2017/12/6
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charsest=UTF-8">
    <title>修改个人信息</title>
</head>

<body>
<form id="userForm" action="${pageContext.request.contextPath}/user/editUsersSubmit.action" method="post">
<input type="hidden" name="id" value="${user.id}"/>
修改个人信息：
<table width="100%" border="1">
<tr>
    <td>用户名</td>
    <td><input type="text" name="username" value="${user.username}"/></td>
</tr>
<tr>
    <td>密码</td>
    <td><input type="text" name="password" value="${user.password}"/></td>
</tr>
    <tr>
        <td>性别</td>
        <td><input type="text" name="sex" value="${user.sex}"/></td>
    </tr>
<tr>
<td>生日</td>
<td><input type="text" name="birthday" value="<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd HH:mm:ss" /> "/></td>
</tr>
    <tr>
        <td>地址</td>
        <td><input type="text" name="address" value="${user.address}"/></td>
    </tr>
<%--<tr>
    <td>商品图片</td>
    <td>
    <c:if test="${item.pic!=null}">
        <img src="/pic/${item.pic}" width="100" height="100"/>
        <br/>
    </c:if>
    <input type="file" name="pictureFile"/>
    </td>
</tr>--%>

<tr>
    <td colspan="2" align="center"><input type="submit" value="提交"/></td>
</tr>

    </table>
    </form>
    </body>
    </html>
