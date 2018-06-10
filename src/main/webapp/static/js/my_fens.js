function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  var flag = checkLogin();
  
  console.log("------:ph::"+localStorage.getItem("phone"));

  function comptime() {
    $.ajax({
      type: "post",
      url: getAPIURL() + "user/fens/listQINYOU",
      dataType: "json",
      data: {
    	  "sh": localStorage.getItem("phone")
      },
      success: function (data) {
    	  console.log(data);
        var list = data.list;
        if (list.length <= 0) {
        	$("#qytuan").html(data.size);
        	
        } else {
        	var html;
        	$.each( list, function(index, content){
        		var inph = content.phone;
        		inph = inph.substring(0, 3) + "****" + inph.substring(7, 11);
        		
        		var nm = content.name;
        		nm = "***"+nm.substring(1);
			    html += "<tr><td class='first'>"+(index+1)+"</td><td>"+nm+"</td><td>"+inph+"</td></tr>";
			});
        	
        	$("#myf_num").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲友团："+data.size+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;粉丝团："+data.pages);
        	$("#gift").html(html);
        }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
//13919358705
  (function () {
    _$gift = $("#gift");
    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});