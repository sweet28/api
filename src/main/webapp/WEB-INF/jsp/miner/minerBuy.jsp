<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>无标题文档</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/mobile.css">
</head>
<body>
	<header>
		<span>购买</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span>G25634845</span>
		</a>
	</header>
	<div class="row top">
		<h3>微型云矿机</h3>
	</div>
	<div class="row shop_detail">
		<b>商品详情：</b>
		<p>微型云矿机：价格10CPA，算力为0.01GH/S,<br>
		运行周期为1440小时，每小时产量<br>
		0.0090277778CPA，总收益13CPA</p>
	</div>
	<div class="line"></div>
	<div class="row buy">
		<p><b>10</b></p>
		<div class="cl"></div>
		<a href="#">立即购买</a>
	</div>
</body>
</html>