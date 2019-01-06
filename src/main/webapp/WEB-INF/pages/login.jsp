<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns:th="http://www.thymeleaf.org">
<head th:include="layout :: head(title=~{::title},links=~{})">
    <title>Please Login</title>
</head>
<body th:include="layout :: body" th:with="content=~{::content}">
<div th:fragment="content">
    <c:url var="loginUrl" value="/login" />
    <form action="${loginUrl}" method="post" class="form-horizontal">
        <fieldset>
            <legend>Please Login</legend>
            <div th:if="${param.error}" class="alert alert-error">
                Invalid username and password.
            </div>
            <div th:if="${param.logout}" class="alert alert-success">
                You have been logged out.
            </div>
            <div class="input-group input-sm">
                <label class="input-group-addon" for="login"><i class="fa fa-user"></i></label>
                <input type="text" class="form-control" id="login" name="login" placeholder="Enter Login" required>
            </div>
            <div class="input-group input-sm">
                <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
            </div>
            <div class="form-actions">
                <input type="submit"
                       class="btn btn-block btn-primary btn-default" value="Log in">
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>

