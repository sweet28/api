<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/mobile.css">
<style>
	html{
		width: 100%;
		height: 100%;
		background: url(images/car.jpg) no-repeat;
		background-size: 100% 100%;
		max-width: 1080px;
	}
</style>
</head>
<body>
	<div class="row index_logo">
		<img src="images/index_logo.png" alt="">
	</div>
	<div class="login">
		<div class="row input-user-index">
			<input type="text">
		</div>
		<div class="row pr input-pass-index">
			<input type="password">
			<a href="">获取验证码</a>
		</div>
		<div class="row pr input-vis-index">
			<input type="text" placeholder="请输入验证码">
			<a href=""><img src="images/vis.png" alt=""></a>
		</div>
		<div class="row input-sub-index">
			<input type="submit" value="注册" class="login">
			<input type="button" value="忘记密码?" class="forget">
		</div>
	</div>
</body>
</html>