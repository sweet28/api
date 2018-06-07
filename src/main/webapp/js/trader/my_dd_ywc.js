(function(){
  $(function(){
	  var tmp = getTimestamp();
      var rad = getRandom();
      var ton = getTom();
      var str = "jddi="+localStorage.getItem("uid")+"trddi="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton;
      $.ajax({
    	  type: "post",
	      url: getAPIURL() + "kuangjy/jy/ywclb",
	      dataType: "json",
	      data:{
          	  "jddi":localStorage.getItem("uid"),
          	  "trddi":localStorage.getItem("uid"),
              "tmp":tmp,
              "rad":rad,
              "tom":ton,
              "token":commingSoon1(str)
	      },
        success: function (data) {
        	 var list = data;
          if (list.length <= 0) {
            var txtsNULL ="<p class='nothing'>无更多记录</p>";
            $("#a_miner").html(txtsNULL);
          } else {
        	  var txt1 = '';//,txt2='';
              for (var i = 0; i < list.length; i++) {
              	var cpatype ;
              	var mm;
              	
              	if(list[i].traderType==2){
              		mm="卖";
              	}
              	if(list[i].traderType==1){
              		mm="买"
              	}
              	if(list[i].traderState==1){
              		cpatype = "待付款";
              	}
              	if(list[i].traderState==2){
              		cpatype = "待确认收款";
              	}
              	if(list[i].traderState==3){
              		cpatype = "已完成";
              	}
              	
              	var ahref;
          		ahref = "<a style='font-weight:bold;color: #fff;' href='traderDetail?"+list[i].id+"'>详情</a>";
              	
          		var totalPriceUSA = list[i].traderCount * list[i].entrustPrice;
          		txt1 += "<li>" +
			    			"<p>类型：" + mm + "单</p>" +
				    		"<span>订单号："+ (list[i].orderNumber) +"</span>" +
				    		"<p>数目价格：" + list[i].traderCount + "CPA*" + list[i].entrustPrice + "$=" + totalPriceUSA + "$</p>" +
				    		"<span>总计人民币：" + totalPriceUSA * 6.5 + "元</span>" +
				    		"<span style='float:right;background: #E91E63;display: inline-block;width: 20%;height: 30px;text-align: center;line-height: 30px;'>" + ahref +"</span>" +
			    		"</li>&nbsp;&nbsp;";
              }
              $('#a_miner').html(txt1);
          }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        }
      });
  });
})();