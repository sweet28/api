(function () {
  var userAgent = navigator.userAgent;
  var index = userAgent.indexOf("Android");
  if(index >= 0){
    var androidVersion = parseFloat(userAgent.slice(index+8));
    if(androidVersion<5){
      var html = document.documentElement;
      var hW = html.getBoundingClientRect().width;
      html.style.fontSize = hW / 7.5 + "px";
    }
  }
})();

function fmtDate(obj){
    var date =  new Date(obj);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}

Date.prototype.Format = function(fmt){  
    var o = {  
         "M+": this.getMonth()+1,  
         "d+": this.getDate(),  
         "H+": this.getHours(),  
         "m+": this.getMinutes(),  
         "s+": this.getSeconds(),  
         "S+": this.getMilliseconds()  
    };  
  
    //因位date.getFullYear()出来的结果是number类型的,所以为了让结果变成字符串型，下面有两种方法：  
    if(/(y+)/.test(fmt)){  
        //第一种：利用字符串连接符“+”给date.getFullYear()+""，加一个空字符串便可以将number类型转换成字符串。  
        fmt=fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));  
    }  
    for(var k in o){  
        if (new RegExp("(" + k +")").test(fmt)){  
            //第二种：使用String()类型进行强制数据类型转换String(date.getFullYear())，这种更容易理解。  
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(String(o[k]).length)));  
        }  
    }     
    return fmt;  
}

function commingSoon(){
	layer.open({
        content:"近期上线，敬请期待！",
        btn:'确定'
      });
}

//加入百度统计
function Common() {

  _init = function() {
    //$(document.body).append("<script type=\"text/javascript\">var cnzz_protocol = ((\"https:\" == document.location.protocol) ? \" https://\" : \" http://\");document.write(unescape(\"%3Cspan id='cnzz_stat_icon_1259647640'%3E%3C/span%3E%3Cscript src='\" + cnzz_protocol + \"s4.cnzz.com/z_stat.php%3Fid%3D1259647640' type='text/javascript'%3E%3C/script%3E\"));</script>)");

//		$(document.body).append("<script src=\"https://s95.cnzz.com/z_stat.php?id=1259787883&web_id=1259787883\" language=\"JavaScript\"></script>");
//		$(document.body).append("<script src=\"https://hm.baidu.com/hm.js?1e43e612748ab51862300a1d1408228e\" language=\"JavaScript\"></script>");
    (function() {
//      var hm = document.createElement("script");
//      hm.src = "https://hm.baidu.com/hm.js?1e43e612748ab51862300a1d1408228e";
//      var s = document.getElementsByTagName("script")[0];
//      s.parentNode.insertBefore(hm, s);
    })();

//	//如果是移动设备
//	if(device.mobile())
//  {
//   	alert("移动设备"+navigator.userAgent);
//  }
//  else
//  {
//  	alert("不是移动设备"+navigator.userAgent);
//  }
//
  },

    (function() {
      _init();
    })();

}

var common;
var device = localStorage.getItem("device");

var request ={
  QueryString : function(val){
    var uri = window.location.search;
    var re = new RegExp("" +val+ "=([^&?]*)", "ig");
    return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null);
  }
};

//判断是否登录，如果未登录跳转登录页，已登录则可以通过getUIDByJWT().unique_name获取UID
function getUIDByJWT() {

  var value = "";
  var str = window.navigator.userAgent;

  //如果是移动设备
  if (str.indexOf("jojojr") != -1) {
    /*是安卓*/
    var  search=window.location.search;
    // alert("search"+search)
    if(search.indexOf("?")!=-1){
      var msg=search.slice(1);
      if(msg.indexOf("&")!=-1){
        var msgArr= msg.split("&");
        for (var i=0;i<msgArr.length;i++){
          var item1=msgArr[i].split("=");
          if(item1[0]=="token"){
            value=item1[1];
          }
        }
      }else {
        var item2=msg.split("=");
        if(item2[0]=="token"){
          value=item2[1];
        }
      }
    }
	//value="000";
    if (value == null
      || value == ""
      || value == undefined) {
    	swal({
		  	  title: '请登录',
		  	  type: 'info',
		  	  showCancelButton: true,
		  	  confirmButtonText: "确定", 
		  	  cancelButtonText: "取消",
		  	  closeOnConfirm: true, 
		  	  closeOnCancel: true
		  	}).then(function(isConfirm) {
		  	  if (isConfirm === true) {
		  		  window.location.href = "../";
		  	  } else if (isConfirm === false) {
		  		  window.location.href = "../";
		  	  } else {
		  		  window.location.href = "../";
		  	  }
		});
      return false;
    }
    else {
      var aftervalue = value.split(".");
      return $.parseJSON($.base64.atob(aftervalue[1], true));
    }
  }
  else {
    value = localStorage.getItem("token");
	
	//value = "123";
	
    if (value == null) {
    	swal({
		  	  title: '请登录',
		  	  type: 'info',
		  	  showCancelButton: true,
		  	  confirmButtonText: "确定", 
		  	  cancelButtonText: "取消",
		  	  closeOnConfirm: true, 
		  	  closeOnCancel: true
		  	}).then(function(isConfirm) {
		  	  if (isConfirm === true) {
		  		  window.location.href = "../";
		  	  } else if (isConfirm === false) {
		  		  window.location.href = "../";
		  	  } else {
		  		  window.location.href = "../";
		  	  }
			});
      setTimeout(function () {
//              location.href = 'login.html?returnurl=' + window.location.href;
    	  window.location.href = "../";
      }, 1000);
      return false;
    }
    else {
      var aftervalue = value.split(".");
      return $.parseJSON($.base64.atob(aftervalue[1], true));
    }
  }

}

