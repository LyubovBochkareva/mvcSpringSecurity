<%--
  Created by IntelliJ IDEA.
  User: lyubov
  Date: 05.01.19
  Time: 2:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="authbar">
    <span>Dear <strong>${loggedinuser}</strong>, Welcome to CrazyUsers.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
</div>
</body>
</html>
