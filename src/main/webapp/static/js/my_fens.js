function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");
  
  var flag = checkLogin();

  function comptime() {
    $.ajax({
      type: "post",
      url: getAPIURL() + "fenuser/list2",
      dataType: "json",
      data: {
    	  "type":"all",
    	  "phone": localStorage.getItem("phone")
      },
      success: function (data) {
        var list = data.list;
        if (list.length <= 0) {
        	console.log(data);
        	console.log(data.size);
        	$("#myf_num").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲友团："+data.size+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;粉丝团：0");
        	
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

  (function () {
    _$gift = $("#gift");
    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});