<%--
  Created by IntelliJ IDEA.
  User: 10046
  Date: 2017/12/9
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jsonTest</title>
    <meta http-equiv="Content-Type" content="text/html;charsest=UTF-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
        function requestJson(){
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/login',
                contentType:'application/json;charset=utf-8',
                data:'{"name":"手机",password:"123"}',
                success:function(data){
                    alert(data);
                }
            });
        }
        function responseJson() {
            $.ajax({
                type:'POST',
                url:'${pageContext.request.contextPath}/login',
                data:'username=手机&password=9999',
                success:function(data){
                    alert(data);
                }
            });
        }
    </script>

</head>
<body>
<input type="button" onclick="requestJson()" value="请求json，输出json"/>
<input type="button" onclick="responseJson()" value="请求key/value，输出json"/>
</body>
</html>
