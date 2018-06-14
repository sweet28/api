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
		console.log("------------------------phone:"+localStorage.getItem("phone"));
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
          $("#a_miner").html("<ul><li><p>暂无记录</p></li></ul>");
        } else {
        	var html = "";
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
        			xh="CA1";
        			syyz = 11;
        		}else if(xh==2){
        			xh="CA2";
        			syyz = 115;
        		}else if(xh==3){
        			xh="CA3";
        			syyz = 1150;
        		}else if(xh==4){
        			xh="CA4";
        			syyz = 6000;
        		}
        		
        		var nowDate = Date.parse(new Date());
        		var rundate = nowDate - content.createDate;
        		
        		rundate = rundate/(1000*60*60*24);
        		if(rundate > 15){
        			rundate=15;
        		}
        		
        		var suanli = content.minerComputingPower;
        		var diejia = 0;
        		
        		if(content.diejia != null){
        			diejia = content.diejia;
        			suanli += Number(diejia);
        		}
        		
        		syyz += diejia/content.minerComputingPower*syyz;
        		
        		var runHours = (rundate*24).toFixed(5);
        		
        		var chanbi = (rundate * ( syyz/15 )).toFixed(5);
        		
        		var kuangchibi = 0;
        		if((chanbi - content.totalRevenue) > 0 ){
        			kuangchibi = (chanbi - content.totalRevenue);
        		}
        		
        		console.log("chanbi:"+chanbi+"---kuangchibi:"+kuangchibi+"---totalRevenue:"+content.totalRevenue);
        		
        		var sec = localStorage.getItem("sec");
        		var conte = "实名审核后可叠加";
        		if(sec == "1"){
        			conte = "<a style='color:#fcbd10;' class='addpcpower' href='javascript:addPower("+content.id+");'>点击叠加算力</a>";
            	}
        		
        		html += 
        					"<li>" +
	        					"<div class='img'>" +
	        						"<img src='"+getAPIURL()+"/imagenew/miner2.gif' style='max-width: 88%;'>" +
								"</div>" +
								"<div class='text'>" +
									"<a href=''>"+ xh +"</a>" +
									"<p>运行时长：<b>"+runHours+"</b></p>" +
									"<p>总算力：<b>"+suanli.toFixed(5)+"</b></p>" +
									"<p>产币总量：<b>" + syyz.toFixed(5) + "</b></p>" +
									"<p>已产币：<b>"+ chanbi +"</b></p>" +
									"<p>已提取：<b>" + content.totalRevenue.toFixed(5) + "</b></p>"+
									"<p>" + conte + "</p>"
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

function addPower(minerId){
	console.log("-----------------------"+minerId);
	var uid = localStorage.getItem("uid");
	$(".addpcpower").hide();
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "user/miner/slh",
	      dataType: "json",
	      data: {
	    	  "sh": localStorage.getItem("phone")
	      },
	      success: function (data) {
	    	  if(data.status == 200){
	    		  var suanli = data.data*0.05;
	    		  console.log(suanli+"----------------");
	    		  if(suanli > 0){
	    			  console.log(suanli);
	    			  
	    			  $.ajax({
	    			      type: "post",
	    			      url: getAPIURL() + "user/miner/kjdj",
	    			      dataType: "json",
	    			      data: {
	    			    	  "uid": localStorage.getItem("uid"),
	    			    	  "djsl":suanli,
	    				      "phone":localStorage.getItem("phone"),
	    			    	  "id":minerId
	    			      },
	    			      success: function (data) {
	    			    	  console.log(data);
	    			    	  $(".addpcpower").show();
	    			    	  if(data.status == 200){
	    			    		  swal({
	    			    			  title: "操作成功",
	    			    			  icon: "success",
	    			    			  button: "确定",
	    			    		  });
	    			    	  }else{
	    			    		  swal({
	    			    			  title: data.msg,
	    			    			  icon: "error",
	    			    			  button: "确定",
	    			    		  });
	    			    	  }
	    			    	  
	    			      }, headers: {
	    			        "Authorization": "Bearer " + getTOKEN()
	    			      }
	    			  });
	    		  }else{
	    			  swal({
	    				  title: "直推算力为0，不能叠加",
	    				  icon: "error",
	    				  button: "确定",
	    			});
	    			  $(".addpcpower").show();
	    		  }
	    	  }else{
	    		  swal({
	    			  title: data.msg,
	    			  icon: "error",
	    			  button: "确定",
	    		});
	    	  }
	    	  
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	  });
}