<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=path%>/cssnew/style.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/sweetalert/css/sweetalert.css">
<link rel="stylesheet" href="<%=path%>/lib/swiper4.3.3/css/swiper.min.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/lib/barrager/css/barrager.css">

<script src="<%=path%>/lib/js/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery.base64.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/lib/js/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/sweetalert/sweetalert.min.js"></script>
<script src="<%=path%>/lib/swiper4.3.3/js/swiper.min.js"></script>
<script type="text/javascript" src="<%=path%>/lib/barrager/js/jquery.barrager.min.js"></script>
<script src="<%=path%>/js/wapframwork.js" type="text/javascript" charset="utf-8"></script>
<style>
    .swiper-container {
      width: 100%;
      height: 100%;
    }
    .swiper-slide {
      text-align: center;
      font-size: 18px;
      background: #fff;

      /* Center slide text vertically */
      display: -webkit-box;
      display: -ms-flexbox;
      display: -webkit-flex;
      display: flex;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      -webkit-justify-content: center;
      justify-content: center;
      -webkit-box-align: center;
      -ms-flex-align: center;
      -webkit-align-items: center;
      align-items: center;
    }
  </style>
</head>
<body>
	<header>
		<%-- <img src="<%=path%>/imagenew/header.jpg" alt="">
		<div class="searchBox">
			<div class="search">
				<!-- <div class="sea">
					<input type="submit" class="index_btn" value="">
					<input type="text" class="index_txt" placeholder="搜索">
				</div> -->
			</div>
		</div> --%>
		<!-- Swiper -->
		  <div class="swiper-container">
		    <div class="swiper-wrapper">
		      <div class="swiper-slide"><img src="<%=path%>/imagenew/header.jpg" alt=""></div>
		      <div class="swiper-slide"><img alt="" src="http://www.artforyou.cn/images/cpa_may.jpg"></div>
		      <div class="swiper-slide"><img alt="" src="http://www.artforyou.cn/images/login_bg2.png"></div>
		    </div>
		    <!-- Add Pagination -->
		    <div class="swiper-pagination"></div>
		  </div>
	</header>
	<!-- Initialize Swiper -->
	  <script>
	    var swiper = new Swiper('.swiper-container', {
	    	autoplay:true,
		    pagination: {
		      el: '.swiper-pagination',
		      //nextEl: '.swiper-button-next',
		      //prevEl: '.swiper-button-prev',
		    },
	    });
	  </script>
	<div class="line1"></div>
	<nav>
		<ul>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/n1.jpg" alt=""><p>个人中心</p></a></li>
			<li><a href="<%=path%>/cpa/news"><img src="<%=path%>/imagenew/n2.jpg" alt=""><p>新闻公告</p></a></li>
			<li><a href="<%=path%>/cpa/knowledge"><img src="<%=path%>/imagenew/n3.jpg" alt=""><p>资料库</p></a></li>
			<li><a href="<%=path%>/cpa/myFens"><img src="<%=path%>/imagenew/n4.jpg" alt=""><p>粉丝团</p></a></li>
			<li><a href="<%=path%>/cpa/myEarnings"><img src="<%=path%>/imagenew/n5.jpg" alt=""><p>收益</p></a></li>
			<li><a href="<%=path%>/cpa/quanbl"><img src="<%=path%>/imagenew/n6.jpg" alt=""><p>券保理</p></a></li>
			<%-- <li><a href="#"><img src="<%=path%>/imagenew/n7.jpg" alt=""><p>奖励名单</p></a></li> --%>
			<li><a href="<%=path%>/cpa/myWorkOrder"><img src="<%=path%>/imagenew/n8.jpg" alt=""><p>工单反馈</p></a></li>
		</ul>
	</nav>
	<div class="cl"></div>
	<div class="news">
		<ul>
			<li><a href="">尊敬的会员，感恩有你，</a></li>
			<li><a href="">car.π 2.0全新出发...</a></li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="coupon">
		<ul>
			<li>
				<div class="row">
					<div class="img">
						<img src="<%=path%>/imagenew/pic1.png" alt="">
						<p>总数:<span style="color:#ff5722;font-weight:bold;">9999</span></p>
					</div>
					<div class="txt">
						<div class="title">
							<a href="">券保理1星券(7天)</a>
							<span style="font-size:16px;color:red">天使一期</span>
						</div>
						<div class="desc">
							<p>锁仓周期：<span style="color:#ff5722;font-weight:bold;">7天</span></p>
							<p>预计收益率：<span style="color:#ff5722;font-weight:bold;">10%</span></p>
						</div>
					</div>
					<div class="price">
						<p style="color:#ff5722;font-weight:bold;">火热发布</p>
						<a id="hid1" href="javascript:tangkuan(1);">标价(￥)：200</a>
					</div>
				</div>
				<div class="row ping">
					<span></span>
					<span>持有两台CA1矿机可购买一张</span>
