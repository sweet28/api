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
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header>
		<span>我的待付款</span>
		<a href="<%=path%>/cpa/personal" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order">
		<ul>
			<li><a href="<%=path%>/cpa/quanbl"><img src="<%=path%>/images/o1.jpg" alt=""><p>我的券保理</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl2"><img src="<%=path%>/images/o3.jpg" alt=""><p>我的待付款</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl3"><img src="<%=path%>/images/o4.jpg" alt=""><p>我的待收款</p></a></li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="coupon">
		<ul id = "lieb">
		
<!-- 			<li> -->
<!-- 				<div class="couponBox"> -->
<!-- 					<div class="title"> -->
<!-- 						<div class="tit"> -->
<!-- 							<span>券保理1星券(7天)</span> -->
<!-- 						</div> -->
<!-- 						<div class="end"> -->
<!-- 							<span>处理中</span> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="desc"> -->
<!-- 						<div class="cd cd1"> -->
<!-- 							<b>10%</b> -->
<!-- 							<p>预计周期收益率</p> -->
<!-- 						</div> -->
<!-- 						<div class="cd cd2"> -->
<!-- 							<b>7天</b> -->
<!-- 							<p>周期</p> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</li> -->
			
		</ul>
	</div>
	
	<div class="line"></div>
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
<script type="text/javascript" src="<%=path%>/js/personal/quanbl2.js"></script>
</html>
