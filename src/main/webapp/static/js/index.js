(function () {
	
//	先判断是否登录
//  var uid = getUIDByJWT().unique_name;
//  if (uid == undefined) {
//    //return false;
//	return true;
//  }
	
	var flag = checkLogin();
	var tmp = getTimestamp();
	var rad = getRandom();
	var ton = getTom();
	var str = "uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton;
	console.log(str);
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "user/qb/list",
	    dataType: "json",
	    data: {
	    	"uid":localStorage.getItem("uid"),
	        "tmp":tmp,
	        "rad":rad,
	        "tom":ton,
	        "token":commingSoon1(str)
	    },
	    success: function (data) {
	    	var dd = data.data;

	    	if(data.status==200){
	    		//可用余额
	    	      var balance = dd.ableCpa;
	    	      $("#ablecpa").text(balance);
	    	      //待收总额
	    	      var waitNum = dd.lockCpa;
	    	      $("#lockcpa").text(waitNum);
	    	      //累计收益
	    	      var returnIn = dd.lockCpa + dd.ableCpa;
	    	      $("#totalcpa").text(returnIn);
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	      $("#balance_num").text(0);
	      $("#balance_dec").text(".00000");
	      $("#waitNum_num").text(0);
	      $("#waitNum_dec").text(".00000");
	      $("#returnIn_num").text(0);
	      $("#returnIn_dec").text(".00000");

	    }
	  });

  function getCookie(c_name) {
    if (document.cookie.length > 0) {
      var c_start = document.cookie.indexOf(c_name + "=");
      if (c_start != -1) {
        c_start = c_start + c_name.length + 1;
        var c_end = document.cookie.indexOf(";", c_start);
        if (c_end == -1) c_end = document.cookie.length;
        return unescape(document.cookie.substring(c_start, c_end))
      }
    }
    return ""
  }


  /*画布画圆*/
  function draw(arrItem, can) {
    var cxt = can.getContext('2d');
    /*毛边*/
    var width = can.width, height = can.height;
    if (window.devicePixelRatio) {
      can.style.width = width + "px";
      can.style.height = height + "px";
      can.height = height * window.devicePixelRatio;
      can.width = width * window.devicePixelRatio;
      cxt.scale(window.devicePixelRatio, window.devicePixelRatio);
    }

    //底层
    cxt.beginPath();
    cxt.moveTo(85, 85);
    cxt.arc(85, 85, 85, 0, Math.PI * 2);
    cxt.fillStyle = '#e4e2e2';
    cxt.fill();
    cxt.closePath();

    /*开始位置圆角*/
    cxt.beginPath();
    cxt.moveTo(85, 85);
    cxt.arc(85, 3, 3, 0, Math.PI * 2, false);
    cxt.fillStyle = '#f16e6e';
    cxt.fill();
    cxt.closePath();

    /*进度*/
    var progress = arrItem.Progress;
    var starProgress = 0;

    //利率
    var rate = arrItem.Rate;
    var starRate = 0;

    //进度层
    var progressTimer = setInterval(function () {
      if (starProgress >= progress && starRate >= rate) {
        clearInterval(progressTimer);
      }
      drawDetails(cxt, arrItem, starProgress, starRate);
      starProgress++;
      starRate++;
    }, 50);

  }

  /*进度条详情*/
  function drawDetails(cxt, arrItem, starProgress, starRate) {
    if (starRate >= arrItem.Rate) {
      starRate = regexNum(arrItem.Rate.toString())
    }
    if (starProgress >= arrItem.Progress) {
      starProgress = arrItem.Progress
    }
    cxt.beginPath();
    cxt.moveTo(85, 85);
    cxt.arc(85, 85, 85, -Math.PI * 2 * 0.25, -Math.PI * 2 * 0.25 + Math.PI * 2 * starProgress / 100, false);
    cxt.fillStyle = '#f16e6e';
    cxt.fill();
    cxt.closePath();
    //顶层补色
    cxt.beginPath();
    cxt.moveTo(85, 85);
    cxt.arc(85, 85, 80, 0, Math.PI * 2);
    cxt.fillStyle = "#fff";
    cxt.fill();
    cxt.closePath();

    //文字
    cxt.font = 'normal 18px 微软雅黑';
    cxt.textAlign = 'center';
    cxt.fillStyle = '#a09f9f';
    cxt.textBaseline = 'middle';
    cxt.moveTo(85, 85);
    cxt.fillText(arrItem.Prefecture, 85, 40);

    cxt.font = 'normal 14px 微软雅黑';
    cxt.textAlign = 'center';
    cxt.fillStyle = '#b9b9b9';
    cxt.textBaseline = 'middle';
    cxt.moveTo(85, 85);
    cxt.fillText(arrItem.Title, 85, 63);

    cxt.font = 'bold 30px Arial';
    cxt.textAlign = 'center';
    cxt.fillStyle = '#eb6332';
    cxt.textBaseline = 'middle';
    cxt.moveTo(85, 85);
    cxt.fillText(starRate, 80, 90);

    cxt.font = 'bold 18px Arial';
    cxt.textAlign = 'center';
    cxt.fillStyle = '#eb6332';
    cxt.textBaseline = 'middle';
    cxt.fillText('%', 125, 90);
    cxt.moveTo(85, 85);
    /*if (arrItem.Rate.toString().indexOf(".") != -1) {
     cxt.fillText('%', 125, 90);
     } else {
     cxt.fillText('.00%', 120, 90);
     }*/

    cxt.font = 'bold 14px Arial';
    cxt.textAlign = 'center';
    cxt.fillStyle = '#eb6332';
    cxt.textBaseline = 'middle';
    cxt.moveTo(85, 85);
    cxt.fillText('预期年化率', 85, 115);
  }

  /*圆圈定位*/
  function positionCircle(cxt, progress) {
    //算出结束弧度在画布所在的位置
    /*step1:声明变量报存 坐标点*/
    var x = 75, y = 0;
    if (progress / 100 <= 0.25) {
      x = 75 - Math.sin((0.25 - 0.25 - progress / 100) * Math.PI * 2) * 75;
      y = 75 - Math.cos((0.25 - 0.25 - progress / 100) * Math.PI * 2) * 75;
    } else if (progress / 100 > 0.25 && progress / 100 <= 0.5) {
      x = Math.cos((progress / 100 - 0.25) * Math.PI * 2) * 75 + 75;
      y = Math.sin((progress / 100 - 0.25) * Math.PI * 2) * 75 + 75;
    } else if (progress / 100 > 0.5 && progress / 100 <= 0.75) {
      x = 75 - Math.cos((1 - 0.25 - progress / 100) * Math.PI * 2) * 75;
      y = 75 + (Math.sin((1 - 0.25 - progress / 100) * Math.PI * 2) * 75);
    } else {
      x = 75 - Math.sin((1 - progress / 100) * Math.PI * 2) * 75;
      y = 75 - Math.cos((1 - progress / 100) * Math.PI * 2) * 75;
    }
    var img = new Image();
    img.width = 15;
    img.height = 15;
    img.src = "../img/circle.png";
    cxt.drawImage(img, x, y, 15, 15);
  }

  /*小数补位*/
  function regexNum(str) {
    var regex = /(\d)(?=(\d\d\d)+(?!\d))/g;
    if (str.indexOf(".") == -1) {
      str = str.replace(regex, ',') + '.00';
    } else {
      var newStr = str.split('.');
      var str_2 = newStr[0].replace(regex, ',');
      if (newStr[1].length <= 1) {
        //小数点后只有一位时
        str_2 = str_2 + '.' + newStr[1] + '0';
      } else if (newStr[1].length > 1) {
        //小数点后两位以上时
        var decimals = newStr[1].substr(0, 2);
        var srt_3 = str_2 + '.' + decimals;
      }
    }
    return str
  }

  function slide() {
    var autoLb = true;          //autoLb=true为开启自动轮播
    var autoLbtime = 2;         //autoLbtime为轮播间隔时间（单位秒）
    var touch = true;           //touch=true为开启触摸滑动
    var slideBt = true;         //slideBt=true为开启滚动按钮
    var slideNub;               //轮播图片数量
    //窗口大小改变时改变轮播图宽高
    $(window).resize(function () {
      $(".slide").height($(".slide").width() * 0.56);
    });
    $(function () {
      $(".slide").height($(".slide").width() * 0.56);
      slideNub = $(".slide .img").size();             //获取轮播图片数量
      for (i = 0; i < slideNub; i++) {
        $(".slide .img:eq(" + i + ")").attr("data-slide-imgId", i);
      }

      //根据轮播图片数量设定图片位置对应的class
      if (slideNub == 1) {
        for (i = 0; i < slideNub; i++) {
          $(".slide .img:eq(" + i + ")").addClass("img3");
        }
      }
      if (slideNub == 2) {
        for (i = 0; i < slideNub; i++) {
          $(".slide .img:eq(" + i + ")").addClass("img" + (i + 3));
        }
      }
      if (slideNub == 3) {
        for (i = 0; i < slideNub; i++) {
          $(".slide .img:eq(" + i + ")").addClass("img" + (i + 2));
        }
      }
      if (slideNub > 3 && slideNub < 6) {
        for (i = 0; i < slideNub; i++) {
          $(".slide .img:eq(" + i + ")").addClass("img" + (i + 1));
        }
      }
      if (slideNub >= 6) {
        for (i = 0; i < slideNub; i++) {
          if (i < 5) {
            $(".slide .img:eq(" + i + ")").addClass("img" + (i + 1));
          } else {
            $(".slide .img:eq(" + i + ")").addClass("img5");
          }
        }
      }


      //根据轮播图片数量设定轮播图按钮数量
      if (slideBt) {
        for (i = 1; i <= slideNub; i++) {
          $(".slide-bt").append("<span data-slide-bt='" + i + "' onclick='tz(" + i + ")'></span>");
        }
        $(".slide-bt").width(slideNub * 34);
        $(".slide-bt").css("margin-left", "-" + slideNub * 17 + "px");
      }


      //自动轮播
      if (autoLb) {
        setInterval(function () {
          right();
        }, autoLbtime * 1000);
      }


      if (touch) {
        k_touch();
      }
      slideLi();
      imgClickFy();
    })


    //右滑动
    function right() {
      var fy = new Array();
      for (i = 0; i < slideNub; i++) {
        fy[i] = $(".slide .img[data-slide-imgId=" + i + "]").attr("class");
      }
      for (i = 0; i < slideNub; i++) {
        if (i == 0) {
          $(".slide .img[data-slide-imgId=" + i + "]").attr("class", fy[slideNub - 1]);
        } else {
          $(".slide .img[data-slide-imgId=" + i + "]").attr("class", fy[i - 1]);
        }
      }
      imgClickFy();
      slideLi();
    }


    //左滑动
    function left() {
      var fy = new Array();
      for (i = 0; i < slideNub; i++) {
        fy[i] = $(".slide .img[data-slide-imgId=" + i + "]").attr("class");
      }
      for (i = 0; i < slideNub; i++) {
        if (i == (slideNub - 1)) {
          $(".slide .img[data-slide-imgId=" + i + "]").attr("class", fy[0]);
        } else {
          $(".slide .img[data-slide-imgId=" + i + "]").attr("class", fy[i + 1]);
        }
      }
      imgClickFy();
      slideLi();
    }


    //轮播图片左右图片点击翻页
    function imgClickFy() {
      $(".slide .img").removeAttr("onclick");
      $(".slide .img2").attr("onclick", "left()");
      $(".slide .img4").attr("onclick", "right()");
    }


    //修改当前最中间图片对应按钮选中状态
    function slideLi() {
      var slideList = parseInt($(".slide .img3").attr("data-slide-imgId")) + 1;
      $(".slide-bt span").removeClass("on");
      $(".slide-bt span[data-slide-bt=" + slideList + "]").addClass("on");
    }


    //轮播按钮点击翻页
    function tz(id) {
      var tzcs = id - (parseInt($(".slide .img3").attr("data-slide-imgId")) + 1);
      if (tzcs > 0) {
        for (i = 0; i < tzcs; i++) {
          setTimeout(function () {
            right();
          }, 1);
        }
      }
      if (tzcs < 0) {
        tzcs = (-tzcs);
        for (i = 0; i < tzcs; i++) {
          setTimeout(function () {
            left();
          }, 1);
        }
      }
      slideLi();
    }


    //触摸滑动模块
    function k_touch() {
      var _start = 0, _end = 0, _content = document.getElementById("slide");
      _content.addEventListener("touchstart", touchStart, false);
      _content.addEventListener("touchmove", touchMove, false);
      _content.addEventListener("touchend", touchEnd, false);
      function touchStart(event) {
        var touch = event.targetTouches[0];
        _start = touch.pageX;
      }

      function touchMove(event) {
        var touch = event.targetTouches[0];
        _end = (_start - touch.pageX);
      }

      function touchEnd(event) {
        if (_end < -100) {
          left();
          _end = 0;
        } else if (_end > 100) {
          right();
          _end = 0;
        }
      }
    }
  }

  $("#myfens").on("click",function(){
	  window.location.href = '../page/my_fens.html';
  })
  /*悬浮按钮*/
  $("#goAppBtn").on("click", function () {
    var ua = navigator.userAgent;
    var isiOS = ua.match(/iPhone|iPod|iPad/i);
    var isAndroid = ua.match(/Android/i);

    if (isiOS) {
      window.location = 'https://itunes.apple.com/cn/app/id1238020212?mt=8';
    } else if (isAndroid) {
      window.location = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.p2p.jojojr';
    }
  });
})();
function isLogin() {
  var value = "";
  var str = window.navigator.userAgent;
  //如果是移动设备
  if (str.indexOf("jojojr") != -1) {
    /*是安卓*/
    var search = window.location.search;
    // alert("search"+search)
    if (search.indexOf("?") != -1) {
      var msg = search.slice(1);
      if (msg.indexOf("&") != -1) {
        var msgArr = msg.split("&");
        for (var i = 0; i < msgArr.length; i++) {
          var item1 = msgArr[i].split("=");
          if (item1[0] == "token") {
            value = item1[1];
          }
        }
      }
      else {
        var item2 = msg.split("=");
        if (item2[0] == "token") {
          value = item2[1];
        }
      }
    }
    if (value == null
      || value == ""
      || value == undefined) {
      return false;
    }
    else {
      var aftervalue = value.split(".");
      return $.parseJSON($.base64.atob(aftervalue[1], true));
    }
  }
  else {
    value = localStorage.getItem("token");
    if (value == null) {
      return false;
    }
    else {
      var aftervalue = value.split(".");
      return $.parseJSON($.base64.atob(aftervalue[1], true));
    }
  }
}

