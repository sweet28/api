(function(){
	
  $(function(){
      var _type = 1;    
      $.ajax({
	      type: "post",
	      url: getAPIURL() + "miner/record/recordlist",
	      dataType: "json",
	      data:{
	    	  "traderState": 0,
          	  "traderType": _type,
          	  "page": 0,
          	  "row": 1,
          	  "traderId": localStorage.getItem("uid")
	      },
	      success: function (data) {
              var list = data.list;
              if (list.length <= 0) {
                var txtsNULL ="<p class='nothing'>无出售订单</p>";
                $('#gift').html(txtsNULL);
              } 
              else {
                var txt1='';
                for (var i = 0; i < list.length; i++) {
                	var cpatype ;
                  	var mm;
                  	
                  	if(list[i].traderType==2){
                  		cpatype = "售卖中";
                  		mm="卖";
                  	}
                  	if(list[i].traderType==1){
                  		cpatype = "买入中";
                  		mm="买"
                  	}
                  	
                  	var ahref = "";
                  	if(_type==1){
                  		//ahref = "<a href='javascript:csCPA("+list[i].id+","+list[i].traderCount+");'>出售</a>";
                  		ahref = "<a href='javascript:csCPA("+list[i].id+","+list[i].traderCount+");'>出售</a>";
                  	}
                  	if(_type==2){
                  		//ahref = "<a href='javascript:mrCPA("+list[i].id+","+list[i].traderCount+");'>买入</a>";
                  		ahref = "<a href='javascript:mrCPA("+list[i].id+","+list[i].traderCount+");'>买入</a>";
                  	}
                  	if(_type==9){
                  		ahref = "<a href='javascript:cxCPA("+list[i].id+");'>撤销</a>";
                  	}
                  	
                  	var totalPrice = "";
                  	totalPrice = list[i].traderCount * list[i].entrustPrice;
//                  	console.log("---------uid:"+list[i].traderId+"----"+list[i]);
                  	
                  	var id = list[i].id;
                  	var tcount = list[i].traderCount;
                  	var tprice = list[i].entrustPrice;
                  	var uname;
//                  	getUname(list[i].traderId,uname);
//                  	console.log("uname::"+uname);
                  	
                  	$.ajax({
          		      type: "post",
          		      url: getAPIURL() + "fenuser/info",
          		      dataType: "json",
          		      data: {
          	    	  "id":list[i].traderId
          		      },
          		      success: function (data2) {
          		    	  console.log(data2);
          		    	  uname = data2.name;
          		          console.log(uname);
          		        txt1 += "<li>" +
		    			"<span class='us1 on'>"+mm+id+"</span>" +
		    			"<span class='us2'>"+tcount+"</span>" +
		    			"<span class='us3'>"+tprice+"</span>" +
		    			"<span class='us4' style='width:15%;word-break:normal;display:block;white-space:pre-wrap;overflow:hidden;color:#0066CC;'>"+uname+"</span>" +
		    			"<span class='us5'>"+ ahref +"</span>" +
		    		"</li>";
          	          },
          	          error: function(XMLHttpRequest, textStatus, errorThrown){
          	          }
          		      ,headers: {
          		        "Authorization": "Bearer " + getTOKEN()
          		      }
          	      });
                  	
//                  	txt1 += "<li>" +
//		    			"<span class='us1 on'>"+mm+id+"</span>" +
//		    			"<span class='us2'>"+tcount+"</span>" +
//		    			"<span class='us3'>"+tprice+"</span>" +
//		    			"<span class='us4' style='width:15%;word-break:normal;display:block;white-space:pre-wrap;overflow:hidden;color:#0066CC;'>"+uname+"</span>" +
//		    			"<span class='us5'>"+ ahref +"</span>" +
//		    		"</li>";
                }
                $('#gift').html(txt1);
              }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            }
	      ,headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
      });
   });
  
  function getUname(uid,name){
	  console.log(uid);
	  $.ajax({
		      type: "post",
		      url: getAPIURL() + "fenuser/info",
		      dataType: "json",
		      data: {
	    	  "id":uid
		      },
		      success: function (data2) {
		    	  console.log(data2);
		          name = data2.name;
		          console.log(name);
	          },
	          error: function(XMLHttpRequest, textStatus, errorThrown){
	          }
		      ,headers: {
		        "Authorization": "Bearer " + getTOKEN()
		      }
	      });
  }
  
  $(function(){
	  $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/jyl",
	      dataType: "json",
	      data: {},
	      success: function (data) {
	    	  console.log(data);
	    	  if(data.status==200){
	    		  if(data.data!=null){
	    			  $("#jye").html(data.data);
	    		  }else{
	    			  $("#jye").html(0);
	    		  }
	    	  }else{
	    		  $("#jye").html(0);
	    	  }
			  
	      },error:function(){
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	    });
  });
  
  
})();


