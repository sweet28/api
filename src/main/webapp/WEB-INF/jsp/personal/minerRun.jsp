<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>运行</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/mobile.css">
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>运行</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span>G25634845</span>
		</a>
	</header>
	
	<div class="row move">
		<div class="title">
			我的：微型云矿机
		</div>
		<div class="moving">
			<img src="<%=path%>/images/move.png" alt="">
		</div>
		<div class="number">
			<span>0.01192168</span>
		</div>
		<div class="text">
			<p>我的算力：0.01GH/s</p>
			<p>累计获得：0.01192168CPA</p>
			<p>全网算力：9,869.14GH/s</p>
		</div>
	</div>
	
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li class="col-xs-3"><a href="#"><img src="<%=path%>/images/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li class="col-xs-3"><a href="#"><img src="<%=path%>/images/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li class="col-xs-3"><a href="#"><img src="<%=path%>/images/menu3.png" alt=""><p>交易中心</p></a></li>
			<li class="col-xs-3"><a href="#"><img src="<%=path%>/images/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>