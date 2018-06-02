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
  url: getAPIURL() + "user/miner/kucunABList",
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
        		var xh = content.minerType;
        		
        		if(xh==1){
        			xh="CA矿机";
        		}else if(xh==2){
        			xh="CB矿机";
        		}
        		
        		var sl = content.minerComputingPower;
        		
        		var sec = localStorage.getItem("sec");
        		var conte = "实名审核后可解冻";
        		if(sec == "1"){
        			conte = "<a href='javascript:zhuanru("+content.id+","+content.bak1+");'>转入运行池</a>";
            	}else if(sec == "2"){
            		conte = "认证审核未通过";
            	}
        		
        		
			    html += "<tr><td class='first'>"+(index+1)+"</td><td>"+xh+"</td><td>"+sl+"</td><td>"+conte+"</td></tr>";
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

function kucunSX(){
	console.log("-------------321");
}

function zhuanru(kjid,kjjb){
	
	layer.open({
          content: '等待分配。'
          , btn: '确定'
      });
	return false;
//	$.ajax({
//		type: "post",
//	      url: getAPIURL() + "bank/list",
//	      dataType: "json",
//	      data: {
//	    	  "fensUserId":localStorage.getItem("uid"),
//	    	  "pageSize":100,
//	    	  "pageNum":0
//	      },
//	      success: function (data) {
//	        var list = data.list;;
//	        if (list.length <= 0) {
//		        loading.close();
//	        	console.log("没有账号信息");
//	        	layer.open({
//			          content: '银行卡未绑定不能转账收益。'
//			          , btn: '确定'
//	  		      });
//	  			return false;
//	        }else{
//	        	loading.open();
//	        	var flag = checkLogin();
//	        	var tmp = getTimestamp();
//	        	var rad = getRandom();
//	        	var ton = getTom();
//	        	var str = "id="+kjid+"tmp="+tmp+"rad="+rad+"tom="+ton;
//	        	$.ajax({
//	        	      type: "post",
//	        	      url: getAPIURL() + "user/miner/minerjd",
//	        	      dataType: "json",
//	        	      data: {
//	        	    	  "id":kjid,
//	        		        "tmp":tmp,
//	        		        "rad":rad,
//	        		        "tom":ton,
//	        		        "token":commingSoon1(str)
//	        	      },
//	        	      success: function (data) {
//	        	        if (data.status==200) {
//	        	        	loading.close();
//	        	          layer.open({
//	        	            content: '转入钱包成功。'
//	        	            , btn: ['确定']
//	        	            , yes: function (index) {
//	        	              window.location.href = "../page/index.html";
//	        	            }
//	        	          });
//	        	        } else{
//	        	        	loading.close();
//	        	        	layer.open({
//	        		            content: data.msg//'操作失败，请检查网络服务。'
//	        		            , btn: ['确定']
//	        		            , yes: function (index) {
//	        		  	          window.location.href = "../page/index.html";
//	        		            }
//	        		          });
//	        	        }
//	        	      },
//	        	      headers: {
//	        	        "Authorization": "Bearer " + getTOKEN()
//	        	      }
//	            });
//	        }
//	      },
//	    error: function (XMLHttpRequest, textStatus, errorThrown) {
//	    	console.log("没有账号信息2222");
//        	layer.open({
//		          content: '银行卡或身份证未认证，不能转账交易。'
//		          , btn: '确定'
//  		      });
//  			return false;
//	    }
//    });
}