<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>注册</title>
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
	<div class="row index_logo" style="margin-top: -70px;margin-bottom: -66px;">
		<img src="images/index_logo.png" alt="">
	</div>
	<div class="login" style="height: 0px;">
		<div class="row input-user-index">
			<input type="text" placeholder="请输入手机号码">
		</div>
		<form name="form0" id="form0"  enctype="multipart/form-data">  
		      <div><span style="font-size:18px;text-align:center;color:red">点击此处上传身份证正面图片(小于2M)</span><input type="file" accept="image/*"name="file0" id="file0" placeholder="点击此处上传身份证正面图片(小于2M)"/><br><img src="" id="img0">
	    </form>
	    <div class="row input-name-index">
			<input type="text" placeholder="姓名" oninput="if(value.length>11)value=value.slice(0,11)" disabled="disabled">
		</div>
		<div class="row input-card-index">
			<input type="text" placeholder="身份证号" oninput="if(value.length>11)value=value.slice(0,18)" disabled="disabled">
		</div>
		<div class="row pr input-pass-index">
			<input type="password" placeholder="请输入密码">
		</div>
		<div class="row pr input-pass-index">
			<input type="text">
			<a href="">获取验证码</a>
		</div>
		
		<div class="row input-user-index">
			<input type="text" placeholder="邀请人手机号码">
		</div>
		<div class="row input-sub-index">
			<input type="submit" value="注册" class="login" style = "width:66%">
		</div>
	</div>
</body>
</html>