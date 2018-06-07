/**
 * Created by tim on 2016/6/21.
 */
function Gift() {
  var self = this, _$gift;

  function comptime() {
	  var uid = localStorage.getItem("uid");
	  var page = 100;
	  var row = 0;
	  var flag = checkLogin();
		var tmp = getTimestamp();
		var rad = getRandom();
		var ton = getTom();
		var str = "uid="+uid+"pg="+page+"ts="+row+"tmp="+tmp+"rad="+rad+"tom="+ton;
  $.ajax({
    type: "post",
    url: getAPIURL() + "user/miner/kuB",
    dataType: "json",
    data: {
  	  "uid":uid,
  	  "pg":page,
  	  "ts":row,
	      "rad":rad,
	      "tom":ton,
	      "token":commingSoon1(str)
      },
      success: function (data) {
        var list = data.list;
        if (list.length <= 0) {
          $("#a_miner").html("<ul><li class='nothing'><p>暂无记录</p></li></ul>");
        } else {
        	var html = "";
        	$.each( list, function(index, content){
        		var runs = content.bak2;
        		var xh = content.bak1;
        		if(runs==0){
        			runs="运行中";
        		}else if(runs==1){
        			runs="死亡";
        		}else if(runs==2){
        			runs="冻结";
        		}
        		
        		if(xh==1){
        			xh="CB1";
        		}else if(xh==2){
        			xh="CB2";
        		}else if(xh==3){
        			xh="CB3";
        		}
        		
        		var nowDate = Date.parse(new Date());
        		var rundate = nowDate - content.createDate;
        		
        		rundate = rundate/1000/3600/24;
        		if(rundate > 15){
        			rundate=15;
        		}
        		
        		var runHours = rundate*24;
        		
        		var chanbi = content.minerComputingPower * runHours/360;
        		
        		html += 
					"<li>" +
    					"<div class='img'>" +
    						"<img src='"+getAPIURL()+"/imagenew/miner1.gif' style='max-width: 88%;'>" +
						"</div>" +
						"<div class='text'>" +
							"<a href=''>"+ xh +"</a>" +
							"<p>运行时长：<b>"+runHours+"</b></p>" +
							"<p>算力：<b>"+content.minerComputingPower+"</b></p>" +
							"<p>产币：<b>"+ chanbi +"</b></p>" +
							"<p>已入矿池：<b>" + (chanbi - content.totalRevenue) + "</b></p>" +
							"<p>已入钱包：<b>" + content.totalRevenue + "</b></p>"
						"</div>" +
						"<div class='look'>" +
							"<a href='#'>"+runs+"</a>" +
						"</div>" +
					"</li>";
        		
        	});
        	
        	$("#a_miner").html(html);
        }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
  
  setInterval(comptime,5000);

  (function () {
    _$gift = $("#a_miner");
    $("#uname").html("欢迎，"+localStorage.getItem("name"));
    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});