/**
 * Created by Administrator on 2017/4/25 0025.
 */
function Safe() {
  var _self = this,
    _$realname, _$mobile, _$datacid,_$account;

  function Init() {
//		先判断是否登录
	var flag = checkLogin();
    var uinfo = getUINFO();
    var uphone = localStorage.getItem("phone");
    var uname = localStorage.getItem("name");
	var cid = localStorage.getItem("ucard");
	var sec = localStorage.getItem("sec");
	
    if(uphone != null){
    	$("#phone").html(uphone);
    }
    if(uname != null)
    	$("#uname").html(uname);
    if(cid != null){
    	cid = cid.substring(0, 6) + "********" + cid.substring(15);
    	if(sec=="" || sec==null){
    		cid = cid+"(未认证)";
    		$('#tocard').show();
    	}else if(sec == "0"){
    		cid = cid+"(待审核)";
    		$('#tocard').hide();
    	}else if(sec == "1"){
    		cid = cid+"(认证)";
    		$('#tocard').hide();
    	}else if(sec == "2"){
    		cid = cid+"(审核不通过)";
    		$('#tocard').show();
    	}
    	
    	console.log("-------cid:"+cid+";;;;;;;;;;;;;;;;;;;;"+sec);
    	
    	$("#datacid").html(cid);
    }else{
    	$("#datacid").html("未认证");
    }
    	
//    $.ajax({
//      type: "GET",
//      url: getAPIURL() + "securitysettings/" + uid,
//      dataType: "json",
//      data: null,
//      success: function (data) {
//        /*用户名*/
//        _$account.find(".item_content").append('<span>' + data.account + '</span>');
//        /*实名认证 未认证 正在审核中 认证失败 已认证 状态*/
//        if (data.cid == '未认证') {
//          var txt1 = "";
//          txt1 += '<span>未认证</span>' +
//            '<span class="setting_status">设置</span>' +
//            '<i class="icon_right"></i>';
//          _$realname.find(".item_content").append(txt1);
//          _$realname.attr("href", "realName_authentication.html#safe_center.html");
//        }
//        else if (data.cid == '正在审核中') {
//          var txt1 = "";
//          txt1 += '<span>正在审核中</span>' +
//            '<span class="setting_status"></span>' +
//            '<i class="icon_right"></i>';
//          _$realname.find(".item_content").append(txt1);
//        }
//        else if (data.cid == '认证失败') {
//          var txt1 = "";
//          txt1 += '<span>认证失败</span>' +
//            '<span class="setting_status">设置</span>' +
//            '<i class="icon_right"></i>';
//          _$realname.find(".item_content").append(txt1);
//          _$realname.attr("href", "realName_authentication.html");
//        }
//        else {
//          _$realname.find(".item_content").append('<span>' + data.realname + '</span>');
//          _$datacid.find(".item_content").append('<span>'+data.cid+'</span>');
//        }
//        /*手机绑定*/
//        _$mobile.find(".item_content").append('<span>' + data.mobile + '</span><span class="setting_status">修改</span><i class="icon_right"></i>');
//        /*$.ajax({
//         type: "GET",
//         url: huifuAPIURL() + "chinapnr/registerinfo?uid=" + uid + "&cptype=mobile",
//         dataType: "json",
//         data: null,
//         success: function (data) {
//         if (data.rtn != 1) {
//         $("#changeBtn").html("<a href='Bound1.html'>修改</a>");
//         }
//         },
//         headers: {
//         "Authorization": "Bearer " + getTOKEN()
//         }
//         });*/
//      },
//      error: function () {
//        $('#mydiv').empty();
//        var txtsNULL = "<p class='nothing'>网络错误</p>";
//        $('#mydiv').append(txtsNULL);
//      },
//      headers: {
//        "Authorization": "Bearer " + getTOKEN()
//      }
//
//    });
  }

  (function () {
    _$account=$("#account");
    _$realname = $("#realname");
    _$mobile = $("#mobile");
    _$datacid = $("#datacid");
    Init();
  })();
}

$("#tocard").click(function (){
	location.href = '../page/realName_authentication.html';
});

var safe;
$(function () {
  safe = new Safe();
});