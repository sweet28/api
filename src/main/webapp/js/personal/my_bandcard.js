function Gift() {
  var self = this, _$gift;

  function comptime() {
	  var uid = localStorage.getItem("uid");
	  var page = 100;
	  var row = 0;
	  var tmp = getTimestamp();
	  var rad = getRandom();
	  var ton = getTom();
	  var str = "uid="+uid+"pg=0"+"ts=100"+"tmp="+tmp+"rad="+rad+"tom="+ton;
    $.ajax({
      type: "post",
      url: getAPIURL() + "fs/bank/list",
      dataType: "json",
      data: {
    	  "uid":uid,
    	  "pg":0,
    	  "ts":100,
          "tmp":tmp,
          "rad":rad,
          "tom":ton,
          "token":commingSoon1(str)
      },
      success: function (data) {
        var list = data.list;
        if (list.length <= 0) {
          $("#a_miner").html("<ul><li class='nothing'><p>暂无记录</p></li></ul>");
        } else {
        	$("#yinhang").html(list[0].bank);
        	$("#cardId").html(list[0].cardNumber);
        	$("#zhihang").html(list[0].openBranch);
        }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
    
    
    $.ajax({
        type: "post",
        url: getAPIURL() + "fs/bank/cx",
        dataType: "json",
        data: {
      	    "uid":uid,
            "tp":1
        },
        success: function (data) {
      	  if(data.status == 200){
      		$("#alipay").html(data.data.cardNumber);
      	  }else{
      		$("#alipay").html("暂无记录");
      	  }
        }, headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      });
    
    $.ajax({
        type: "post",
        url: getAPIURL() + "fs/bank/cx",
        dataType: "json",
        data: {
      	    "uid":uid,
            "tp":2
        },
        success: function (data) {
      	  if(data.status == 200){
      		$("#weixin").html(data.data.cardNumber);
      	  }else{
      		$("#weixin").html("暂无记录");
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

function addCard(){
	var yinhang;
	var cardId;
	var zhihang;
	
	swal({   
		title: "请输入银行名称",   
		type: "input",   
		showCancelButton: true,   
		closeOnConfirm: false,   
		animation: "slide-from-top",   
		inputPlaceholder: "请输入银行名称" 
	}, function(inputValue){   
		if (inputValue === false) return false;      
		if (inputValue === "") {     
			swal.showInputError("请输入!");     
			return false   
		}
		yinhang = inputValue;
		
		swal({   
			title: "请输入卡号",   
			type: "input",   
			showCancelButton: true,   
			closeOnConfirm: false,   
			animation: "slide-from-top",   
			inputPlaceholder: "请输入卡号" 
		}, function(inputValue){   
			if (inputValue === false) return false;      
			if (inputValue === "") {     
				swal.showInputError("请输入!");     
				return false   
			}
			cardId = inputValue;
			swal({   
				title: "请输入支行名称",   
				type: "input",   
				showCancelButton: true,   
				closeOnConfirm: false,   
				animation: "slide-from-top",   
				inputPlaceholder: "请输入支行名称" 
			}, function(inputValue){   
				if (inputValue === false) return false;      
				if (inputValue === "") {     
					swal.showInputError("请输入!");     
					return false   
				}
				zhihang = inputValue;
				
				var tmp = getTimestamp();
			    var rad = getRandom();
			    var ton = getTom();
			    var str = "uid="+localStorage.getItem("uid")+"kh="+cardId+"zh="+zhihang+"yh="+yinhang+"xm="+localStorage.getItem("name")+"sh"+localStorage.getItem("phone")+"tmp="+tmp+"rad="+rad+"tom="+ton;
				$.ajax({
				      type: "post",
				      url: getAPIURL() + "fs/bank/tjcard",
				      dataType: "json",
				      data:{
				    	  "uid":localStorage.getItem("uid"),
				    	  "kh":cardId,
				    	  "zh":zhihang,
				    	  "yh":yinhang,
				    	  "xm":localStorage.getItem("name"),
				    	  "sh":localStorage.getItem("phone"),
				          "tmp":tmp,
				          "rad":rad,
				          "tom":ton
				      },
				      success: function (data) {
				        if (data.status==200) {
				        	swal("绑定银行卡成功!", "success"); 
				        } else {
				        	swal(data.msg, "error"); 
				        }
				      },
				      headers: {
				        "Authorization": "Bearer " + getTOKEN()
				      }
				    });
			});
		});
		
	});
}

function addAliPay(){
	swal({   
		title: "请输入支付宝账号",   
		type: "input",   
		showCancelButton: true,   
		closeOnConfirm: false,   
		animation: "slide-from-top",   
		inputPlaceholder: "请输入账号" 
	}, function(inputValue){   
		if (inputValue === false) return false;      
		if (inputValue === "") {     
			swal.showInputError("请输入!");     
			return false   
		}
		
		var tmp = getTimestamp();
	    var rad = getRandom();
	    var ton = getTom();
	    //var str = "uid="+localStorage.getItem("uid")+"xm="+localStorage.getItem("name")+"sh"+localStorage.getItem("phone")+"tmp="+tmp+"rad="+rad+"tom="+ton;
		$.ajax({
		      type: "post",
		      url: getAPIURL() + "fs/bank/zw",
		      dataType: "json",
		      data:{
		    	  "uid":localStorage.getItem("uid"),
		    	  "kh":inputValue,
		    	  "tp":1,
		    	  "xm":localStorage.getItem("name"),
		    	  "sh":localStorage.getItem("phone"),
		          "tmp":tmp,
		          "rad":rad,
		          "tom":ton
		      },
		      success: function (data) {
		        if (data.status==200) {
		        	swal("绑定支付宝成功!", "success"); 
		        } else {
		        	swal(data.msg, "error"); 
		        }
		      },
		      headers: {
		        "Authorization": "Bearer " + getTOKEN()
		      }
		    });
	});
}

function addWXPay(){
	swal({   
		title: "请输入微信账号",   
		type: "input",   
		showCancelButton: true,   
		closeOnConfirm: false,   
		animation: "slide-from-top",   
		inputPlaceholder: "请输入账号" 
	}, function(inputValue){   
		if (inputValue === false) return false;      
		if (inputValue === "") {     
			swal.showInputError("请输入!");     
			return false   
		}
		
		var tmp = getTimestamp();
	    var rad = getRandom();
	    var ton = getTom();
	    //var str = "uid="+localStorage.getItem("uid")+"xm="+localStorage.getItem("name")+"sh"+localStorage.getItem("phone")+"tmp="+tmp+"rad="+rad+"tom="+ton;
		$.ajax({
		      type: "post",
		      url: getAPIURL() + "fs/bank/zw",
		      dataType: "json",
		      data:{
		    	  "uid":localStorage.getItem("uid"),
		    	  "kh":inputValue,
		    	  "tp":2,
		    	  "xm":localStorage.getItem("name"),
		    	  "sh":localStorage.getItem("phone"),
		          "tmp":tmp,
		          "rad":rad,
		          "tom":ton
		      },
		      success: function (data) {
		        if (data.status==200) {
		        	swal("绑定微信成功!", "success"); 
		        } else {
		        	swal(data.msg, "error"); 
		        }
		      },
		      headers: {
		        "Authorization": "Bearer " + getTOKEN()
		      }
		    });
	});
}

function addImtoken(){
	swal("Comming soon!"); 
}