function getTimestamp(){
	return Date.parse(new Date());
}

function getRandom(){
return Math.floor(Math.random()*9999999999+1)
}

function getTom(){
	return localStorage.getItem("token");
}

function checkLogin(){
	var uid = getTOKEN();
	if (uid == undefined || uid == "" || uid == null) {
		  localStorage.clear();
		  
		  swal({
		  	  title: '已退出',
		  	  type: 'info',
		  	  showCancelButton: true,
		  	  confirmButtonText: "确定", 
		  	  cancelButtonText: "取消",
		  	  closeOnConfirm: true, 
		  	  closeOnCancel: true
		  	}).then(function(isConfirm) {
		  	  if (isConfirm === true) {
		  		  window.location.href = "../";
		  	  } else if (isConfirm === false) {
		  		  window.location.href = "../";
		  	  } else {
		  		  window.location.href = "../";
		  	  }
			});
	    return false;
	  }
}

function getUINFO(){
	return localStorage.getItem("uinfo");
}

//获取uid
function getUIDByJWT1() {
  var value = localStorage.getItem("token");
  if (value == null) {
    /*setTimeout(function() {
     location.href = 'login.html?returnurl=' + window.location.href;
     }, 1000);*/
    return false;
  } else {
    var aftervalue = value.split(".");
    return $.parseJSON($.base64.atob(aftervalue[1], true));
  }
}

function getUserName() {
  return localStorage.getItem("username");
}

function getPhone() {
	  return localStorage.getItem("phone");
}

function Logout() {
  //如果是移动设备
  /*if(device==="app")
   {
   localStorage.clear();
   }
   else
   {
   sessionStorage.clear();
   }*/
  localStorage.clear();
  swal({
  	  title: '已退出',
  	  type: 'info',
  	  showCancelButton: true,
  	  confirmButtonText: "确定", 
  	  cancelButtonText: "取消",
  	  closeOnConfirm: true, 
  	  closeOnCancel: true
  	}).then(function(isConfirm) {
  	  if (isConfirm === true) {
  		  window.location.href = "../";
  	  } else if (isConfirm === false) {
  		  window.location.href = "../";
  	  } else {
  		  window.location.href = "../";
  	  }
	});
//  layer.open({
//    content: "已退出",
//    skin: 'msg',
//    time: 2, //2秒后自动关闭
//    end: function () {
//      location.href = '../page/login.html';
//    }
//  });
}

function getTOKEN() {
  return localStorage.getItem("token");
  //如果是移动设备
  /*if(device==="app")
   {
   return localStorage.getItem("token");
   }
   else
   {
   return sessionStorage.getItem("token");
   }*/
}

//+---------------------------------------------------
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd
//+---------------------------------------------------
function daysBetween(DateOne,DateTwo){
	var OneMonth = DateOne.substring(5,DateOne.lastIndexOf ('-'));
	var OneDay = DateOne.substring(DateOne.length,DateOne.lastIndexOf ('-')+1);
	var OneYear = DateOne.substring(0,DateOne.indexOf ('-'));
	
	var TwoMonth = DateTwo.substring(5,DateTwo.lastIndexOf ('-'));
	var TwoDay = DateTwo.substring(DateTwo.length,DateTwo.lastIndexOf ('-')+1);
	var TwoYear = DateTwo.substring(0,DateTwo.indexOf ('-'));
	
	var cha=((Date.parse(OneMonth+'/'+OneDay+'/'+OneYear)- Date.parse(TwoMonth+'/'+TwoDay+'/'+TwoYear))/86400000);
	return Math.abs(cha);
}

var cpaURL = "http://cpa.artforyou.cn/";//"http://cpa.artforyou.cn/";//"http://cpa.artforyou.cn/";



function getAPIURL() {
//  return "http://localhost:8080/api/";
//	return "http://cpa.artforyou.cn:8088/api/";
	return cpaURL +"api/";
}

function baseUrl() {
//  return "http://localhost:8080/api/";
//	return "http://cpa.artforyou.cn:8088/api/";
	return cpaURL +"api/";
}
//var getwapURL = "http://localhost:8080/api/";
//var getwapURL = "http://cpa.artforyou.cn:8088/api/";
var getwapURL = cpaURL +"api/";

function getP2PAPI() {
//  return "http://cpa.artforyou.cn:8088/api/";
}

//        取得参数
function parseUrl(){
  var url=location.href;
  var i=url.indexOf('?');
  if(i==-1)return;
  var querystr=url.substr(i+1);
  var arr1=querystr.split('&');
  var arr2 = new Object();
  for  (i in arr1){
    var ta=arr1[i].split('=');
    arr2[ta[0]]=ta[1];
  }
  return arr2;
}


function getYUE(){
	 //	调用相关金额的接口
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
	    	      return dd.ableCpa;
	    	}else{
	    		  return 0;
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	      return 0;
	    }
	  });
}


function gameAPIURL() {
//  return "http://cpa.artforyou.cn:8088/api/";
}

//function getOpenIdURL() {
//	return "http://api.gcjiujiu.com/";
//}
//var gameURL = "http://cpa.artforyou.cn:8088/api/";

function getTICKET() {
  return localStorage.getItem("ticket");
}
function getSHARD() {
  return localStorage.getItem("sharid");
}
function getOPENID() {
  return localStorage.getItem("openid");
}
function getNAME() {
  return localStorage.getItem("nickname");
}
//function getFinance () {
//	return "http://api.gcjiujiu.com/";
//}

function huifuAPIURL() {
//  return "http://cpa.artforyou.cn:8088/api/";
}


