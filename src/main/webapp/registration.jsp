<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
	<meta charset="utf-8">
	<title>Registration on site</title>
	<style>
		<%@include file="/css/style.css"%>
	</style>
	<link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="form-container">
	<img src="img/bgImg.jpg" alt="" class="bg__img">
	<form class="form-box" action="controller" method="post">
		<img src="img/user.png" alt="" class="user">
		<h1>Hello</h1>
		${error}
		<input type="hidden" name="command" value="registration">
		<input type="text" name="name" placeholder="Your Name" />
		<input type="text" name="lastname" placeholder="Your Last Name" />
		<input type="text" name="login" placeholder="Your Login" />
		<input type="password" name="pass"  placeholder="Password" />
		<input type="text" name="contact" placeholder="Contact no" />

		<input type="submit" name="signup" value="Register" />
		<a href="#" class="forgot__link">Forgot password?</a>
		<a href="login.jsp" class="forgot__link">Login</a>
	</form>
</div>

</body>
</html>