function searchTrader(type){
	var phoneValue = $("#searchPhone").val();
	
	if(phoneValue==null || phoneValue==""){
		swal({
	  		  title: "请输入查询账号。",
	  		  icon: "error",
	  		  button: "确定",
	  	});
		return false;
	}else{
		$.ajax({
		      type: "post",
		      url: getAPIURL() + "kuangjy/jy/phone",
		      dataType: "json",
		      data:{
		    	  "sh": phoneValue,
	        	  "tp": type
		      },
		      success: function (data) {
		    	  console.log(data);
		    	  if(data.status==200){
		    		  var list = data.data;
			            if (list.length <= 0) {
			              var txtsNULL ="<p class='nothing'>无查询的订单</p>";
			              $('#gift').html(txtsNULL);
			            } 
			            else {
			              var txt1='';
			              for (var i = 0; i < list.length; i++) {
			              	var cpatype ;
			                	var mm;
			                	
			                	if(list[i].traderType==2){
			                		cpatype = "售卖中";
			                		mm="卖";
			                	}
			                	if(list[i].traderType==1){
			                		cpatype = "买入中";
			                		mm="买"
			                	}
			                	
			                	var ahref = "";
			                	if(type==1){
			                		//ahref = "<a href='javascript:csCPA("+list[i].id+","+list[i].traderCount+");'>出售</a>";
			                		ahref = "<a href='javascript:csCPA("+list[i].id+","+list[i].traderCount+");'>出售</a>";
			                	}
			                	if(type==2){
			                		//ahref = "<a href='javascript:mrCPA("+list[i].id+","+list[i].traderCount+");'>买入</a>";
			                		ahref = "<a href='javascript:mrCPA("+list[i].id+","+list[i].traderCount+");'>买入</a>";
			                	}
			                	
			                	var totalPrice = "";
			                	totalPrice = list[i].traderCount * list[i].entrustPrice;
			                	
			    			    txt1 += "<li>" +
			    			    			"<span class='us1 on'>"+mm+(list[i].id)+"</span>" +
			    			    			"<span class='us2'>"+list[i].traderCount+"</span>" +
			    			    			"<span class='us3'>"+list[i].entrustPrice+"</span>" +
			    			    			"<span class='us4' style='width:15%;word-break:normal;display:block;white-space:pre-wrap;overflow:hidden;color:#0066CC;'>"+totalPrice+"</span>" +
			    			    			"<span class='us5'>"+ ahref +"</span>" +
						    			"</li>";
			                  
			              }
			              $('#gift').html(txt1);
			            }
		    	  }else{
		    		  var txtsNULL ="<p class='nothing'>无查询的订单</p>";
		              $('#gift').html(txtsNULL);
		    	  }
	          },
	          error: function(XMLHttpRequest, textStatus, errorThrown){
	            //var txtsNULL ="<p class='nothing'>暂无记录</p>";
	            //_$indexlist.append(txtsNULL);
	            //alert('出错了！');
	          }
		      ,headers: {
		        "Authorization": "Bearer " + getTOKEN()
		      }
	    });
	}
}

