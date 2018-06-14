<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
%>   
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>券保理订单</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="<%=path%>/js/trader/my_dd_check.js" type="text/javascript" charset="utf-8"></script> --%>

<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>券保理订单</span>
		<a href="<%=path%>/cpa/personal" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order">
		<ul>
			<li><a href="<%=path%>/cpa/quanbl"><img src="<%=path%>/images/o1.jpg" alt=""><p>我的券保理</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl2"><img src="<%=path%>/images/o3.jpg" alt=""><p>我的待收款</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl3"><img src="<%=path%>/images/o4.jpg" alt=""><p>交易完成</p></a></li>
<%-- 			<li><a href="<%=path%>/cpa/traderMyCheck"><img src="<%=path%>/images/o5.jpg" alt=""><p style="color:red;font-weight:bold;">交易审核</p></a></li> --%>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="order_list">
		<ul>
			<li>
			</li>
		</ul>
	</div>
	
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li><a href="<%=path%>/cpa/main"><img src="<%=path%>/imagenew/menu5.png" alt=""><p>首页</p></a></li>
			<li><a href="<%=path%>/cpa/minerHouse"><img src="<%=path%>/imagenew/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/imagenew/menu3.png" alt=""><p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/imagenew/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/menu4_on.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>