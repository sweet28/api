function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  var flag = checkLogin();

  function comptime() {
    $.ajax({
      type: "post",
      url: getAPIURL() + "user/miner/slgeren",
      dataType: "json",
      data: {
    	  "uid": localStorage.getItem("uid")
      },
      success: function (data) {
    	  console.log("::::"+data);
    	  if(data.status == 200){
    		  $("#qytuan").html(data.data);
    	  }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
  
  function kuangjiList() {
	    $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/he",
	      dataType: "json",
	      data: {
	    	  "uid": localStorage.getItem("uid")
	      },
	      success: function (data) {
	        var list = data.list;
	        if (list.length <= 0) {
	        	
	        } else {
	        	var html = "";
	        	$.each( list, function(index, content){
	        		var inph = content.phone;
	        		inph = inph.substring(0, 3) + "****" + inph.substring(7, 11);
	        		
	        		var nm = content.name;
	        		nm = "***"+nm.substring(1);
	        		
				    //html += "<tr><td class='first'>"+(index+1)+"</td><td>"+nm+"</td><td>"+inph+"</td></tr>";
				    
//				    html += "<li>" +
//				    			"<p>序号：" + (index+1) + "</p>" +
//					    		"<span>姓名："+ nm +"</span>" +
//					    		"<p>手机号：" + inph + "</p>"
//				    		"</li>&nbsp;&nbsp;&nbsp;&nbsp;";
				});
//	        	$("#gift").html(html);
	        }
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
	    });
	  }

  (function () {
    _$gift = $("#gift");
    comptime();
    kuangjiList();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});