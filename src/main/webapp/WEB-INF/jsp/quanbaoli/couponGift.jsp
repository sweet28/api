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
<title>券保理</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>

<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>券保理-积分</span>
		<a href="<%=path%>/cpa/personal" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order">
		<ul>
			<li><a href="<%=path%>/cpa/couponGift"><img src="<%=path%>/imagenew/pic1.png" alt=""><p>我的券积分</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl"><img src="<%=path%>/images/o1.jpg" alt=""><p>我的券保理</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl2"><img src="<%=path%>/images/o3.jpg" alt=""><p>我的待付款</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl3"><img src="<%=path%>/images/o4.jpg" alt=""><p>我的待收款</p></a></li>
		</ul>
	</div>
	<div class="cl">
	</div>
	<div class="order_list">
		<ul>
			<li><span style="color:red;font-weight:bold;">积分规则</span>
				<div class='text'>
					<p><b>1、获取规则:</b>用户本人认筹券保理；<b style="color:red;">本人认筹时间起始</b>，比其<b style="color:red;">晚认筹</b>的直推粉丝每认筹成功一份券保理，用户本人获得直推粉丝所认筹券保理价值<b style="color:#E91E63;">10%</b>的券积分。</p>
					<p><b>2、提现规则:</b>券积分为<b style="color:#E91E63;">200</b>的倍数方可提现。</p>
					<p><b>3、其他规则:</b>券积分与人民币兑换比例为<b style="color:#E91E63;">1:1</b>。</p>
				</div>
			</li>
		</ul>
		<p style="color:#f1f0f6;">******</p>
		<ul>
			<li>
				<span style="color:#E91E63;font-weight:bold;">券积分信息</span>
				<p>券积分预计入账总数:<b id="couponTotalScore">0</b></p>
				<p>券积分成功入账总数:<b id="couponTotalScoreReal"></b></p>
				<p>已提现总数:<b id="couponYiyongScore">0</b></p>
				<span style='float:right;color:#fff;background: #E91E63;display: inline-block;width: 20%;height: 30px;text-align: center;line-height: 30px;'>
					<a style="color:#fff;" href="javascript:toMoney();">提现</a>
				</span>
			</li>
		</ul>
		<p style="color:#f1f0f6;">******</p>
		<ul>
			<li>
				<span style="color:#E91E63;font-weight:bold;">直推粉丝认筹券保理信息</span>
				<p>1星券(7天)认筹总份数:<b id="one7">0</b>&nbsp;&nbsp;&nbsp;&nbsp;认筹成功总份数:<b id="one7Real"></b></p>
				<p>1星券(21天)认筹总份数:<b id="one21">0</b>&nbsp;&nbsp;&nbsp;&nbsp;认筹成功总份数:<b id="one21Real"></b></p>
				<p>2星券(15天)认筹总份数:<b id="two15">0</b>&nbsp;&nbsp;&nbsp;&nbsp;认筹成功总份数:<b id="two15Real"></b></p>
				<p>3星券(10天)认筹总份数:<b id="three10">0</b>&nbsp;&nbsp;&nbsp;&nbsp;认筹成功总份数:<b id="three10Real"></b></p>
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
<script src="<%=path%>/js/personal/couponGift.js" type="text/javascript" charset="utf-8"></script>
</html>