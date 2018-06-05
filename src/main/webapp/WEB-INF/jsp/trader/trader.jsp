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
<title>我的订单</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>我的订单</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order">
		<ul>
			<li><a href=""><img src="<%=path%>/images/o1.jpg" alt=""><p>发起中</p></a></li>
			<li><a href=""><img src="<%=path%>/images/o2.jpg" alt=""><p>待付款</p></a></li>
			<li><a href=""><img src="<%=path%>/images/o3.jpg" alt=""><p>已付款</p></a></li>
			<li><a href=""><img src="<%=path%>/images/o4.jpg" alt=""><p>交易完成</p></a></li>
			<li><a href=""><img src="<%=path%>/images/o5.jpg" alt=""><p>交易取消</p></a></li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="order_list">
		<ul>
			<li>
				<span>卖出</span>
				<p>256.0SUNBTC*12$=3097.6$</p>
				<span>总计人民币：21063.68元</span>
			</li>
		</ul>
	</div>
	
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li class="col-xs-3"><a href="<%=path%>/cpa/minerHouse"><img src="<%=path%>/images/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/images/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/images/menu3.png" alt=""><p>交易中心</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/personal"><img src="<%=path%>/images/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>