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
<title>工单反馈详情</title>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript"
	charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js"
	type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript"
	charset="utf-8"></script>
<%-- <script src="<%=path%>/js/personal/gon_dan_list.js" type="text/javascript" charset="utf-8"></script> --%>
<style type=text/css>
.chat {
	width: 500px;
	height: 500px;
	margin: 0 auto;
	padding: 20px 20px;
	background: #cdb79e;
}

.chat1 {
	border: 0px solid #303030;
	border-radius: 10px; /*向div元素添加圆角边框，数值定义圆角的形状*/
	box-sizing: border-box; /*允许以特定的方式定义匹配某个区域的特定元素*/
	float: left;
	clear: both;
	margin-bottom: 9px;
	padding: 4px 22px;
	background: #fff8dc;
	position: relative;
	width: auto;
}

.chat2 {
	background: #aeeeee;
	float: right;
}
</style>
</head>
<body>
	<header> <span>工单反馈详情</span> <a
		href="javascript:history.go(-1)" class="history"><img
		src="<%=path%>/images/go.png" alt=""></a> <a href="#" class="share"><img
		src="<%=path%>/images/share.png" alt=""></a> </header>
	<div class="cl"></div>
	<div class="wlist">
		<ul>
			<li id="liebiao" style="margin-top: 6px;">
				<div class="title">
					<span>交易类型</span id="leixing"> <span>：交易&nbsp;&nbsp;&nbsp;</span><span>单号</span>
					<span id="danhao">：88</span>
				</div>
				<div class="desc">
					<span>问题描述</span> <span>*</span> <span style="margin-left: 26%;">时间：</span>
					<span id="time">2019-06-26</span>
				</div>
				<div class="text">
					<textarea name="" id="conent" readonly="readonly"
						placeholder="请具体且准确的描述您的问题，这有助于我们更高效的帮助您！">交易拥堵</textarea>
				</div>
			</li>
		</ul>
	</div>

	<div class="wlist">
		<ul>
			<li id="liebiao2" style="margin-top: 6px;">
				<div class="desc">
					<span>反馈详情描述：</span>
				</div>
				<div class="gonggao" style="height: 225px;">
					<div id="lie1">
					  <div>
<%-- 					    <img  style="float: right;width:30px;height:30px;" src="<%=path%>/image/yonghu.png"/> --%>
						<div class='chat1 chat2' style="float:right;width:160px;" >
							1111111111111111
						</div>
					 </div><br />
					 
					 <div>
<%-- 						<img  style="float: right;width:30px;height:30px;" src="<%=path%>/image/yonghu.png"/> --%>
						<div class='chat1 chat2' style="float:right;width:160px;" >
							222222222222
						</div>
					</div><br />
						
					<div>
<%-- 						<img  style="float: right;width:30px;height:30px;" src="<%=path%>/image/yonghu.png"/> --%>
						<div class='chat1 chat2' style="float:right;width:160px;" >
							33333333333333
						</div>
					</div><br />
						
					<div>
<%-- 						<img  style="float: right;width:30px;height:30px;" src="<%=path%>/image/yonghu.png"/> --%>
						<div class='chat1 chat2' style="float:right;width:160px;" >
							44444444444444
						</div>
					</div>
<%-- 				     	<img  style="float: right;width:30px;height:30px;" src="<%=path%>/image/yonghu.png"/> --%>
<!-- 						<div class='chat1 chat2' style="float:right;width:160px;margin-right: 13%;margin-top: -9%;" > -->
<!-- 							1111111111111111 -->
<!-- 						</div> -->
<!-- 					    <br /> -->
<!-- 						<div class='chat1' style="float:left;width:160px;margin-left: 10%;"> -->
<!-- 							1111111111111111 -->
<!-- 						</div>  -->
<%-- 						<img style="float:left;width:30px;height:30px;margin-left:-59%;" src="<%=path%>/image/kefu.png"/> --%>
<!-- 						<br /> -->
<!-- 						<div class='chat1 chat2' style="float: right" style="width:100px;margin-right: 10%;"> -->
<!-- 							1111111111111111 &nbsp;&nbsp;<img -->
<%-- 								src="<%=path%>/image/yonghu.png" --%>
<!-- 								style="width: 30px; height: 30px;" /> -->
<!-- 						</div> -->
<!-- 						<br /> <br /> -->
<!-- 						<div class='chat1' style="float: left" style="width:100px;margin-right: 10%;"> -->
<%-- 							<img src="<%=path%>/image/kefu.png" --%>
<!-- 								style="width: 30px; height: 30px;" />&nbsp;&nbsp;1111111111111111 -->
<!-- 						</div> -->
<!-- 						<br /> -->
<!-- 						<div class='chat1 chat2' style="float: right" style="width:100px;margin-right: 10%;"> -->
<!-- 							1111111111111111 &nbsp;&nbsp;<img -->
<%-- 								src="<%=path%>/image/yonghu.png" --%>
<!-- 								style="width: 30px; height: 30px;" /> -->
<!-- 						</div> -->
<!-- 						<br /> <br /> -->
<!-- 						<div class='chat1' style="float: left" style="width:100px;margin-right: 10%;"> -->
<%-- 							<img src="<%=path%>/image/kefu.png" --%>
<!-- 								style="width: 30px; height: 30px;" />&nbsp;&nbsp;1111111111111111 -->
<!-- 						</div> -->
<!-- 						<br /> -->
<!-- 						<div class='chat1 chat2' style="float: right" style="width:100px;margin-right: 10%;"> -->
<!-- 							1111111111111111 &nbsp;&nbsp;<img -->
<%-- 								src="<%=path%>/image/yonghu.png" --%>
<!-- 								style="width: 30px; height: 30px;" /> -->
<!-- 						</div> -->
<!-- 						<br /> <br /> -->
<!-- 						<div class='chat1' style="float: left" style="width:100px;margin-right: 10%;"> -->
<%-- 							<img src="<%=path%>/image/kefu.png" --%>
<!-- 								style="width: 30px; height: 30px;" />&nbsp;&nbsp;1111111111111111 -->
<!-- 						</div> -->
<!-- 						<br /> -->
					</div>
				</div>
				<div class="gonggao" style="height: 50px;">
					<textarea name="" id="jianyi" style="width: 100%;;margin-right: 10%;"
						placeholder="请具体且准确的描述您的问题，这有助于我们更高效的帮助您！"></textarea>
				</div>
				<div style="text-align: center; margin-top: 7px;">
					<input type="button" id="register_btn" value="提交"
						onclick="tijiao();" class="login"
						style="width: 100%; height: 32px;" />
				</div>
			</li>
		</ul>
	</div>

	<!-- 	<div class="line"></div>
	<div class="space"></div>
 -->
	<div class="menu">
		<ul>
			<li><a href="<%=path%>/cpa/main"><img
					src="<%=path%>/imagenew/menu5.png" alt="">
					<p>首页</p></a></li>
			<li><a href="<%=path%>/cpa/minerHouse"><img
					src="<%=path%>/imagenew/menu1.png" alt="">
					<p>矿机商城</p></a></li>
			<li><a href="<%=path%>/cpa/traderCenter"><img
					src="<%=path%>/imagenew/menu3.png" alt="">
					<p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img
					src="<%=path%>/imagenew/menu2.png" alt="">
					<p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img
					src="<%=path%>/imagenew/menu4_on.png" alt="">
					<p>个人中心</p></a></li>
		</ul>
	</div>
</body>
<script src="<%=path%>/js/personal/fang_kui.js" type="text/javascript"
	charset="utf-8"></script>
</html>