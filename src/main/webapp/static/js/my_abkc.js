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
        			conte = "<a href='javascript:zhuanru("+content.id+","+content.minerType+");'>转入运行池</a>";
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
	loading.open();
	$.ajax({
		type: "post",
	      url: getAPIURL() + "kuangjy/jy/shuaxinyxc",
	      dataType: "json",
	      data: {
	    	  "uid":localStorage.getItem("uid")
	      },
	      success: function (data) {
	        if (data.status == 200) {
	        	loading.close();
	        	window.location.href = "../page/my_abkc.html";
	        }else{
	        	loading.close();
	        	layer.open({
	  	          content: data.msg
	  	          , btn: '确定'
	  		      });
	        	return false;
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	loading.close();
	    	layer.open({
		          content: data.msg
		          , btn: '确定'
			      });
			return false;
	    }
	});
}

function zhuanru(kid,type){
	
//	layer.open({
//          content: '排队转入中。'
//          , btn: '确定'
//    });
//	return false;
	loading.open();
	$.ajax({
		type: "post",
	      url: getAPIURL() + "kuangjy/jy/zhuanruyxc",
	      dataType: "json",
	      data: {
	    	  "uid":localStorage.getItem("uid"),
	    	  "type":type,
	    	  "kid":kid
	      },
	      success: function (data) {
	        if (data.status == 200) {
	        	loading.close();
	        	layer.open({
    	            content: '操作成功。'
        	            , btn: ['确定']
        	            , yes: function (index) {
        	            	window.location.href = "../page/my_abkc.html";
        	            }
    	         });
	        }else{
	        	loading.close();
	        	layer.open({
	  	          content: data.msg
	  	          , btn: '确定'
	  		      });
	        	return false;
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	loading.close();
	    	layer.open({
		          content: data.msg
		          , btn: '确定'
			      });
			return false;
	    }
	});
}