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
  url: getAPIURL() + "user/miner/kuAListKC",
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
        	var html="";
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
        			syyz=11;
        		}else if(xh==2){
        			xh="CA2矿池";
        			syyz=115;
        		}else if(xh==3){
        			xh="CA3矿池";
        			syyz=1150;
        		}else if(xh==4){
        			xh="CA4矿池";
        			syyz=6000;
        		}
        		
        		var sl = content.minerComputingPower;
        		
        		var nowDate = new Date();
        		var rundate = nowDate - content.createDate;
        		rundate = rundate/(1000*60*60*24);
        		
        		if(rundate >= 15){
        			rundate = 15;
        		}
        		
        		var suanli = content.minerComputingPower;
        		var diejia = 0;
        		
        		if(content.diejia != null){
        			diejia = content.diejia;
        			suanli += Number(diejia);
        		}
        		
        		var sy;
        		sy = rundate * (syyz/15) + (diejia/content.minerComputingPower) * (syyz/15);
        		
        		var runHours = rundate*24;
        		
        		var sec = localStorage.getItem("sec");
        		var conte = "实名审核后可解冻";
        		if(sec == "1"){
        			conte = "<a class='zrbutton' href='javascript:jiedong("+content.id+","+content.bak1+");'>转入钱包</a>";
            	}else if(sec == "2"){
            		conte = "认证审核未通过";
            	}
        		
        		var syz = (sy-content.totalRevenue);
        		if(syz < 0){
        			syz = 0 ;
        		}
        		
        		
        		html += "<ul>" + (index+1) +
							"<li>" +
								"<div class='img'>" +
									"<img src='"+getAPIURL()+"/imagenew/miner7.gif' style='max-width: 90%;'>" +
								"</div>" +
								"<div class='text'>" +
									"<a href=''>"+ xh +"</a>" +
									"<p>运行时长：<b>"+runHours.toFixed(5)+"</b></p>" +
									"<p>可用收益：<b>"+syz.toFixed(5)+"</b></p>" +
									"<p>算力：<b>"+suanli+"</b></p>" +
								"</div>" +
								"<div class='look'>" +
									conte +
								"</div>" +
							"</li>" +
						"</ul>";
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


function jiedong(kjid,kjjb){
	$(".zrbutton").hide();
	
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		swal({
    		  title: "未认证用户不能收益转账。",
    		  icon: "error",
    		  button: "确定",
    	});
		$(".zrbutton").show();
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
	        var list = data.list;;
	        if (list.length <= 0) {
	        	swal({
		      		  title: "银行卡未绑定不能转账收益。",
		      		  icon: "error",
		      		  button: "确定",
		      	});
	        	$(".zrbutton").show();
	  			return false;
	        }else{
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
	        	        	swal({
		        	          	  title: '转入钱包成功。',
		        	          	  type: 'success',
		        	          	  showCancelButton: true,
		        	          	  confirmButtonText: "确定", 
		        	          	  cancelButtonText: "取消",
		        	          	  closeOnConfirm: true, 
		        	          	  closeOnCancel: true
		        	          	}).then(function(isConfirm) {
		        	          	  if (isConfirm === true) {
		        	          		  window.location.href = "myMinerKC";
		        	          	  } else if (isConfirm === false) {
		        	          	   
		        	          	  } else {
		        	          	    // Esc, close button or outside click
		        	          	    // isConfirm is undefined
		        	          	  }
	        	          	});
	        	        	$(".zrbutton").show();
	        	        } else{
	        	        	swal({
		        	          	  title: data.msg,
		        	          	  type: 'error',
		        	          	  showCancelButton: true,
		        	          	  confirmButtonText: "确定", 
		        	          	  cancelButtonText: "取消",
		        	          	  closeOnConfirm: true, 
		        	          	  closeOnCancel: true
		        	          	}).then(function(isConfirm) {
		        	          	  if (isConfirm === true) {
		        	          		  window.location.href = "myMinerKC";
		        	          	  } else if (isConfirm === false) {
		        	          		  window.location.href = "myMinerKC";
		        	          	  } else {
		        	          		  window.location.href = "myMinerKC";
		        	          	  }
	        	          	});
	        	        	$(".zrbutton").show();
	        	        }
	        	      },
	        	      headers: {
	        	        "Authorization": "Bearer " + getTOKEN()
	        	      }
	            });
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	swal({
	      		  title: "银行卡或身份证未认证，不能转账交易。",
	      		  icon: "error",
	      		  button: "确定",
	      	});
	    	$(".zrbutton").show();
  			return false;
	    }
    });
}


function jiedongAll(){
	$(".zrbutton").hide();
	
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		swal({
    		  title: "未认证用户不能收益转账。",
    		  icon: "error",
    		  button: "确定",
    	});
		$(".zrbutton").show();
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
	        var list = data.list;;
	        if (list.length <= 0) {
	        	swal({
		      		  title: "银行卡未绑定不能转账收益。",
		      		  icon: "error",
		      		  button: "确定",
		      	});
	        	$(".zrbutton").show();
	  			return false;
	        }else{
	        	var flag = checkLogin();
	        	var tmp = getTimestamp();
	        	var rad = getRandom();
	        	var ton = getTom();
	        	var str = "uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton;
	        	$.ajax({
	        	      type: "post",
	        	      url: getAPIURL() + "user/miner/minerjd2",
	        	      dataType: "json",
	        	      data: {
	        	    	  "uid":localStorage.getItem("uid"),
	        		        "tmp":tmp,
	        		        "rad":rad,
	        		        "tom":ton,
	        		        "token":commingSoon1(str)
	        	      },
	        	      success: function (data) {
	        	        if (data.status==200) {
	        	        	swal({
		        	          	  title: '转入钱包成功。',
		        	          	  type: 'success',
		        	          	  showCancelButton: true,
		        	          	  confirmButtonText: "确定", 
		        	          	  cancelButtonText: "取消",
		        	          	  closeOnConfirm: true, 
		        	          	  closeOnCancel: true
		        	          	}).then(function(isConfirm) {
		        	          	  if (isConfirm === true) {
		        	          		  window.location.href = "myMinerKC";
		        	          	  } else if (isConfirm === false) {
		        	          	   
		        	          	  } else {
		        	          	    // Esc, close button or outside click
		        	          	    // isConfirm is undefined
		        	          	  }
	        	          	});
	        	        	$(".zrbutton").show();
	        	        } else{
	        	        	swal({
		        	          	  title: data.msg,
		        	          	  type: 'error',
		        	          	  showCancelButton: true,
		        	          	  confirmButtonText: "确定", 
		        	          	  cancelButtonText: "取消",
		        	          	  closeOnConfirm: true, 
		        	          	  closeOnCancel: true
		        	          	}).then(function(isConfirm) {
		        	          	  if (isConfirm === true) {
		        	          		  window.location.href = "myMinerKC";
		        	          	  } else if (isConfirm === false) {
		        	          		  window.location.href = "myMinerKC";
		        	          	  } else {
		        	          		  window.location.href = "myMinerKC";
		        	          	  }
	        	          	});
	        	        	$(".zrbutton").show();
	        	        }
	        	      },
	        	      headers: {
	        	        "Authorization": "Bearer " + getTOKEN()
	        	      }
	            });
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	swal({
	      		  title: "银行卡或身份证未认证，不能转账交易。",
	      		  icon: "error",
	      		  button: "确定",
	      	});
	    	$(".zrbutton").show();
  			return false;
	    }
    });
}