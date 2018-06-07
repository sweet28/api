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
<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<header>
		<span>我的工单</span>
		<a href="#" class="history"><img src="<%=path%>/images/go.png" alt=""></a>
		<a href="#" class="share"><img src="<%=path%>/images/share.png" alt=""></a>
	</header>
	<div class="work_list">
		<a href="" class="on"><span>提交工单</span></a>
		<a href=""><span>工单列表</span></a>
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
			    <option value="mc">其他</option>
			</select>
			<!-- <span><input type="radio" name="p" id="p1"><label for="p1">购买</label></span>
			<span><input type="radio" name="p" id="p2"><label for="p2">提现</label></span>
			<span><input type="radio" name="p" id="p3"><label for="p3">其他</label></span> -->
		</div>
		<div class="row phash">
			<span>交易ID</span><span class="hui">(hash)</span><span class="huang">*</span>
			<input type="text" placeholder="请从发币方获取交易ID">
		</div>
	</div>
	<div class="problems">
		<div class="row pro">
			<span>问题描述</span><span>*</span>
		</div>
		<div class="row">
			<textarea name="" id="" placeholder="请具体且准确的描述您的问题，这有助于我们更高效的帮助您！"></textarea>
		</div>
		<div class="row pros">
			<span>问题截图</span><span>（选填）</span>
		</div>
		<div class="row files">
			<input type="file" name="pic" id="fpic" accept="image/gif" /><label for="fpic"></label>
		</div>
		<div class="row">
			<input type="submit" class="msub" value="提交">
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
			<li class="col-xs-3"><a href="<%=path%>/cpa/minerHouse"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/shangchengweixuanzhong.png" alt=""><p>矿机商城</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/myMiner"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/kuangjiweixuanzhong.png" alt=""><p>我的矿机</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/traderCenter"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/jyweixuanzhong.png" alt=""><p>交易中心</p></a></li>
			<li class="col-xs-3"><a href="<%=path%>/cpa/personal"><img style="width:40%;height:auto;" src="<%=path%>/imagenew/gerenzhongxin.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
</html>