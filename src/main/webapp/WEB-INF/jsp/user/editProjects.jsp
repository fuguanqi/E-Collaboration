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
    <title>修改项目信息</title>
</head>

<body>
<form id="projectForm" action="${pageContext.request.contextPath}/user/editProjectsSubmit.action" method="post">
<input type="hidden" name="id" value="${project.id}"/>
修改项目信息：
<table width="100%" border="1">
<tr>
    <td>项目名</td>
    <td><input type="text" name="name" value="${project.name}"/></td>
</tr>
<tr>
    <td>项目复制人id</td>
    <td><input type="text" name="userid" value="${project.userid}"/></td>
</tr>
<tr>
    <td>项目创建时间</td>
    <td><input type="text" name="createtime" value="<fmt:formatDate value="${project.createtime}" pattern="yyyy-MM-dd HH:mm:ss" /> "/></td>
</tr>
<tr>
    <td>项目简介</td>
    <td><input type="text" name="detail" value="${project.detail}"/></td>
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
