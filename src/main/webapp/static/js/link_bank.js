var bankname = $("#bankname");
var cardnum = $("#cardnum");
var cardloc = $("#cardloc");
var cardbank = $("#cardbank");

$("#recharge").click(function(){
	console.log("-------------");
	
	if(bankname.val()==null || bankname.val()==""){
		layer.open({
            content: "请输入银行名称，不能含有空格！",
            btn: '确定'
          });
          return false;
	}
	if(cardnum.val()==null || cardnum.val()==""){
		layer.open({
            content: "请输入银行卡号！",
            btn: '确定'
          });
          return false;
	}
//	if(cardloc.val()==null || cardloc.val()==""){
//		layer.open({
//            content: "请输入开户所在地！",
//            btn: '确定'
//          });
//          return false;
//	}
	if(cardbank.val()==null || cardbank.val()==""){
		layer.open({
            content: "请输入开户行！",
            btn: '确定'
          });
          return false;
	}
	
	var tmp = getTimestamp();
    var rad = getRandom();
    var ton = getTom();
    var str = "uid="+localStorage.getItem("uid")+"kh="+cardnum.val()+"zh="+cardbank.val()+"yh="+bankname.val()+"xm="+localStorage.getItem("name")+"sh"+localStorage.getItem("phone")+"tmp="+tmp+"rad="+rad+"tom="+ton;
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "fs/bank/tjcard",
	      dataType: "json",
	      data:{
	    	  "uid":localStorage.getItem("uid"),
	    	  "kh":cardnum.val(),
	    	  "zh":cardbank.val(),
	    	  "yh":bankname.val(),
	    	  "xm":localStorage.getItem("name"),
	    	  "sh":localStorage.getItem("phone"),
	          "tmp":tmp,
	          "rad":rad,
	          "tom":ton
	      },
	      success: function (data) {
	        if (data.status==200) {
	          layer.open({
	            content: '绑定银行卡成功。'
	            , btn: ['确定']
	            , yes: function (index) {
	              window.location.href = "../page/personal.html";
	            }
	          });
	        } else {
	        	layer.open({
		            content: data.msg
		            , btn: ['确定']
		            , yes: function (index) {
		  	          window.location.href = "../page/my_bankcard.html";
		            }
		          });
	        }
	      },
	      headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	    });
});