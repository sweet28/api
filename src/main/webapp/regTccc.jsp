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
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
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
			<input placeholder="请输入手机号码" type="number" id="phonenum" oninput="if(value.length>11)value=value.slice(0,11)"
                           onkeyup="value = value.replace(/[^\d{2}\.]/g, '').replace(/(\.\d{2}).*/g, '$1')">
		</div>
		<form name="form0" id="form0"  enctype="multipart/form-data">  
		      <span style="font-size:18px;text-align:center;color:red">点击此处上传身份证正面图片(小于2M)</span>
		      <input type="file" accept="image/*"name="file0" id="file0" placeholder="点击此处上传身份证正面图片(小于2M)"/><br>
		      <img src="" id="img0" style="width:10%;">
	    </form>
	    <div class="row input-name-index">
			<input type="text" id="uname" placeholder="姓名" oninput="if(value.length>11)value=value.slice(0,11)"/>
		</div>
		<div class="row input-card-index">
			<input type="text" id="cardnum" placeholder="身份证号" oninput="if(value.length>11)value=value.slice(0,18)"/>
		</div>
		<div class="row pr input-pass-index">
			<input type="password" id="password" placeholder="密码6-20位字母数字组合" maxlength="20">
		</div>
		<div class="row pr input-pass-index">
			<input type="text" id="captcha" placeholder="请输入右侧图片验证码" name="captcha" style="width:75%;" maxlength="8" />  
			<img id="changeCaptcha" src="captcha/getCaptchaCode" onclick="changePic();"/>
		</div>
		<div class="row pr input-pass-index">
			<input type="text" id="valicode" placeholder="短信验证码" maxlength="6"
                           onkeyup="value = value.replace(/[^\d{2}\.]/g, '').replace(/(\.\d{2}).*/g, '$1')" />
			<a><span class="get_code" id="get_valicode">获取验证码</span></a>
		</div>
		
		<div class="row input-user-index">
			<input id="recommend_p" type="text" placeholder="邀请人手机号码(需要通过邀请链接)" disabled="disabled" value="17315049290"/>
		</div>
		<div class="row input-sub-index" id="regClassbtn">
			<input type="button" id="register_btn" value="注册" class="login" style = "width:66%" />
		</div>
	</div>
</body>
<script src="<%=path%>/js/reg.js" type="text/javascript" charset="utf-8"></script>
</html>