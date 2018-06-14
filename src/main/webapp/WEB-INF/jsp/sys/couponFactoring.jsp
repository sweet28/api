<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>券保理-种子招募计划</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/cssnew/style.css">
</head>
<body>
<div class="wrap coupon_page">
    <section class="container" style="height: 100%;">
        <header class="header giftRewards_title" style="display: none;">
            <a href="javascript:history.go(-1)">
                <i class="icon">返回</i>
            </a>
            <span>券保理-种子招募计划</span>
            <a href="personal.html">
                <i class="icon"></i>
            </a>
        </header>
        <section class="coupon_list" >
        	<img alt="" src="http://www.artforyou.cn/images/cpa_qbl.png" style="width:100%;">
        </section>
    </section>
</div>
<div class="line1"></div>
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

</html>