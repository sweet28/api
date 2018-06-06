<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>个人中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/css/mobile.css">
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
		<span>个人中心</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="username">
			<span id="uname"></span>
		</a>
	</header>
	<div class="row default">
		<p><a href="javascript:Logout();">安全退出</a><span class="fr">CPA总额:<span id="returnIn_num"></span><span id="returnIn_dec" class="num_deci"></span></span></p>
		<p>可用CPA:<span id="balance_num"></span><span id="balance_dec" class="num_deci"></span></p>
		<p>冻结CPA:<span id="waitNum_num"></span><span id="waitNum_dec" class="num_deci"></span></p>
	</div>	
	<div class="cl"></div>
	<div class="menu_list">
		<ul>
			<li><a href="<%=path%>/cpa/myMiner" class="m1"><span>我的矿机</span></a></li>
			<li><a href="<%=path%>/cpa/traderMyGD" class="m2"><span>我的订单</span></a></li>
 			<li><a href="" class="m4"><span>我的收益</span></a></li>
			<li><a href="" class="m5"><span>我的粉丝</span></a></li>
			<li><a href="" class="m6"><span>邀请链接</span></a></li>
			<li><a href="" class="m8"><span>个人资料</span></a></li>
			<li><a href="" class="m3"><span>支付管理</span></a></li>
			<li><a href="" class="m9"><span>安全中心</span></a></li>
			<li><a href="" class="m11"><span>系统公告</span></a></li>
			<li><a href="" class="m10"><span>联系我们</span></a></li>
			<li><a href="" class="m12"><span>我的工单</span></a></li>
			<!-- <li><a href="" class="m7"><span>APP下载</span></a></li> -->
		</ul>
	</div>
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li class="col-xs-3"><a href="<%=path%>/cpa/minerHouse"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/shangchengweixuanzhong.png" alt=""><p>矿机商城</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMiner"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/kuangjiweixuanzhong.png" alt=""><p>我的矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/traderCenter"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/jyweixuanzhong.png" alt=""><p>交易中心</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/personal"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/gerenzhongxin.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>

<script src="<%=path%>/js/personal/personal.js" type="text/javascript" charset="utf-8"></script>

</html>