function buyCPA(trader_type){
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		swal({
	  		  title: "未认证用户不能收益转账。",
	  		  icon: "error",
	  		  button: "确定",
	  	});
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
	        var list = data.list;
	        if (list.length <= 0) {
	        	swal({
		      		  title: "银行卡未绑定不能转账收益。",
		      		  icon: "error",
		      		  button: "确定",
		      	});
	  			return false;
	        }else{
	        	var type = $("#typecpa").val();


	        	//交易状态   0代表挂单中；1代表成交
	        	var trader_state = 0;
	        	//交易类型   1代表买  2代表卖
	        	//交易人id
	        	var trader_id = localStorage.getItem("uid");
	        	//交易CPA数
	        	var trader_count = $("#cpanum").val();
	        	//委托价格(美元)
	        	var entrust_price = $("#cpadj").val();
	        	
	        	if(trader_count==null || trader_count==""){
	        		swal({
			      		  title: "请输入交易数量。",
			      		  icon: "error",
			      		  button: "确定",
			      	});
	        		return false;
	        	}
	        	if(trader_count<10){
	        		swal({
			      		  title: "交易数量不能小于10。",
			      		  icon: "error",
			      		  button: "确定",
			      	});
	        		return false;
	        	}
	        	
	        	if(trader_count > 500){
	        		swal({
			      		  title: "交易数量不能大于500。",
			      		  icon: "error",
			      		  button: "确定",
			      	});
	        		return false;
	        	}
	        	
	        	if(entrust_price==null || entrust_price==""){
	        		swal({
			      		  title: "请输入交易单价。",
			      		  icon: "error",
			      		  button: "确定",
			      	});
	        		return false;
	        	}
	        	
	        	console.log("-------------------mm:"+trader_type);
	        	
	      	    var flag = checkLogin();
	      	    var tmp = getTimestamp();
	      	    var rad = getRandom();
	      	    var ton = getTom();
	            var str = "trddi="+localStorage.getItem("uid")+"mmtype="+parseInt(trader_type)+"jgwt="+entrust_price+"jysl="+trader_count+"tmp="+tmp+"rad="+rad+"tom="+ton; 
	        	
	            $.ajax({
	        	      type: "post",
	        	      url: getAPIURL() + "kuangjy/jy/zjjl",
	        	      dataType: "json",
	        	      data:{
	        	    	  "trddi":parseInt(localStorage.getItem("uid")),
	        	    	  "mmtype":parseInt(trader_type),
	        	    	  "jgwt":entrust_price,
	        	    	  "jysl":trader_count,
	      		    	  "tmp":tmp,
	    		          "rad":rad,
	    		          "tom":ton,
	    		          "token":commingSoon1(str)
	        	      },
	        	      success: function (data) {
	        	        if (data.status==200) {
	        	        	
	        	        	if(trader_type == 2){
	        	        		swal({
			        	          	  title: '挂单成功,待审核通过后同步至交易中心。',
			        	          	  type: 'success',
			        	          	  showCancelButton: true,
			        	          	  confirmButtonText: "确定", 
			        	          	  cancelButtonText: "取消",
			        	          	  closeOnConfirm: true, 
			        	          	  closeOnCancel: true
		        	        		},
		        	        		function(isConfirm){ 
		        	        		  if (isConfirm) { 
		        	        		    //swal("删除！", "你的虚拟文件已经被删除。","success"); 
		        	        		    window.location.href = "traderCenter";
		        	        		  } else { 
		        	        		    //swal("取消！", "你的虚拟文件是安全的:)","error"); 
		        	        		    window.location.href = "traderCenter";
		        	        		  } 
	        	        		});
	        	        	}
	        	        	
	        	        	if(trader_type == 1){
	        	        		swal({
			        	          	  title: '挂单成功。',
			        	          	  type: 'success',
			        	          	  showCancelButton: true,
			        	          	  confirmButtonText: "确定", 
			        	          	  cancelButtonText: "取消",
			        	          	  closeOnConfirm: true, 
			        	          	  closeOnCancel: true
		        	        		},
		        	        		function(isConfirm){ 
		        	        		  if (isConfirm) { 
		        	        		    //swal("删除！", "你的虚拟文件已经被删除。","success"); 
		        	        		    window.location.href = "traderCenter";
		        	        		  } else { 
		        	        		    //swal("取消！", "你的虚拟文件是安全的:)","error"); 
		        	        		    window.location.href = "traderCenter";
		        	        		  } 
	        	        		});
	        	        	}
	        	          
	        	        } else {
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
		        	          		  window.location.href = "traderCenter";
		        	          	  } else if (isConfirm === false) {
		        	          		  window.location.href = "traderCenter";
		        	          	  } else {
		        	          		  window.location.href = "traderCenter";
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
        	swal({
	      		  title: "身份不合法，不能挂单交易。",
	      		  icon: "error",
	      		  button: "确定",
	      	});
  			return false;
	    }
    });
	
}

function csCPA(id,count){
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		swal({
    		  title: "未认证用户不能交易。",
    		  icon: "error",
    		  button: "确定",
    	});
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
	        var list = data.list;
	        if (list.length <= 0) {
	        	console.log("没有账号信息");
	        	swal({
		      		  title: "银行卡未绑定不能交易挂单。",
		      		  icon: "error",
		      		  button: "确定",
		      	});
	  			return false;
	        }else{
	        	
	        	$.ajax({
	        	    type: "post",
	        	    url: getAPIURL() + "wallet/list",
	        	    dataType: "json",
	        	    data: {
	        	    	"fensUserId":localStorage.getItem("uid")
	        	    },
	        	    success: function (data) {
	        	    	var dd = data.data;
	        	    	if(data.status==200){
	        	    		//可用余额
	        	    		var yue = dd.ableCpa;
	        	    		if(yue >= count*1.2){
	        	    			
	        	    			swal({   
	        	    				title: "请输入交易密码",   
	        	    				type: "input",   
	        	    				showCancelButton: true,   
	        	    				closeOnConfirm: false,   
	        	    				animation: "slide-from-top",   
	        	    				inputPlaceholder: "请输入交易密码！" 
	        	    			}, function(inputValue){   
	        	    				if (inputValue === false) return false;      
	        	    				if (inputValue === "") {     
	        	    					swal.showInputError("请输入!");     
	        	    					return false   
	        	    				}
	        	    				
	        	    				var tmp = getTimestamp();
	        	    			    var rad = getRandom();
	        	    			    var ton = getTom();
	        	    			    var str = "id="+id+"uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton;
	        	    			    
	        	    			    
	        	    			    $.ajax({
	        	    				      type: "post",
	        	    				      url: getAPIURL() + "user/fens/jiaoyan",
	        	    				      dataType: "json",
	        	    				      data: {
	        	    				    	  "uid": localStorage.getItem("uid"),
	        	    				    	  "old_jymm":inputValue
	        	    				      },
	        	    				      success: function (data) {
	        	    						  
	        	    						  if(data.status == 200){
	        	    							  $.ajax({
	        	        	    				      type: "post",
	        	        	    				      url: getAPIURL() + "kuangjy/jy/buyDanJieDan",
	        	        	    				      dataType: "json",
	        	        	    				      data:{
	        	        	    				    	  "id":id,
	        	        	    				    	  "uid":localStorage.getItem("uid"),
	        	        	    				    	  "tmp":tmp,
	        	        	    				          "rad":rad,
	        	        	    				          "tom":ton,
	        	        	    				          "token":commingSoon1(str)
	        	        	    				      },
	        	        	    				      success: function (data) {
	        	        	    				    	  console.log("--------------111---------"+data);
	        	        	    				        if (data.status==200) {
	        	        	    				        	swal({
	        	        	    					          	  title: '交易提交成功，待系统扫描CPA资产合法性通过后，进行交易。',
	        	        	    					          	  type: 'success',
	        	        	    					          	  showCancelButton: true,
	        	        	    					          	  confirmButtonText: "确定", 
	        	        	    					          	  cancelButtonText: "取消",
	        	        	    					          	  closeOnConfirm: true, 
	        	        	    					          	  closeOnCancel: true
	        	        	    					          	},function(isConfirm) {
	        	        	    					          	  if (isConfirm === true) {
	        	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenter";
	        	        	    					          	  } else if (isConfirm === false) {
	        	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenter";
	        	        	    					          	  } else {
	        	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenter";
	        	        	    					          	  }
	        	        	    				          	});
	        	        	    				        } else {
	        	        	    				        	swal({
	        	        	    					          	  title: data.msg,
	        	        	    					          	  type: 'error',
	        	        	    					          	  showCancelButton: true,
	        	        	    					          	  confirmButtonText: "确定", 
	        	        	    					          	  cancelButtonText: "取消",
	        	        	    					          	  closeOnConfirm: true, 
	        	        	    					          	  closeOnCancel: true
	        	        	    					          	},function(isConfirm) {
	        	        	    					          	  if (isConfirm === true) {
	        	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenter";
	        	        	    					          	  } else if (isConfirm === false) {
	        	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenter";
	        	        	    					          	  } else {
	        	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenter";
	        	        	    					          	  }
	        	        	    				          	});
	        	        	    				        }
	        	        	    				      }
	        	        	    				});
	        	    						  }else{
	        	    							  swal({
	        		        			      		  title: data.msg,
	        		        			      		  icon: "error",
	        		        			      		  button: "确定",
	        		        			      	});
	        	    						  }
	        	    						  
	        	    				      },error:function(){
	        	    				      }, headers: {
	        	    				        "Authorization": "Bearer " + getTOKEN()
	        	    				      }
	        	    			    });
	        	    				
	        	    			});
	        	    			
	        	    		}else{
	        	    			swal({
	        			      		  title: "账户钱包CPA余额不足。",
	        			      		  icon: "error",
	        			      		  button: "确定",
	        			      	});
	        	    			return false;
	        	    		}
	        	    	}else{
	        	    		swal({
	      			      		  title: "账户钱包CPA余额不足。",
	      			      		  icon: "error",
	      			      		  button: "确定",
	      			      	});
	            		    return false;
	        	    	}
	        	    },
	        	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	        	    	swal({
	  			      		  title: "账户钱包CPA余额不足。",
	  			      		  icon: "error",
	  			      		  button: "确定",
	  			      	});
	        	    	flag = 0;
	          			return false;
	        	    }
	            });
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	console.log("没有账号信息2222");
	    	swal({
	      		  title: "身份不合法，不能挂单交易。",
	      		  icon: "error",
	      		  button: "确定",
	      	});
  			return false;
	    }
    });
}
