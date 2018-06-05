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
<title>订单详情</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header>
		<span>订单详情</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order_details">
		<div class="row pic">
			<img src="<%=path%>/images/success.jpg" alt="">
		</div>
		<div class="row title">
			<span>提交成功</span>
		</div>
		<div class="row desc">
			恭喜您！订单已成功提交！谢谢您对我们工作的支持！
		</div>
		<div class="bank">
			<ul>
				<li>
					<span>开户银行：中国建设银行</span>
					<p>6227 **** 2356 4569 111</p>
				</li>
				<li>
					<span>开户银行：中国建设银行</span>
					<p>6227 **** 2356 4569 111</p>
				</li>
				<li>
					<span>开户银行：中国建设银行</span>
					<p>6227 **** 2356 4569 111</p>
				</li>
				<li>
					<span>开户银行：中国建设银行</span>
					<p>6227 **** 2356 4569 111</p>
				</li>
				<li>
					<span>开户银行：中国建设银行</span>
					<p>6227 **** 2356 4569 111</p>
				</li>
				<li>
					<span>开户银行：中国建设银行</span>
					<p>6227 **** 2356 4569 111</p>
				</li>
			</ul>
		</div>
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