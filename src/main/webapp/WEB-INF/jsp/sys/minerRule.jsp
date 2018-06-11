<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>挖矿(游戏)规则</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1.0"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <link rel="stylesheet" href="<%=path%>/static/css/common.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/index.css"/>
    <link rel="stylesheet" href="<%=path%>/static/css/personalCenter.css" type="text/css">
</head>
<body>
<div class="wrap coupon_page">
    <section class="container" style="height: 100%;">
        <header class="header giftRewards_title" style="display: none;">
            <a href="javascript:history.go(-1)">
                <i class="icon">返回</i>
            </a>
            <span>挖矿规则</span>
            <a href="personal.html">
                <i class="icon"></i>
            </a>
        </header>
        <section class="coupon_list" >
        	<img alt="" src="<%=path%>/static/html/cpa_miner.jpg" style="width:100%;">
        </section>
    </section>
</div>
</body>

</html>