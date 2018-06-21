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
<title>我的矿机</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/mobile.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/css/layer.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/sweetalert/css/sweetalert.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/jquery-dialogbox/jquery.dialogbox-1.0.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.min.js"></script>
<script src="<%=path%>/lib/jquery-dialogbox/jquery.dialogbox-1.0.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>我的A矿机</span>
		<a href="<%=path%>/cpa/personal" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span id="uname"></span>
		</a>
	</header>
	<div class="line"></div>
	<div class="row myshop">
		<ul id="a_miner">
			
		</ul>
	</div>	
	<div class="cl"></div>
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMiner"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/kjyx.png" alt=""><p>A型矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMinerKC"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/kuangchiwx.png" alt=""><p>A型矿池</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMinerB"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/kjwx.png" alt=""><p>B型矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMinerBKC"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/kuangchiwx.png" alt=""><p>B型矿池</p></a></li>
		</ul>
	</div>
</body>
<script src="<%=path%>/js/miner/my_kj.js"></script>
</html>