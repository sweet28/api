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
<title>提交工单</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/css/style.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/sweetalert/sweetalert.js" type="text/javascript" ></script>
</head>
<body>
	<header>
		<span>我的工单</span>
		<a href="javascript:history.go(-1)" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="work_list">
		<a href="<%=path%>/cpa/myWorkOrder" class="on"><span>提交工单</span></a>
		<a href="<%=path%>/cpa/myWorkOrderList"><span>工单列表</span></a>
	</div>
	<div class="cl"></div>
	<form action="">
	<div class="problem">
		<div class="row pro">
			<span>问题分类</span><span>*</span>
		</div>
		<div class="row pcl">
			<select id="typecpa">
			    <option value="mr">交易</option>
			    <option value="mc">矿机</option>
			    <option value="mt">其他</option>
			</select>
			<!-- <span><input type="radio" name="p" id="p1"><label for="p1">购买</label></span>
			<span><input type="radio" name="p" id="p2"><label for="p2">提现</label></span>
			<span><input type="radio" name="p" id="p3"><label for="p3">其他</label></span> -->
		</div>
		<div class="row phash">
			<!-- <span>交易ID</span><span class="hui">(hash)</span><span class="huang">*</span>
			<input type="text" placeholder="请从发币方获取交易ID"> -->
		</div>
	</div>
	<div class="problems">
		<div class="row pro">
			<span>问题描述</span><span>*</span>
		</div>
		<div class="row">
			<textarea name="" id = "conent" placeholder="请具体且准确的描述您的问题，这有助于我们更高效的帮助您！"></textarea>
		</div>
		<!-- <div class="row pros">
			<span>问题截图</span><span>（选填）</span>
		</div> -->
		<form name="form0" id="form0"  enctype="multipart/form-data">  
		      <input type="file" accept="image/*"name="file0" id="file0"/><br>
		      <img src="" id="img0" style="width:10%;">
	    </form>
		<!-- <div class="row files">
			<input type="file" name="pic" id="fpic" accept="image/gif" /><label for="fpic"></label>
		</div> -->
		<div class="row">
			<input type="submit" onclick="sb();" class="msub" value="提交">
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
			<li><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/imagenew/menu3.png" alt=""><p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/imagenew/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/menu4_on.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
<script src="<%=path%>/js/personal/gon_dan.js" type="text/javascript" charset="utf-8"></script>
</html>