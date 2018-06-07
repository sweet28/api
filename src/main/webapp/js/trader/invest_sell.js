(function(){
	$(function(){
		$.ajax({
		      type: "post",
		      url: getAPIURL() + "jb/jt",
		      dataType: "json",
		      data:null,
		      success: function (data) {
		    	  if(data.status==200){
		    		  $("#zgj").html(data.data.maxPrice);
		    		  $("#zdj").html(data.data.minPrice);
		    	  }else{
		    		  swal({
			      		  title: data.msg,
			      		  icon: "error",
			      		  button: "确定",
			      	});
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
	});
	

	var datas = new Array();
	
	$(function(){
		$.ajax({
		      type: "post",
		      url: getAPIURL() + "jb/mouth",
		      dataType: "json",
		      data:null,
		      success: function (data) {
		    	  if(data.status==200){
		    		  var list = data.data;
		    		  if(list.length > 0){

			    		  for(var i = 0; i < list.length; i++){
			    			  var datas2 = new Array(5);
			    			  datas2[0] = fmtDate(list[i].createDate);
			    			  datas2[1] = list[i].minPrice;
			    			  datas2[2] = list[i].maxPrice;
			    			  datas2[3] = list[i].minPrice;
			    			  datas2[4] = list[i].maxPrice;
			    			  datas[i] = datas2;
			    		  }
			    		  
			    			var dom = document.getElementById("container");
			    			var myChart = echarts.init(dom);
			    			var app = {};
			    			option = null;
			    			var upColor = '#ec0000';
			    			var upBorderColor = '#8A0000';
			    			var downColor = '#00da3c';
			    			var downBorderColor = '#008F28';

			    			// 数据意义：开盘(open)，收盘(close)，最低(lowest)，最高(highest)
			    			var data0 = splitData(datas);

			    			function splitData(rawData) {
			    			    var categoryData = [];
			    			    var values = []
			    			    for (var i = 0; i < rawData.length; i++) {
			    			        categoryData.push(rawData[i].splice(0, 1)[0]);
			    			        values.push(rawData[i])
			    			    }
			    			    return {
			    			        categoryData: categoryData,
			    			        values: values
			    			    };
			    			}

			    			function calculateMA(dayCount) {
			    			    var result = [];
			    			    for (var i = 0, len = data0.values.length; i < len; i++) {
			    			        if (i < dayCount) {
			    			            result.push('-');
			    			            continue;
			    			        }
			    			        var sum = 0;
			    			        for (var j = 0; j < dayCount; j++) {
			    			            sum += data0.values[i - j][1];
			    			        }
			    			        result.push(sum / dayCount);
			    			    }
			    			    return result;
			    			}

			    			option = {
			    			    title: {
			    			        //text: '上证指数',
			    			        left: 0
			    			    },
			    			    tooltip: {
			    			        trigger: 'axis',
			    			        axisPointer: {
			    			            type: 'cross'
			    			        }
			    			    },
			    			    legend: {
			    			        data: ['日K']
			    			    },
			    			    grid: {
			    			        left: '10%',
			    			        right: '10%',
			    			        bottom: '15%'
			    			    },
			    			    xAxis: {
			    			        type: 'category',
			    			        data: data0.categoryData,
			    			        scale: true,
			    			        boundaryGap : false,
			    			        axisLine: {onZero: false},
			    			        splitLine: {show: true},
			    			        splitNumber: 20,
			    			        min: 'dataMin',
			    			        max: 'dataMax'
			    			    },
			    			    yAxis: {
			    			        scale: true,
			    			        splitArea: {
			    			            show: true
			    			        }
			    			    },
									// dataZoom: [
									// {
									// type: 'inside',
									// start: 50,
									// end: 100
									// },
									// {
									// show: true,
									// type: 'slider',
									// y: '90%',
									// start: 50,
									//			    			            end: 100
									//			    			        }
									//			    			    ],
			    			    series: [
			    			        {
			    			            name: '日K',
			    			            type: 'candlestick',
			    			            data: data0.values,
			    			            itemStyle: {
			    			                normal: {
			    			                    color: upColor,
			    			                    color0: downColor,
			    			                    borderColor: upBorderColor,
			    			                    borderColor0: downBorderColor
			    			                }
			    			            },
			    			            markPoint: {
			    			                label: {
			    			                    normal: {
			    			                        formatter: function (param) {
			    			                            return param != null ? Math.round(param.value) : '';
			    			                        }
			    			                    }
			    			                },
			    			                data: [
			    			                    {
			    			                        name: 'XX标点',
			    			                        coord: ['2013/5/31', 2300],
			    			                        value: 2300,
			    			                        itemStyle: {
			    			                            normal: {color: 'rgb(41,60,85)'}
			    			                        }
			    			                    },
			    			                    {
			    			                        name: 'highest value',
			    			                        type: 'max',
			    			                        valueDim: 'highest'
			    			                    },
			    			                    {
			    			                        name: 'lowest value',
			    			                        type: 'min',
			    			                        valueDim: 'lowest'
			    			                    },
			    			                    {
			    			                        name: 'average value on close',
			    			                        type: 'average',
			    			                        valueDim: 'close'
			    			                    }
			    			                ],
			    			                tooltip: {
			    			                    formatter: function (param) {
			    			                        return param.name + '<br>' + (param.data.coord || '');
			    			                    }
			    			                }
			    			            },
			    			            markLine: {
			    			                symbol: ['none', 'none'],
			    			                data: [
			    			                    [
			    			                        {
			    			                            name: 'from lowest to highest',
			    			                            type: 'min',
			    			                            valueDim: 'lowest',
			    			                            symbol: 'circle',
			    			                            symbolSize: 10,
			    			                            label: {
			    			                                normal: {show: false},
			    			                                emphasis: {show: false}
			    			                            }
			    			                        },
			    			                        {
			    			                            type: 'max',
			    			                            valueDim: 'highest',
			    			                            symbol: 'circle',
			    			                            symbolSize: 10,
			    			                            label: {
			    			                                normal: {show: false},
			    			                                emphasis: {show: false}
			    			                            }
			    			                        }
			    			                    ],
			    			                    {
			    			                        name: 'min line on close',
			    			                        type: 'min',
			    			                        valueDim: 'close'
			    			                    },
			    			                    {
			    			                        name: 'max line on close',
			    			                        type: 'max',
			    			                        valueDim: 'close'
			    			                    }
			    			                ]
			    			            }
			    			        }

			    			    ]
			    			};

			    			if (option && typeof option === "object") {
			    			    myChart.setOption(option, true);
			    			}
		    		  }
		    	  }else{
		    		  swal({
			      		  title: data.msg,
			      		  icon: "error",
			      		  button: "确定",
			      	});
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
		
		
	});
	
	
  $(function(){
      var _type = 2;    
      $.ajax({
	      type: "post",
	      url: getAPIURL() + "miner/record/recordlist",
	      dataType: "json",
	      data:{
	    	  "traderState": 0,
          	  "traderType": _type,
          	  "page": 0,
          	  "row": 10000,
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
	        	if(trader_count<1){
	        		swal({
			      		  title: "交易数量不能小于1。",
			      		  icon: "error",
			      		  button: "确定",
			      	});
	        		return false;
	        	}
	        	
	        	if(trader_count > 100){
	        		swal({
			      		  title: "交易数量不能大于100。",
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
		        	        		    window.location.href = "traderCenterSell";
		        	        		  } else { 
		        	        		    //swal("取消！", "你的虚拟文件是安全的:)","error"); 
		        	        		    window.location.href = "traderCenterSell";
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
		        	        		    window.location.href = "traderCenterSell";
		        	        		  } else { 
		        	        		    //swal("取消！", "你的虚拟文件是安全的:)","error"); 
		        	        		    window.location.href = "traderCenterSell";
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
		        	          		  window.location.href = "traderCenterSell";
		        	          	  } else if (isConfirm === false) {
		        	          		  window.location.href = "traderCenterSell";
		        	          	  } else {
		        	          		  window.location.href = "traderCenterSell";
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

function mrCPA(id,count){
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
	        	    		if(yue*0.8 >= count){
	        	    			
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
	        	    				      url: getAPIURL() + "kuangjy/jy/sellDanJieDan",
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
	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenterSell";
	        	    					          	  } else if (isConfirm === false) {
	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenterSell";
	        	    					          	  } else {
	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenterSell";
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
	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenterSell";
	        	    					          	  } else if (isConfirm === false) {
	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenterSell";
	        	    					          	  } else {
	        	    					          		  window.location.href = getAPIURL()+"cpa/traderCenterSell";
	        	    					          	  }
	        	    				          	});
	        	    				        }
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

