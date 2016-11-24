<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
<script src="${basePath}/static/js/jquery.min.js"></script>
<title>支付失败</title>
</head>
<script>
function pay(){
	$.post("${basePath}/mobile/pay/doPay",{
		"order_id"   : "${order.order_id}",
		"pay_method" : "zfb",
		"show_url"   : "${basePath}/h5/payErr.htm?total_money=${order.total_money}&order_id=${order.order_id}"
	},function(data1){
		if(data1.code == "1"){
			document.write(data1.model);
		}
	},"json");
}
</script>
<body>
<div class="main bg1">
    <div class="header"><span class="fail">支付失败 ：（</span></div>
    <div class="box mt10 bg2">
        <ul class="ul01">
            <li class="top mt8">
                <span class="fl">付款金额</span>
                <span class="fr">¥${order.total_money}</span>
            </li>
            <li class="bor pb15">
            	<span class="fl">交易单号</span>
            	<span class="fr">${order.order_id}</span>
            </li>
        </ul>
    </div>
    <div id="WXpay" style="display:none">
	    <div class="box wx bg2">
	        <span class="fl wx">微信支付</span>
	        <span class="succ fr"></span>
	    </div>
	    <div class="box sub">
	        <a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3a%2f%2fwww.你们的网址.com%2fh5%2fWXPay.htm&response_type=code&scope=snsapi_base&state=${order.order_id}#wechat_redirect">重新支付</a>
	    </div>
    </div>
    <div id="Alipay" style="display:none">
	    <div class="box wx bg2">
	        <span class="fl zfb">支付宝支付</span>
	        <span class="succ fr"></span>
	    </div>
	    <div class="box sub">
	        <a href="javascript:pay();">重新支付</a>
	    </div>
    </div>
</div>
</body>
<script>
//判断是否为微信浏览器
var ua = navigator.userAgent.toLowerCase();
if(ua.match(/MicroMessenger/i)=="micromessenger") {
	document.getElementById("WXpay").style.display="block";
} else {
	document.getElementById("Alipay").style.display="block";
}
</script>
</html>