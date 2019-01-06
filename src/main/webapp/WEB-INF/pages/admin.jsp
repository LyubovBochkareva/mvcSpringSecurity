<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
</head>
<body>
<div class="generic-container">
    <%@include file="authheader.jsp" %>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Login</th>
                <th>Password</th>
                <th>Name</th>
                <th>Age</th>
                <th>Role</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listUsers}" var="user">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.password}</td>
                    <td>${user.name}</td>
                    <td>${user.age}</td>
                    <td>${user.role}</td>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td><a href="<c:url value='editUser?id=${user.id}' />" class="btn btn-success custom-width">edit</a></td>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <td><a href="<c:url value='deleteUser?id=${user.id}' />" class="btn btn-danger custom-width">delete</a></td>
                    </sec:authorize>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <sec:authorize access="hasRole('ADMIN')">
        <div class="well">
            <a href="<c:url value='/addUser' />">Add New User</a>
        </div>
    </sec:authorize>
</div>
</body>
</html>