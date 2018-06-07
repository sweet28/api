(function () {
	
	var flag = checkLogin();
  
    var phone = Number(getPhone());
    var yqURL = "http://cpa.artforyou.cn:8089/api/reg.jsp?yqurl=";//"http://cpa.artforyou.cn:8089/api/reg.jsp?yqurl=";
  
    if(phone!=null){
    	$("#yaoqing_url").html(yqURL + phone.toString(8));
    }
})();

function copyURL(){
	swal({
  	  title: '当前浏览器不支持复制，请长按邀请链接复制并发送给好友注册',
  	  type: 'info'
  	})
}