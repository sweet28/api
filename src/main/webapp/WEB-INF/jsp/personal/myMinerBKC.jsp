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
<title>我的矿池</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/mobile.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/css/layer.css"/>
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>B矿场矿池</span>
		<a href="<%=path%>/cpa/personal" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span id="uname"></span>
		</a>
	</header>
	<div class="line"></div>
	<div class="row myshop"  id="a_miner">
		<%-- <ul>
			<li>
				<div class="img">
					<img src="<%=path%>/images/p1.jpg">
				</div>
				<div class="text">
					<a href="">CA1型</a>
					<p>运行时长：<b>1440小时</b></p>
<!-- 					<p>产量/小时：0.00......</p> -->
					<!-- <p>矿机编号：H4256655</p> -->
					<p>矿机状态：<b>正在运行</b></p>
				</div>
				<div class="look">
					<a href="">查看</a>
				</div>
			</li>
		</ul> --%>
	</div>	
	<div class="cl"></div>
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/images/menu2.png" alt=""><p>A矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMinerKC"><img src="<%=path%>/images/menu2.png" alt=""><p>A矿池</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMinerB"><img src="<%=path%>/images/menu2.png" alt=""><p>B矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMinerBKC"><img src="<%=path%>/images/menu2.png" alt=""><p>B矿池</p></a></li>
		</ul>
	</div>
</body>
<script src="<%=path%>/js/miner/my_kjbc.js"></script>
</html>