<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>资料库</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/sweetalert/css/sweetalert.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.min.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
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
	<div class="row">
	</div>
	<div class="cl"></div>
	<div class="newsList">
		<ul>
			<li>
				<div class="row title">
					<a href="<%=path%>/cpa/couponFactoring">券保理--车派种子用户招募计划</a>
					<span>2018-05-10</span>
				</div>
				<div class="row desc">
				</div>
			</li>
			<li>
				<div class="row title">
					<a href="<%=path%>/cpa/whitePaper">CPA白皮书</a>
					<span>2018-05-10</span>
				</div>
				<div class="row desc">
				</div>
			</li>
			<li>
				<div class="row title">
					<a href="<%=path%>/cpa/cpaIntro">CPA介绍</a>
					<span>2018-05-10</span>
				</div>
				<div class="row desc">
				</div>
			</li>
		</ul>
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