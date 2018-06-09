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
<title>交易中心</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header>
		<span>出售CPA</span>
		<a href="<%=path%>/cpa/traderCenter" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="work_list">
		<!-- <a href="" class="on"><span>提交工单</span></a>
		<a href=""><span>工单列表</span></a> -->
	</div>
	<div class="cl"></div>
	<form action="">
	<div class="problem">
		<div class="row phash">
			<span>单价（$）</span><span class="hui"></span><span class="huang">*</span>
			<input type="text" placeholder="" id="cpaprice" disabled="disabled">
		</div>
		<div class="row phash">
			<span>CPA数量</span><span class="hui"></span><span class="huang">*</span>
			<input type="text" placeholder="" id="cpa_count" disabled="disabled">
		</div>
		<div class="row phash">
			<span>交易密码</span><span class="hui"></span><span class="huang">*</span>
			<input type="text" placeholder="请输入交易密码" id="jypwd" placeholder="请输入交易密码">
		</div>
	</div>
	<div class="problems">
		<!-- <div class="row pros">
			<span>上传身份证</span><span class="huang">*</span>
		</div>
		<div class="row files">
			<input type="file" name="pic" id="fpic" accept="image/gif" /><label for="fpic"></label>
		</div> -->
		<div class="row">
			<input type="submit" class="msub" value="出售" onclick="cpaNextChuShou();">
		</div>
	</div>
	</form>
	<script type="text/javascript">
	$(function() {
    	$('.pcl label').click(function(){
		    var radioId = $(this).attr('name');
		    $('.input1 label').removeAttr('class') && $(this).attr('class', 'checked');
		    $('input[type="checkbox"]').removeAttr('checked') && $('#' + radioId).attr('checked', 'checked');
		});
	});
	</script>
	<div class="line"></div>
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li><a href="<%=path%>/cpa/main"><img src="<%=path%>/imagenew/menu5.png" alt=""><p>首页</p></a></li>
			<li><a href="<%=path%>/cpa/minerHouse"><img src="<%=path%>/imagenew/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/imagenew/menu3_on.png" alt=""><p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/imagenew/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
<script src="<%=path%>/js/trader/my_invest.js" type="text/javascript" charset="utf-8"></script>
</html>