$("#cpamai").click(function (){
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		layer.open({
	          content: '未认证用户不能交易。'
	          , btn: '确定'
	      });
		return false;
	}
	
	$.ajax({
		type: "post",
	      url: getAPIURL() + "bank/list",
	      dataType: "json",
	      data: {
	    	  "fensUserId":localStorage.getItem("uid"),
	    	  "pageSize":100,
	    	  "pageNum":0
	      },
	      success: function (data) {
	        var list = data.list;
	        console.log(list.length+"-------------dddd");
	        if (list.length <= 0) {
	        	console.log("没有账号信息");
	        	layer.open({
			          content: '银行卡未绑定不能交易挂单。'
			          , btn: '确定'
	  		      });
	  			return false;
	        }else{
	        	var type = $("#typecpa").val();


	        	//交易状态   0代表挂单中；1代表成交
	        	var trader_state = 0;
	        	//交易类型   1代表买方  2代表卖方
	        	var trader_type = 0;
	        	//交易人id
	        	var trader_id = localStorage.getItem("uid");
	        	//交易CPA数
	        	var trader_count = $("#cpanum").val();
	        	//委托价格(美元)
	        	var entrust_price = $("#cpadj").val();
	        	
	        	if(type == "mc"){
	        		trader_type = 2;
	        	}
	        	
	        	
	        	if(type == "mr"){
	        		trader_type = 1;
	        	}
	        	
	        	if(trader_count==null || trader_count==""){
	        		layer.open({
	        	          content: '请输入交易数量。'
	        	          , btn: '确定'
	        	      });
	        		return false;
	        	}
	        	if(trader_count<1){
	        		layer.open({
	        	          content: '交易数量需要大于1。'
	        	          , btn: '确定'
	        	      });
	        		return false;
	        	}
	        	
	        	if(trader_count > 100){
	        		layer.open({
	        	          content: '交易数量不能大于100。'
	        	          , btn: '确定'
	        	      });
	        		return false;
	        	}
	        	
	        	if(trader_type == 2){
	        		console.log("----type::::"+trader_type);
	        		
	        		var flag = 0;
	        		$.ajax({
	        		    type: "post",
	        		    url: getAPIURL() + "wallet/list",
	        		    dataType: "json",
	        		    data: {
	        		    	"fensUserId":localStorage.getItem("uid")
	        		    },
	        		    success: function (data) {
	        		    	var dd = data.data;
	        		    	if(data.status==200){
	        		    		//可用余额
	        		    		var yue = dd.ableCpa;
	        		    		if(yue < 1 ){
	        		    			layer.open({
	        		    		          content: '账户钱包CPA余额不足。'
	        		    		          , btn: '确定'
	        		    		      });
	        		    			return false;
	        		    		}
	        		    		if(trader_count > yue){
	        		    			layer.open({
	        		    		          content: '账户钱包可用CPA不足。'
	        		    		          , btn: '确定'
	        		    		      });
	        		    			return false;
	        		    		}
	        		    		flag = 1;
	        		    	}else{
	        		    		  return false;
	        		    	}
	        		    },
	        		    error: function (XMLHttpRequest, textStatus, errorThrown) {
	        		    	layer.open({
	          		          content: '账户钱包CPA余额不足，。'
	          		          , btn: '确定'
	        	  		      });
	        		    	flag = 0;
	        	  			return false;
	        		    }
	        	    });
	        	}
	        	
	        	if(entrust_price==null || entrust_price==""){
	        		layer.open({
	        	          content: '请输入交易单价。'
	        	          , btn: '确定'
	        	      });
	        		return false;
	        	}
	        	
	        	$.ajax({
	        	      type: "post",
	        	      url: getAPIURL() + "miner/record/addRecord",
	        	      dataType: "json",
	        	      data:{
	        	    	  "traderId":parseInt(localStorage.getItem("uid")),
	        	    	  "traderType":parseInt(trader_type),
	        	    	  "traderState":parseInt(trader_state),
	        	    	  "entrustPrice":entrust_price,
	        	    	  "traderCount":trader_count
	        	      },
	        	      success: function (data) {
	        	        if (data.status==200) {
	        	        	if(type == "mc"){
	        	        		trader_type = 2;
	        	        		layer.open({
	    	        	            content: '挂单成功,待审核通过后同步至交易中心。'
	    		        	            , btn: ['确定']
	    		        	            , yes: function (index) {
	    		        	              window.location.href = "../page/index.html";
	    		        	            }
    		        	         });
	        	        	}
	        	        	
	        	        	
	        	        	if(type == "mr"){
	        	        		trader_type = 1;
	        	        		layer.open({
	    	        	            content: '挂单成功。'
	    	        	            , btn: ['确定']
	    	        	            , yes: function (index) {
	    	        	              window.location.href = "../page/index.html";
	    	        	            }
	    	        	          });
	        	        	}
	        	          
	        	        } else {
	        	        	layer.open({
	        		            content: data.msg
	        		            , btn: ['确定']
	        		            , yes: function (index) {
	        		  	          window.location.href = "../page/index.html";
	        		            }
	        		          });
	        	        }
	        	      },
	        	      headers: {
	        	        "Authorization": "Bearer " + getTOKEN()
	        	      }
	            });
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	console.log("没有账号信息2222");
        	layer.open({
		          content: '银行卡未绑定帮能交易挂单。'
		          , btn: '确定'
  		      });
  			return false;
	    }
    });
	
});