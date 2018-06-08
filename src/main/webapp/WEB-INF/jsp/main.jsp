<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
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
	<nav>
		<ul>
			<li><a href=""><img src="<%=path%>/imagenew/n1.jpg" alt=""><p>个人中心</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n2.jpg" alt=""><p>新闻公告</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n3.jpg" alt=""><p>黑名单</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n4.jpg" alt=""><p>粉丝团</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n5.jpg" alt=""><p>收益</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n6.jpg" alt=""><p>券保理</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n7.jpg" alt=""><p>奖励排行</p></a></li>
			<li><a href=""><img src="<%=path%>/imagenew/n8.jpg" alt=""><p>工单反馈</p></a></li>
		</ul>
	</nav>
	<div class="cl"></div>
	<div class="news">
		<ul>
			<li><a href="">和盛科技公司最近与小明公司...</a></li>
			<li><a href="">和盛科技公司最近与小明公司...</a></li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="coupon">
		<ul>
			<li>
				<div class="row">
					<div class="img">
						<img src="<%=path%>/imagenew/pic.jpg" alt="">
					</div>
					<div class="txt">
						<div class="title">
							<a href="">一星券</a>
						</div>
						<div class="desc">
							<p>所属行业：服务</p>
							<p>专家评估：300W</p>
						</div>
					</div>
					<div class="price">
						<p>2018/2/20</p>
						<a href="">标价：320W</a>
					</div>
				</div>
				<div class="row ping">
					<span>2308人感兴趣</span>
					<span>一次性付款</span>
					<span>四星专家评估</span>
				</div>
			</li>
			<li>
				<div class="row">
					<div class="img">
						<img src="<%=path%>/imagenew/pic.jpg" alt="">
					</div>
					<div class="txt">
						<div class="title">
							<a href="">一星券</a>
						</div>
						<div class="desc">
							<p>所属行业：服务</p>
							<p>专家评估：300W</p>
						</div>
					</div>
					<div class="price">
						<p>2018/2/20</p>
						<a href="">标价：320W</a>
					</div>
				</div>
				<div class="row ping">
					<span>2308人感兴趣</span>
					<span>一次性付款</span>
					<span>四星专家评估</span>
				</div>
			</li>
		</ul>
	</div>
	
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li><a href="#"><img src="<%=path%>/imagenew/menu5.jpg" alt=""><p>首页</p></a></li>
			<li><a href="#"><img src="<%=path%>/imagenew/menu1.jpg" alt=""><p>矿机商城</p></a></li>
			<li><a href="#"><img src="<%=path%>/imagenew/menu2.jpg" alt=""><p>我的矿机</p></a></li>
			<li><a href="#"><img src="<%=path%>/imagenew/menu3.jpg" alt=""><p>交易中心</p></a></li>
			<li><a href="#"><img src="<%=path%>/imagenew/menu4.jpg" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>