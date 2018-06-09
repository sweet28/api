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
<title>提交工单</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/js/personal/gon_dan_list.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header>
		<span>我的工单</span>
		<a href="javascript:history.go(-1)" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="work_list">
		<a href="<%=path%>/cpa/myWorkOrder"><span>提交工单</span></a>
		<a href="<%=path%>/cpa/myWorkOrderList" class="on"><span>工单列表</span></a>
	</div>
	<div class="cl"></div>
	<div class="wlist">
		<ul>
			<li id = "liebiao">
				<!-- <div class="title">
					<span>交易类型</span>
					<span>：</span>
					<span>单号</span>
					<span>：</span>
				</div>
				<div class="desc">
					<span>问题描述</span>
					<span>*</span>
				</div>
				<div class="text">
					<textarea name="" id="" readonly="readonly" placeholder="请具体且准确的描述您的问题，这有助于我们更高效的帮助您！"></textarea>
				</div> -->
			</li>
		</ul>
		
		<ul>
			<li id = "liebiao2">
				 <div class="title">
					<span>交易类型</span>
					<span>：</span>
					<span>单号</span>
					<span>：</span>
				</div>
				<div class="desc">
					<span>问题描述</span>
					<span>*</span>
				</div>
				<div class="text">
					<textarea name="" id="" readonly="readonly" placeholder="请具体且准确的描述您的问题，这有助于我们更高效的帮助您！"></textarea>
				</div> 
			</li>
		</ul>
	</div>
	
<!-- 	<div class="line"></div>
	<div class="space"></div>
 -->	
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