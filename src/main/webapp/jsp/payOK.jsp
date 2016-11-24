<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
    <title>支付成功</title>
</head>
<body>
<div class="main">
    <div class="header bg1"><span class="succ">您已支付成功，我们会尽快安排发货：）</span></div>
    <div class="box mt10">
        <ul class="ul01">
            <li class="top bor">
                <span class="fl">付款金额</span>
                <span class="fr">¥${order.total_money}</span>
            </li>
            <li class="mt8"><span class="fl">成交时间</span><span class="fr">${order.order_time}</span></li>
            <li><span class="fl">支付方式</span>
            	<span class="fr">
				<#if order.pay_method == 'wx'>微信</#if>
				<#if order.pay_method == 'zfb'>支付宝</#if>
				</span>
			</li>
            <li class="pb15 bor"><span class="fl">交易单号</span><span class="fr">${order.order_id}</span></li>
        </ul>
    </div>
</div>
</body>
</html>