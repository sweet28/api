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
<title>矿机商城</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/mobile.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script type="text/javascript" src="<%=path%>/lib/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="<%=path%>/lib/js/jquery-2.1.4.min.js"></script>

<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>

<script type="text/javascript" src="<%=path%>/js/wapframwork.js"></script>
<script type="text/javascript" src="<%=path%>/js/miner/invest_kj.js"></script>
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>矿机商城</span>
		<a href="javascript:history.go(-1)" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span id="uname"></span>
		</a>
	</header>
	<div class="row pro">
	</div>
	<div class="cl"></div>
	<div class="row shop_title">
		<span>A矿场</span>
	</div>
	<div class="row shop_list">
		<ul>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner1.png" style='max-width: 90%;'></a>
				</div>
				<div class="text">
					<p><a href="">CA1型</a> <span>价格：10CPA</span></p>
					<p>产量/小时：0.0305555556</p>
					<p>产币总量：11CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="javascript:agoumai(1,1);">购买</a>
				</div>
			</li>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner2.png" style='max-width: 90%;'></a>
				</div>
				<div class="text">
					<p><a href="">CA2型</a> <span>价格：100CPA</span></p>
					<p>产量/小时：0.319444444</p>
					<p>产币总量：115CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="javascript:agoumai(1,2);">购买</a>
				</div>
			</li>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner3.png" style='max-width: 90%;'></a>
				</div>
				<div class="text">
					<p><a href="">CA3型</a> <span>价格：1000CPA</span></p>
					<p>产量/小时：3.19444444</p>
					<p>产币总量：1150CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="javascript:agoumai(1,3);">购买</a>
					<!-- <a href="#">备货中</a> -->
				</div>
			</li>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner4.png" style='max-width: 90%;'></a>
				</div>
				<div class="text">
					<p><a href="">CA4型</a> <span>价格：5000CPA</span></p>
					<p>产量/小时：16.6666667</p>
					<p>产币总量：6000CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="javascript:agoumai(1,4);">购买</a>
					<!-- <a href="#">备货中</a> -->
				</div>
			</li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="row shop_title">
		<span>B矿场</span>
	</div>
	<div class="row shop_list">
		<ul>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner21.png"></a>
				</div>
				<div class="text">
					<p><a href="">CB1型</a> <span>价格：5CPA</span></p>
					<p>产量/小时：0.0152777778</p>
					<p>产币总量：5.5CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="javascript:agoumai(2,1);">购买</a>
				</div>
			</li>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner22.png"></a>
				</div>
				<div class="text">
					<p><a href="">CB2型</a> <span>价格：50CPA</span></p>
					<p>产量/15：0.152777778</p>
					<p>产币总量：55CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="javascript:agoumai(2,2);">购买</a>
				</div>
			</li>
			<li>
				<div class="img">
					<a href=""><img src="<%=path%>/imagenew/miner23.png"></a>
				</div>
				<div class="text">
					<p><a href="">CB3型</a> <span>价格：500CPA</span></p>
					<p>产量/小时：1.52777778</p>
					<p>产币总量：550CPA</p>
					<p>运行周期：360小时</p>
				</div>
				<div class="more">
					<a href="#">备货中</a>
					<!-- <a href="javascript:agoumai(2,3);">购买</a> -->
				</div>
			</li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li><a href="<%=path%>/cpa/main"><img src="<%=path%>/imagenew/menu5.png" alt=""><p>首页</p></a></li>
			<li><a href="<%=path%>/cpa/minerHouse"><img src="<%=path%>/imagenew/menu1_on.png" alt=""><p>矿机商城</p></a></li>
			<li><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/imagenew/menu3.png" alt=""><p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/imagenew/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>