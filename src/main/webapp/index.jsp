<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/mobile.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/css/layer.css"/>
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/js/loading.js"></script>
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
			<input type="text" id="phone" placeholder="请输入手机号">
		</div>
		<div class="row pr input-pass-index">
			<input type="password" id="pwd" placeholder="请输入密码">
		</div>
		<!-- <div class="row pr input-vis-index">
			<input type="text" placeholder="请输入验证码">
			<a href=""><img src="images/vis.png" alt=""></a>
		</div> -->
		<div class="row input-sub-index">
			<!-- <input type="submit" value="注册" class="forget" id = "reg_btn"> -->
			<input type="button" value="登录" class="login" style="width:80%;height:3em;" id = "login_btn">
		</div>
	</div>
</body>
<script src="<%=path%>/js/login.js" type="text/javascript" charset="utf-8"></script>
</html>