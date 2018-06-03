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
	  url: getAPIURL() + "user/miner/kuBListKC",
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
        		var syyz;
        		if(runs==0){
        			runs="运行中";
        		}else if(runs==1){
        			runs="死亡";
        		}else if(runs==2){
        			runs="冻结";
        		}
        		
        		if(xh==1){
        			xh="CB1矿池";
        			syyz=5.5;
        		}else if(xh==2){
        			xh="CB2矿池";
        			syyz=55;
        		}else if(xh==3){
        			xh="CB3矿池";
        			syyz=550;
        		}
        		
        		var sl = content.minerComputingPower;
        		
        		var nowDate = new Date();
        		var rundate = nowDate - content.createDate;
        		rundate = rundate/(1000*60*60*24);
        		
        		if(rundate >= 15){
        			rundate = 15;
        		}
        		
        		var sy;
        		sy = rundate * (syyz /15);
        		
        		var sec = localStorage.getItem("sec");
        		var conte = "实名审核后可解冻";
        		if(sec == "1"){
        			conte = "<a href='javascript:jiedong("+content.id+","+content.bak1+");'>转入钱包</a>";
            	}else if(sec == "2"){
            		conte = "认证审核未通过";
            	}
        		
			    html += "<tr><td class='first'>"+(index+1)+"</td><td>"+xh+"</td><td>"+(sy-content.totalRevenue)+"</td><td>"+conte+"</td></tr>";
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


function jiedong(kjid,kjjb){
	console.log("-syjiedongbbbb-");
	
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		layer.open({
	          content: '未认证用户不能收益转账。'
	          , btn: '确定'
	      });
		return false;
	}
	loading.open();
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
	        if (list.length <= 0) {
	        	loading.close();
	        	layer.open({
			          content: '银行卡未绑定不能转账收益。'
			          , btn: '确定'
	  		      });
	  			return false;
	        }else{
	        	loading.open();
	        	var flag = checkLogin();
	        	var tmp = getTimestamp();
	        	var rad = getRandom();
	        	var ton = getTom();
	        	var str = "id="+kjid+"tmp="+tmp+"rad="+rad+"tom="+ton;
	        	$.ajax({
	        	      type: "post",
	        	      url: getAPIURL() + "user/miner/minerjd",
	        	      dataType: "json",
	        	      data: {
	        	    	  "id":kjid,
	        		        "tmp":tmp,
	        		        "rad":rad,
	        		        "tom":ton,
	        		        "token":commingSoon1(str)
	        	      },
	        	      success: function (data) {
	        	        if (data.status==200) {
	        	        	loading.close();
	        	          layer.open({
	        	            content: '转入钱包成功。'
	        	            , btn: ['确定']
	        	            , yes: function (index) {
	        	              window.location.href = "../page/my_kjbc.html";
	        	            }
	        	          });
	        	        } else {
	        	        	loading.close();
	        	        	layer.open({
	        		            content: data.msg
	        		            , btn: ['确定']
	        		            , yes: function (index) {
	        		  	          window.location.href = "../page/my_kjbc.html";
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
		          content: '银行卡或身份证未认证，不能转账交易。'
		          , btn: '确定'
  		      });
  			return false;
	    }
    });
}