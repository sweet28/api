<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试</title>
<script src="${basePath}/static/js/jquery.min.js"></script>
</head>

<script>
//微信登陆返回
function WXLogin(){
	//alert('${wx_error}');
	if("${userinfo.wx_id}" != ""){
		//正确返回用户id，保存到自己的数据库
		$.post("${basePath}/mobile/customer/customerLogin",{
			"wx_id"         : "${userinfo.wx_id}",
			"nickName"      : "${userinfo.nickName}",
			"sex"           : "${userinfo.sex}",
			"head_portrait" : "${userinfo.head_portrait}",
			"birthday"      : "${userinfo.birthday}"
		},function(data){
			if (data.code == 1) {
				showError('登录失败，请重新登陆');
			} else {
				var expires = new Date();
				expires.setTime(expires.getTime() + 30 * 24 * 60 * 60 * 1000);
				var ck_user = "i_userID=" + data.model.id + ";expires=" + expires.toGMTString() + ";path=/";
				var ck_nick = "i_nickName=" + encodeURIComponent(data.model.nickName) + ";expires=" + expires.toGMTString() + ";path=/";
				document.cookie = ck_user;
				document.cookie = ck_nick;
				window.name = "";
				window.history.go( - 3);
			}
		},"json");
	}
}
$(function() {
	WXLogin();
})

//支付类型默认支付宝
var payType = "zfb";
//下单
function addOrder(){
	//if(userID != "0"){//判断用户是否登陆
	//还有地址是否选择的判断，你们最好把一个订单所必需的的都做好判断，再继续后面操作
		$.post("${basePath}/h5/addOrder",{
			
		},function(data){
			if(data.code == "1"){
				if(payType == "wx"){
					location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3a%2f%2fwww.你们的网址.com%2fh5%2fWXPay.htm&response_type=code&scope=snsapi_base&state="+data.model+"#wechat_redirect";
				}else if(payType == "zfb"){
					$.post("${basePath}/mobile/pay/doPay",{
						"order_id"   : data.model,
						"pay_method" : "zfb",
						"show_url"   : "${basePath}/h5/payErr.htm?total_money=1&order_id="+data.model
					},function(data1){
						if(data1.code == "1"){
							document.write(data1.model);
						}
					},"json");
				}
			}
		},"json");
	//}else{//用户未登录，跳转登陆页面
	//	location.href = "${basePath}/h5/login.htm";
	//}
}
</script>

<body>
<a class="btn chat" href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3a%2f%2fwww.你们的网址.com%2fh5%2fWXLogin.htm&response_type=code&scope=snsapi_userinfo#wechat_redirect"><span>用微信登录</span></a>
<div class="onlinePay">
    <p>在线支付</p>
    <ul class="payType">
    </ul>
</div>
<div class="buybtn">
    <a class="total-price1" href="javascript:addOrder();">确认支付 </a>
</div>
</body>
<script>
//判断是否为微信浏览器
var ua = navigator.userAgent.toLowerCase();
if(ua.match(/MicroMessenger/i)=="micromessenger") {
	payType = "wx";
	$(".payType").html('<li><span class="paystyle">微信支付</span><span class="select"></span></li>');
} else {
	payType = "zfb";
	$(".payType").html('<li><span class="paystyle">支付宝支付</span><span class="select"></span></li>');
}
</script>
</html>