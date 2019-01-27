<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login page</title>
    <link th:replace="fragments :: link_css"></link>
</head>
<body onload='document.loginForm.username.focus();'>
<h1 align="center" class="login-h1">Login page</h1>

<div class="container">
    <form class="form-signin" name='loginForm' action="" method='POST'>
        <input class="form-control" placeholder="login" type="text" name="username"/>
        <input class="form-control" placeholder="password" type="password" name="password"/>
        <div class="${not empty error}?('error') bg-danger" th:text="${error}"></div>
        <div class="${not empty msg}?('msg') bg-danger" th:text="${msg}"></div>
        <br/>
        <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Login"/>
        <a class="btn btn-lg btn-primary btn-block" href="/registration">Registration</a>
    </form>
</div>
</body>

</html>