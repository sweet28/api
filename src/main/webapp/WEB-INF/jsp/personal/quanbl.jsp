<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>代金券</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/style.css">
</head>
<body>
	<header>
		<span>代金券</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	
	<div class="coupon">
		<ul>
			<li>
				<div class="couponBox">
					<div class="title">
						<div class="tit">
							<span>A型券7天</span>
						</div>
						<div class="end">
							<span>已结束</span>
						</div>
					</div>
					<div class="desc">
						<div class="cd cd1">
							<b>8.00%</b>
							<p>预计年化收益率</p>
						</div>
						<div class="cd cd2">
							<b>7天</b>
							<p>周期</p>
						</div>
					</div>
				</div>
			</li>
			<li>
				<div class="couponBox">
					<div class="title">
						<div class="tit">
							<span>A型券7天</span>
						</div>
						<div class="end">
							<span>已结束</span>
						</div>
					</div>
					<div class="desc">
						<div class="cd cd1">
							<b>8.00%</b>
							<p>预计年化收益率</p>
						</div>
						<div class="cd cd2">
							<b>7天</b>
							<p>周期</p>
						</div>
					</div>
				</div>
			</li>
			<li>
				<div class="couponBox">
					<div class="title">
						<div class="tit">
							<span>A型券7天</span>
						</div>
						<div class="end">
							<span>已结束</span>
						</div>
					</div>
					<div class="desc">
						<div class="cd cd1">
							<b>8.00%</b>
							<p>预计年化收益率</p>
						</div>
						<div class="cd cd2">
							<b>7天</b>
							<p>周期</p>
						</div>
					</div>
				</div>
			</li>
		</ul>
	</div>
	
	<div class="line"></div>
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
