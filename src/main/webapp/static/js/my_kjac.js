/**
 * Created by tim on 2016/6/21.
 */
function Gift() {
  var self = this, _$gift;

  function comptime() {
	  var uid = localStorage.getItem("uid");
	  var page = 100;
	  var row = 0;
    $.ajax({
      type: "post",
      url: getAPIURL() + "fenuser/miner/minerAList",
      dataType: "json",
      data: {
    	  "fensUserId":uid,
    	  "page":page,
    	  "row":row
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
        		var syyz;
        		if(runs==0){
        			runs="运行中";
        		}else if(runs==1){
        			runs="死亡";
        		}else if(runs==2){
        			runs="冻结";
        		}
        		
        		if(xh==1){
        			xh="CA1矿池";
        			syyz=10;
        		}else if(xh==2){
        			xh="CA2矿池";
        			syyz=100;
        		}else if(xh==3){
        			xh="CA3矿池";
        			syyz=1000;
        		}else if(xh==4){
        			xh="CA4矿池";
        			syyz=5000;
        		}
        		
        		var sl = content.minerComputingPower;
        		
        		var nowDate = Date.parse(new Date());
        		var rundate = nowDate - content.createDate;
        		rundate = rundate/1000/3600;
        		
        		var sy;
        		sy = rundate * syyz /15/24;
        		
        		var sec = localStorage.getItem("sec");
        		var content = "实名审核后可解冻";
        		if(sec == "1"){
        			content = "审核通过，待晚上交易中心上线后交易";
            	}else if(sec == "2"){
            		content = "认证审核未通过";
            	}
        		
			    html += "<tr><td class='first'>"+(index+1)+"</td><td>"+xh+"</td><td>"+sy+"</td><td>"+content+"</td></tr>";
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