<!-- 					<span>稳步收益</span> -->
					<span>剩余数量：<b id="yxing" style="color:#ff5722;"></b>张</span>
				</div>
			</li>
			<li>
				<div class="row">
					<div class="img">
						<img src="<%=path%>/imagenew/pic1.png" alt="">
						<p>总数:<span style="color:#ff5722;font-weight:bold;">6666</span></p>
					</div>
					<div class="txt">
						<div class="title">
							<a href="">券保理1星券(21天)</a>
							<span style="font-size:16px;color:red">天使一期</span>
						</div>
						<div class="desc">
							<p>锁仓周期：<span style="color:#ff5722;font-weight:bold;">21天</span></p>
							<p>预计收益率：<span style="color:#ff5722;font-weight:bold;">40%</span></p>
						</div>
					</div>
					<div class="price">
						<p style="color:#ff5722;font-weight:bold;">火热发布</p>
						<a id="hid2" href="javascript:tangkuan(2);">标价(￥)：200</a>
					</div>
				</div>
				<div class="row ping">
					<span></span>
					<span>持有两台CA1矿机可购买一张</span>
					<span>剩余数量：<b id="exing" style="color:#ff5722;"></b>张</span>
				</div>
			</li>
			<li>
				<div class="row">
					<div class="img">
						<img src="<%=path%>/imagenew/pic1.png" alt="">
						<p>总数:<span style="color:#ff5722;font-weight:bold;">3333</span></p>
					</div>
					<div class="txt">
						<div class="title">
							<a href="">券保理2星券(15天)</a>
							<span style="font-size:16px;color:red">天使一期</span>
						</div>
						<div class="desc">
							<p>锁仓周期：<span style="color:#ff5722;font-weight:bold;">15天</span></p>
							<p>预计收益率：<span style="color:#ff5722;font-weight:bold;">25%</span></p>
						</div>
					</div>
					<div class="price">
						<p style="color:#ff5722;font-weight:bold;">火热发布</p>
						<a id="hid3" href="javascript:tangkuan(3);">标价(￥)：500</a>
					</div>
				</div>
				<div class="row ping">
					<span></span>
					<span>持有一台CA2矿机可购买一张</span>
					<span>剩余数量：<b id="sxing" style="color:#ff5722;"></b>张</span>
				</div>
			</li>
			
			<li>
				<div class="row">
					<div class="img">
						<img src="<%=path%>/imagenew/pic1.png" alt="">
						<p>总数:<span style="color:#ff5722;font-weight:bold;">1111</span></p>
					</div>
					<div class="txt">
						<div class="title">
							<a href="">券保理3星券(10天)</a>
							<span style="font-size:16px;color:red">天使一期</span>
						</div>
						<div class="desc">
							<p>锁仓周期：<span style="color:#ff5722;font-weight:bold;">10天</span></p>
							<p>预计收益率：<span style="color:#ff5722;font-weight:bold;">30%</span></p>
						</div>
					</div>
					<div class="price">
						<p style="color:#ff5722;font-weight:bold;">火热发布</p>
						<a id="hid4" href="javascript:tangkuan(4);">标价(￥)：1000</a>
					</div>
				</div>
				<div class="row ping">
					<span></span>
					<span>持有一台CA3矿机可购买一张</span>
					<span>剩余数量：<b id="ssxing" style="color:#ff5722;"></b>张</span>
				</div>
			</li>
		</ul>
	</div>
	
	<div class="space"></div>
	<div class="menu">
		<ul>
			<li><a href="<%=path%>/cpa/main"><img src="<%=path%>/imagenew/menu5_on.png" alt=""><p>首页</p></a></li>
			<li><a href="<%=path%>/cpa/minerHouse"><img src="<%=path%>/imagenew/menu1.png" alt=""><p>矿机商城</p></a></li>
			<li><a href="<%=path%>/cpa/traderCenter"><img src="<%=path%>/imagenew/menu3.png" alt=""><p>交易中心</p></a></li>
			<li><a href="<%=path%>/cpa/myMiner"><img src="<%=path%>/imagenew/menu2.png" alt=""><p>我的矿机</p></a></li>
			<li><a href="<%=path%>/cpa/personal"><img src="<%=path%>/imagenew/menu4.png" alt=""><p>个人中心</p></a></li>
		</ul>
	</div>
</body>
<script src="<%=path%>/js/main.js" type="text/javascript" charset="utf-8"></script>
</html>