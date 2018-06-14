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
<title>我的收益</title>
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
		<span>我的收益</span>
		<a href="<%=path%>/cpa/personal" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order">
		<ul>
			<li><a href="<%=path%>/cpa/myEarnings"><img src="<%=path%>/images/niu.png" alt=""><p>个人算力</p></a></li>
			<li><a href="<%=path%>/cpa/myEarnings2"><img src="<%=path%>/images/ma.png" alt=""><p>直推算力</a></li>
			<li><a href="<%=path%>/cpa/myEarnings3"><img src="<%=path%>/images/long.png" alt=""><p>直推收益</p></a></li>
			<li><a href="<%=path%>/cpa/myEarnings4"><img src="<%=path%>/images/ma.png" alt=""><p style="color:red;font-weight:bold;">节点收益</p></a></li>
			<li><a href="#"><img src="<%=path%>/images/niu.png" alt=""><p>其他</p></a></li>
		</ul>
	</div>
	<div class="cl">
	</div>
	<div class="order_list">
		<ul>
			<li><span style="color:red;">节点信息</span>
				<div class='text'>
					<p>粉丝用户等级:<b id="fensgrade"></b></p>
					<p>粉丝团人数(人):<b id="fenstuan"></b></p>
					<p>粉丝团算力(G):<b id="fenssl"></b></p>
				</div>
			</li>
		</ul>
	</div>
	
	<div class="cl">
	</div>
	<div class="order_list">
		<ul>
			<li><span style="color:red;">节点算力奖励</span>
				<div class='text' id = "fcpgift">
					<p><b></b></p>
				</div>
				<div class='look'>
					<a href='#'></a>
				</div>
			</li>
		</ul>
		<ul id = "gift">
		</ul>
	</div>
	
	<div class="cl">
	</div>
	<div class="order_list">
		<ul>
			<li><span style="color:red;">节点全球交易分红记录</span>
				<div class='text' id="earngift">
					<p><b></b></p>
				</div>
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
<script src="<%=path%>/js/personal/my_earning4.js" type="text/javascript" charset="utf-8"></script>
</html>