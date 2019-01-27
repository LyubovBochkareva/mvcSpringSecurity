<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Admin</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link th:replace="fragments :: link_css"></link>
</head>
<body>
<th:block th:replace="fragments :: header-panel"></th:block>

<div class="container-fluid">
    <div class="row">
        <h2 align="center">Admin page</h2>
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <table class="table table-striped">
            <tr>
                <td><b>Login</b></td>
                <td><b>Password</b></td>
                <td><b>Name</b></td>
                <td><b>Age</b></td>
                <td><b>Role</b></td>
            </tr>
            <c:forEach items="${listUsers}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.roles}</td>
                    <td>
                        <a href="" th:href="@{/admin/users/__${user.id}__/update}">
                            <button type="button" class="btn btn-info btn-success">Update</button>
                        </a>
                    </td>
                    <td>
                        <a href="" th:href="@{/admin/products/__${user.id}__/delete}">
                            <button type="button" class="btn btn-danger btn-success">Delete</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</div>
</body>
</html>

