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
//        		console.log(suanli+"============1");
        		if(content.diejia != null){
//        			console.log("****************2********");
        			diejia = content.diejia;
//        			console.log(diejia+"-----3");
        			suanli += Number(diejia);
//        			console.log(suanli+"^^^^^^4^^^^^^^^^^");
        		}
        		
        		syyz += (diejia/content.minerComputingPower)*syyz;
        		
        		var runHours = (rundate*24).toFixed(5);
        		
        		var chanbi = (rundate * ( syyz/15 )).toFixed(5);
        		
        		var kuangchibi = 0;
        		if((chanbi - content.totalRevenue) > 0 ){
        			kuangchibi = (chanbi - content.totalRevenue);
        		}
        		
//        		console.log("chanbi:"+chanbi+"---kuangchibi:"+kuangchibi+"---totalRevenue:"+content.totalRevenue+"" +
//        				"---syyz:"+syyz+"-----runHours:"+runHours+"---rundate:"+rundate+
//        				"---diejia:"+diejia+"----suanli:"+suanli+"----diejia/content.minerComputingPower:"+(diejia/content.minerComputingPower));
        		
        		var sec = localStorage.getItem("sec");
        		var conte = "实名审核后可叠加";
        		var conte2 = "实名审核后可叠加";
        		
        		if(sec == "1"){
        			conte = "<a style='color:#fcbd10;' class='addpcpower' href='javascript:addPower("+content.id+");'>叠加直推算力</a>";
        			conte2 = "<a style='color:#fc105a;' class='addpcpower' href='javascript:addGradePower("+content.id+");'>叠加节点算力</a>";
            	}
        		
        		html += 
        					"<li>" + "<b style='color:blue;'>" + (index+1) + "</b>" +
	        					"<div class='img'>" +
	        						"<img src='"+getAPIURL()+"/imagenew/miner2.gif' style='max-width: 88%;'>" +
								"</div>" +
								"<div class='text'>" +
									"<a href=''>"+ xh + "(" + fmtDate(content.createDate) + ")</a>" +
									"<p>运行时长：<b>"+runHours+"</b></p>" +
									"<p>总算力：<b>"+suanli.toFixed(5)+"</b></p>" +
									"<p>产币总量：<b>" + syyz.toFixed(5) + "</b></p>" +
									"<p>已产币：<b>"+ chanbi +"</b></p>" +
									"<p>已提取：<b>" + content.totalRevenue.toFixed(5) + "</b></p>"+
									"<p>" + conte + "</p>"+
									"<p>" + conte2 + "</p>"
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

function addGradePower(minerId){
	console.log("-----------------");
	$('body').dialogbox({
		type:"normal",title:"叠加节点算力",
		buttons:[{
			Text:"确定",
			ClickToClose:true,
			callback:function (dialog){
				var id1 = $(dialog).find("input[name='companyRdoID']:checked").val();
                returnData = {
                    "ID": id1,
                    "ShowText": $(dialog).find("#companyShowText" + id1).val()
                };
                
                $.ajax({
          	      type: "post",
          	      url: getAPIURL() + "user/miner/kjaddGP",
          	      dataType: "json",
          	      data: {
          	    	  "phone": localStorage.getItem("phone"),
          	    	  "powerType": id1,
          	    	  "uid": localStorage.getItem("uid"),
			    	  "id":minerId
          	      },
          	      success: function (data) {
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
                
                
			}
		}],
		message:"<div id='companySelectFotmTable' style='display:table;width:100%;'>" +
					"<div id='comapnyRowId1' style='display:table-row;'>" +
						"<div style='display:table-cell;'>" +
							"<input type='radio' name='companyRdoID' id='companyRdoID1' value='1' checked='true'>" +
						"</div>" +
						"<div style='display:table-cell;'>" +
							"<label for='companyRdoID1'>普通节点奖励算力</label>" +
						"</div>" +
					"</div>" +
					"<div id='comapnyRowId2' style='display:table-row;'>" +
						"<div style='display:table-cell;'>" +
							"<input type='radio' name='companyRdoID' id='companyRdoID2' value='2'>" +
						"</div>" +
						"<div style='display:table-cell;'>" +
							"<label for='companyRdoID2'>高级节点奖励算力</label>" +
						"</div>" + 
					"</div>" +
					"<div id='comapnyRowId3' style='display:table-row;'>" +
						"<div style='display:table-cell;'>" +
							"<input type='radio' name='companyRdoID' id='companyRdoID3' value='3'>" +
						"</div>" +
						"<div style='display:table-cell;'>" +
							"<label for='companyRdoID3'>超级节点奖励算力</label>" +
						"</div>" +
					"</div>" +
				"</div>"
	});
}

function addPower(minerId){
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