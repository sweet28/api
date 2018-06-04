<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>交易中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/mobile.css">
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>交易中心</span>
		<a href="javascript:history.go(-1)" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span>152****0000</span>
		</a>
	</header>
	<div class="cl"></div>
	<div class="deal">
		<div class="deal_text">
			<p>今日最高价：10.00</p>
			<p>今日最低价：1.16</p>
			<b><span>$:10.00</span></b>
		</div>
		<div class="row deal_pic">
			<img src="<%=path%>/images/deal.jpg" alt="">
		</div>
		<div class="deal_button">
			<a href="" class="d1">买入CPA</a>
			<a href="" class="d2">卖出CPA</a>
		</div>
	</div>
	<div class="cl"></div>
	<div class="row buyin">
		<div class="col-xs-6 buy_input">
			<input type="text" name="" placeholder="请输入要购买数量" class="num">
			<input type="text" name="" placeholder="请输入购买单价">
		</div>
		<div class="col-xs-6 buy_btn">
			<a href="">买入</a>
		</div>
	</div>
	<div class="cl"></div>
	<div class="user_title">
		<ul>
			<li class="on">用户</li>
			<li>LOD</li>
			<li>单价（$）</li>
			<li>总2价（$）</li>
			<li>状态</li>
		</ul>
	</div>
	<div class="user_list">
		<div class="searchBox">
			<input type="text" class="sea" value="" placeholder="买家账号检索">
			<input type="submit" class="subbtn" value="检索">
		</div>
		<ul>
			<li>
				<span class="us1">1520564***...</span>
				<span class="us2">166</span>
				<span class="us3">1.19</span>
				<span class="us4">197.54</span>
				<span class="us5"><a href="">等待中</a></span>
			</li>
			<li>
				<span class="us1">1520564***...</span>
				<span class="us2">166</span>
				<span class="us3">1.19</span>
				<span class="us4">197.54</span>
				<span class="us5"><a href="">等待中</a></span>
			</li>
			<li>
				<span class="us1">1520564***...</span>
				<span class="us2">166</span>
				<span class="us3">1.19</span>
				<span class="us4">197.54</span>
				<span class="us5"><a href="">等待中</a></span>
			</li>
			<li>
				<span class="us1">1520564***...</span>
				<span class="us2">166</span>
				<span class="us3">1.19</span>
				<span class="us4">197.54</span>
				<span class="us5"><a href="">等待中</a></span>
			</li>
			<li>
				<span class="us1">1520564***...</span>
				<span class="us2">166</span>
				<span class="us3">1.19</span>
				<span class="us4">197.54</span>
				<span class="us5"><a href="">等待中</a></span>
			</li>
			<li>
				<span class="us1">1520564***...</span>
				<span class="us2">166</span>
				<span class="us3">1.19</span>
				<span class="us4">197.54</span>
				<span class="us5"><a href="">等待中</a></span>
			</li>
		</ul>
	</div>

	<div class="space"></div>
	<div class="menu">
		<ul>
			<li class="col-xs-3"><a href="<%=path%>/cpa/minerBuy"><img src="<%=path%>/images/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/images/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/images/menu3.png" alt=""><p>交易中心</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/personal"><img src="<%=path%>/images/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>