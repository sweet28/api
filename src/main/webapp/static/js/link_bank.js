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
	
	$.ajax({
	      type: "post",
	      url: getAPIURL() + "bank/addBlank",
	      dataType: "json",
	      data:{
	    	  "fensUserId":localStorage.getItem("uid"),
	    	  "cardNumber":cardnum.val(),
	    	  "openBranch":cardbank.val(),
	    	  "bank":bankname.val(),
	    	  "name":localStorage.getItem("name"),
	    	  "phone":localStorage.getItem("phone"),
	    	  "isApply":1
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
		            content: '绑定银行卡失败。'
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