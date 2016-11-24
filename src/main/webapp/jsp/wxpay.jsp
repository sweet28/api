<script src="${basePath}/statics/h5/js/jquery.min.js"></script>
<script>
function onBridgeReady() {
	WeixinJSBridge.invoke('getBrandWCPayRequest', {
		"appId"     : "${WCPayRequest.appId}",
		"timeStamp" : "${WCPayRequest.timeStamp}",
		"nonceStr"  : "${WCPayRequest.nonceStr}",
		"package"   : "${WCPayRequest.package}",
		"signType"  : "MD5",
		"paySign"   : "${WCPayRequest.paySign}"
	},function(res) {
		if(res.err_msg == "get_brand_wcpay_request:ok"){
			var payDate = getDate("${WCPayRequest.timeStamp}");
			location.href = "${basePath}/h5/payOK.htm?total_money=${WCPayRequest.total_money}&order_time="+payDate+"&order_id=${WCPayRequest.order_id}&pay_method=wx";
		}else{
			location.href = "${basePath}/h5/payErr.htm?total_money=${WCPayRequest.total_money}&order_id=${WCPayRequest.order_id}";
		}
	});
}
if (typeof WeixinJSBridge == "undefined") {
	if (document.addEventListener) {
		document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	} else if (document.attachEvent) {
		document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
		document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	}
} else {
	onBridgeReady();
}

function getDate(_date){
	var d = new Date();
	d.setTime(_date*1000);
	var year = d.getFullYear();
	var month = d.getMonth()+1;
	var date = d.getDate();

	var hour = d.getHours();
	var minute = d.getMinutes();
	var second = d.getSeconds();
	var newDate = year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
	return newDate;
}
</script>