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