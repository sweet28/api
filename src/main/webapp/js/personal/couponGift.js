function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  $("#jibie").html("加载中…");
  $("#shijian").html("计算中…");
  
  var flag = checkLogin();
  
  function couponGift() {
	    $.ajax({
		      type: "post",
		      url: getAPIURL() + "quan/couponGiftInfo",
		      dataType: "json",
		      data: {
		    	  "uid": localStorage.getItem("uid"),
		    	  "phone": localStorage.getItem("phone")
		      },
		      success: function (data) {

		    	  $("#couponTotalScore").html(data.data.couponTotalScore);
		    	  $("#couponYiyongScore").html(data.data.couponYiyongScore);
		    	  $("#one7").html(data.data.one7);
		    	  $("#one21").html(data.data.one21);
		    	  $("#two15").html(data.data.two15);
		    	  $("#three10").html(data.data.three10);
		    	  
		    	  $("#couponTotalScoreReal").html(data.data.couponTotalScoreReal);
		    	  $("#one7Real").html(data.data.one7);
		    	  $("#one21Real").html(data.data.one21);
		    	  $("#two15Real").html(data.data.two15);
		    	  $("#three10Real").html(data.data.three10);
		    	  
		      },error:function(){
		    	  console.log(333);
		      }, headers: {
		        "Authorization": "Bearer " + getTOKEN()
		      }
	    });
	  }

  (function () {
    _$gift = $("#gift");
    couponGift();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});

function toMoney(){
	$("#tixian").hide();
	var couponScoreReal = $("#couponTotalScoreReal").html();
	console.log(couponScoreReal);
	
	if(couponScoreReal < 200){
		
	}
	if(couponScoreReal >= 200){
		$.ajax({
			  type: "post",
		      url: getAPIURL() + "quan/tiqu",
		      dataType: "json",
		      data: {
		    	  "uid": localStorage.getItem("uid")
		      },
		      success:function(data){
		    	  if(data.status == 200){
		    		  swal({
		    			  title: "等待官方审核",
		    			  icon: "info",
		    			  button: "确定",
		    		  });
		    		  $("#tixian").show();
		    		  $("#couponYiyongScore").html(data.data);
		    	  }else{
		    		  swal({
		    			  title: data.msg,
		    			  icon: "error",
		    			  button: "确定",
		    		  });
		    	  }
		    	  $("#tixian").show();
		      },
		      error:function(){
		    	  swal({
	    			  title: "提取人数过多，请稍后重试",
	    			  icon: "error",
	    			  button: "确定",
	    		  });
		    	  $("#tixian").show();
		      }
		});
		
	}
}
