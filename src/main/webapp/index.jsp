<%@page language="java"  contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<html>
<body>
<h2>Hello Worssssld!</h2>

<form action="${pageContext.request.contextPath }/file/upload"
      method="post" enctype="multipart/form-data">
    选择文件:<input type="file" name="file" width="120px">
    <input type="text" name="projectId" value="1" />
    <input type="text" name="uploaderId" value="1" />
    <input type="text" name="uploaderName" value="liuxi" />
    <input type="submit" value="上传">
</form>
<hr>
<form action="${pageContext.request.contextPath }/file/down"
      method="get">
    <input type="submit" value="下载">
</form>

</body>
</html>
