<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
    <meta charset="utf-8">
    <title>Login on site</title>
    <style>
        <%@include file="/css/style.css"%>
    </style>
</head>

<body>

<div class="form-container">
    <img src="img/bgImg.jpg" alt="" class="bg__img">
    <form class="form-box" action="controller" method="post">

        <img src="img/user.png" alt="" class="user">
        <h1>Welcome</h1>
        ${error}
        ${info}
        <input type="hidden" value="login" name="command">

        <input type="text" name="login" placeholder="Your login">

        <input type="password" name="password" placeholder="Password">

        <input type="submit" name="" value="Login">

        <a href="#" class="forgot__link">Forgot password?</a>
        <a href="registration.jsp" class="forgot__link">Register</a>

    </form>
</div>

</body>
</html>
