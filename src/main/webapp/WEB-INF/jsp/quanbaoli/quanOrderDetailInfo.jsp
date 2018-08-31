<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
%>   
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>付款详情</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/sweetalert/css/sweetalert.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.min.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<style>
	body{ background: #f1f0f6; }
</style>
</head>
<body>
	<header>
		<span>付款详情</span>
		<a href="javascript:history.go(-1);" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="cl"></div>
	<div class="order">
	</div>
	<div class="cl"></div>
	
	<div class="order_list">
		<ul>
			<li>
				<span style="color:#E91E63;font-weight:bold;">基本信息：</span>
				<p id="ddNum3">订单号：</p>
				<span id="createTime3">订单生成时间：</span>
				<p id="pricecpa3">订单状态：</p>
				<span id="cpaCount" style="color:red">请在订单生成后24小时内付款</span>
				<p>打款金额：<b style="color:red" id="mon3"></b></p>
				<span id="syTime"></span>
				<p id="totalPrice"></p>
				<span><b style="color:blue;">打款截图:</b>
					<img id="yfkUrl" alt="" src="">
				</span>
			</li>
		</ul>
		<p style="color:gray;">******</p>
		<ul>
			<li>
				<span style="color:#E91E63;font-weight:bold;">买家信息：</span>
				<p id="mrxm3"></p>
				<span>手机号：<b style="color:red" id="mrsj3"></b></span>
			</li>
		</ul>
		<p style="color:gray;">******</p>
		<ul>
			<li>
				<span style="color:#E91E63;font-weight:bold;">卖家信息：</span>
				<p id="mjxm3"></p>
				<span>手机号：<b  style="color:red" id="mrsjj3"></span>
				<p id="bankAttr"></p>
				<p style="color:#E91E63;font-weight:bold;">银行卡信息</p>
				<p id="bank3"></p>
				<span id="bankCard3"></span>
				<p id="bankDetail3"></p>
				<span style="color:#E91E63;font-weight:bold;">支付宝信息</span>
				<p id="alipayID3">无账号信息</p>
				<span style="color:#E91E63;font-weight:bold;">微信信息</span>
				<p id="weixinID3">无账号信息</p>
	<!-- 				<span style="color:#E91E63;font-weight:bold;">imtoken eth地址</span> -->
	<!-- 				<p id="imtokenID">无</p> -->
			</li>
		</ul>
		<p style="color:gray;">******</p>
		<ul>
			<li>
				<span style="color:#E91E63;font-weight:bold;">操作：</span>
				<p id="yaoqing_url"></p>
				<form name="form0" id="form0"  enctype="multipart/form-data">  
				      <span style="font-size:18px;text-align:center;color:red">点击此处上传打款截图(小于2M)</span>
				      <input type="file" accept="image/*"name="file0" id="file0" placeholder="点击此处上传身份证正面图片(小于2M)"/><br>
				      <img src="" id="img0" style="width:10%;">
			    </form>
				<span></span>
				<span id="modifypassword_btn" style='float:right;color:#fff;background: #E91E63;display: inline-block;width: 20%;height: 30px;text-align: center;line-height: 30px;'>
					<a style="color:#fff;" href="javascript:fk();">我已付款</a>
				</span>
 				<span id="modifypassword_btn2" style='float:right;color:#fff;background: #E91E63;display: inline-block;width: 20%;height: 30px;text-align: center;line-height: 30px;'>
 					<a style="color:#fff;" href="javascript:shouk();">我已收款</a>
 				</span>
			</li>
		</ul>
	</div>
	<div class="space"></div>
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
<script src="<%=path%>/js/personal/quanOrderDetailInfo.js" type="text/javascript" charset="utf-8"></script>
</html>
