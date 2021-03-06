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
<title>券保理详情</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>券保理详情</span>
		<a href="<%=path%>/cpa/quanbl" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="buy_detail">
		<div class="row title" id = "name">
			A型券7天
		</div>
		<div class="row desc">
			<div class="bdc bdc1">
				<b id = "sybil">8.00%</b>
				<p>预计周期收益率</p>
			</div>
			<div class="bdc bdc2">
				<b id = "day">7天</b>
				<p>周期</p>
			</div>
		</div>
		<div class="row quota">
			<ul>
				<li><b id="erdu">100</b><p>券保理额度</p></li>
				<li><b id="shouyi">0</b><p>收益额度</p></li>
				<li><b id="chujued">0.002</b><p>出局额度</p></li>
			</ul>
		</div>
	</div>
	<div class="cycle">
		<div class="row title">
			周期
		</div>
		<div class="cycle_list">
			<ul>
				<li class="col-xs-4"><span>购买时间</span><p id="jzdate">2018-05-22</p></li>
				<li class="col-xs-4"><span>计息时间</span><p id="jxdate">2018-05-29</p></li>
				<li class="col-xs-4"><span>到期时间</span><p id="daoqi">2018-06-03</p></li>
			</ul>
		</div>
	</div>
	<div class="cl"></div>
	<div class="order_list">
<!-- 		<ul id="a_miner"> -->
<!-- 			<li style="margin-top: 18px;"> -->
<!-- 				<p>类型：买单</p> <span>订单号：1003834109555900416</span> -->
<!-- 				<p>数目价格：7CPA*0.25$=1.75$</p> <span>总计人民币：11.375元</span><span -->
<!-- 				style='float: right; background: #E91E63; display: inline-block; width: 20%; height: 30px; text-align: center; line-height: 30px;'> -->
<!-- 					<a style='font-weight: bold; color: #fff;' href='traderDetail?5550'>详情</a> -->
<!-- 			</span> -->
<!-- 			</li> -->
<!-- 		</ul> -->
	</div>
	
	<div class="order_list">
<!-- 		<ul id="a_miner"> -->
<!-- 			<li style="margin-top: 18px;"> -->
<!-- 				<p>类型：买单</p> <span>订单号：1003834109555900416</span> -->
<!-- 				<p>数目价格：7CPA*0.25$=1.75$</p> <span>总计人民币：11.375元</span><span -->
<!-- 				style='float: right; background: #E91E63; display: inline-block; width: 20%; height: 30px; text-align: center; line-height: 30px;'> -->
<!-- 					<a style='font-weight: bold; color: #fff;' href='traderDetail?5550'>详情</a> -->
<!-- 			</span> -->
<!-- 			</li> -->
<!-- 		</ul> -->
	</div>
	
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
<script type="text/javascript" src="<%=path%>/js/personal/quan_detail.js"></script>
</html>