<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CPA介绍</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/cssnew/style.css">
</head>
<body>
	<header>
		<img src="<%=path%>/imagenew/header.jpg" alt="">
		<div class="searchBox">
			<div class="search">
				<div class="sea">
					<input type="submit" class="index_btn" value="">
					<input type="text" class="index_txt" placeholder="搜索">
				</div>
			</div>
		</div>
	</header>
	<div class="line1"></div>
	<div class="cl"></div>
	<div class="newsConts">
		<div class="row title">CPA介绍</div>
		<div class="row content">
			<section class="coupon_list" >
        	<img alt="" src="<%=path%>/static/html/ppt/1.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/2.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/3.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/4.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/5.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/6.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/7.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/8.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/9.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/10.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/11.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/12.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/13.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/14.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/15.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/16.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/17.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/18.jpg" style="width:100%;">
        	<img alt="" src="<%=path%>/static/html/ppt/19.jpg" style="width:100%;">
        </section>
		</div>
	</div>
	
	<div class="line1"></div>
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li><a href="<%=path%>/cpa/main"><img src="<%=path%>/imagenew/menu5_on.png" alt=""><p>首页</p></a></li>
			<li><a href="<%=path%>/cpa/minerHouse"><img src="<%=path%>/imagenew/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/imagenew/menu3.png" alt=""><p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/imagenew/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>