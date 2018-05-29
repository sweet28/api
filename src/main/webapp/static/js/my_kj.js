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
		console.log(commingSoon1(str));
    $.ajax({
      type: "post",
      url: getAPIURL() + "user/miner/kuA",
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
        	var html;
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
        			xh="CA1";
        		}else if(xh==2){
        			xh="CA2";
        		}else if(xh==3){
        			xh="CA3";
        		}else if(xh==4){
        			xh="CA4";
        		}
        		
        		var nowDate = Date.parse(new Date());
        		var rundate = nowDate - content.createDate;
        		
        		rundate = rundate/1000/3600/24;
        		if(rundate > 15){
        			rundate=15;
        		}
        		
        		var runHours = rundate*24;
        		
			    html += "<tr><td class='first'>"+(index+1)+"</td><td>"+xh+"</td><td>"+content.minerComputingPower+"</td><td>"+runs+"</td><td>"+runHours+"</td></tr>";
			});
        	
        	$("#a_miner").html(html);
        }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  (function () {
    _$gift = $("#a_miner");
    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});