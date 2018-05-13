function FindPwd() {
  var self = this, _txtMsgCode, _sendCode;

  //发短信
  var timenum = 120;
  var lock = true;

  function TimeTicker(timenum) {
    if (parseInt(timenum) == 0) {
      $("#sendCode").val("点击获取");
      $("#sendCode").removeAttr("disabled");
      lock = true;
      clearTimeout();
    }
    else {
      timenum--;
      lock = false;
      $("#sendCode").val(timenum + "秒后重新发送");
      $("#sendCode").attr("disabled",true);
      setTimeout(function () {
        TimeTicker(timenum)
      }, 1000);
    }
  }

  /*点击下一步响应处理函数*/
  function findpwd(mobile,code,pwd) {
	  var num = Math.random()*7000000;
	  num = parseInt(num, 10);
	  console.log(num);
    $.ajax({
      type: "post",
      url: getAPIURL() + "fenuser/forgetPwd",
      dataType: "json",
      data: {
    	  "phone":mobile,
    	  "code":code,
    	  "code_type":"change_code",
    	  "pwd":pwd
      },
      success: function (data) {
        if (data.status == "200") {
          timenum = 0;
          TimeTicker(timenum);
          layer.open({
              content:"修改密码成功，前往登录页"
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          window.location.href = 'forgetPwd2.html';
        } else {
          layer.open({
            content:data.msg
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
          });
        }
      }
    });
  }

  /*点击发送验证码响应处理函数*/
  function getCode(mobile) {
    $.ajax({
      type: "post",
      url: getAPIURL() + "sms/verification_code/send",
      dataType: "json",
      data:{
        "mobile": mobile.toString(),
        "code_type":"change_code"
      } ,
      success: function (data) {
        if (data.error_code == "0") {
        	layer.open({
                content: '验证码发送成功',
                skin: 'msg',
                time: 2, //2秒后自动关闭
                end: function(){
                }
           });
          timenum = 120;
          if (lock == true) {
            TimeTicker(timenum);
          }
          else {
            return false;
          }
        }else if (data.error_code == "101") {
        	layer.open({
                content: '验证码发送成功',
                skin: 'msg',
                time: 2, //2秒后自动关闭
                end: function(){
                	$("#txtMsgCode").val(data.error_msg);
                }
           });
          timenum = 120;
          if (lock == true) {
            TimeTicker(timenum);
          }
          else {
            return false;
          }
        }
        else {
          layer.open({
            content: data.error_msg,
            skin: 'msg',
            time: 2 //2秒后自动关闭
          });
        }
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  (function () {
    _txtMsgCode = $("#txtMsgCode");
    /*验证码输入框*/
    _sendCode = $("#sendCode");
    /*获取验证码按钮*/
    _phone = $("#phone");
    /*手机号*/
    _next = $("#nextstep");
    /*下一步*/


    /*发送验证码*/
    _sendCode.on("click", function () {
      /*判断用户是否输入手机号*/
      var mobile = $.trim(_phone.val()).replace(/[^\d]/g, '');
      var pwd = $.trim($("#pwd").val());
      var pwd2 = $.trim($("#pwd2").val());
      if (mobile == "" || mobile.length != 11) {
        layer.open({
          content: '请输入正确手机号'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        _phone.val("").focus();
        return false;
      }
      if(pwd == "" || pwd == null || pwd.length<6 || pwd.length>20){
    	  layer.open({
              content: '请输入正确的密码'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            $("#pwd").val("").focus();
            return false;
      }
      if(pwd2 == "" || pwd2 == null){
    	  layer.open({
              content: '请重复密码'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            $("#pwd2").val("").focus();
            return false;
      }
      if(pwd2 != pwd){
    	  layer.open({
              content: '重复密码与新密码不一致，请重新输入'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            $("#pwd2").val("").focus();
            return false;
      }
      else {
        getCode(mobile);
      }
    });

    _next.on("click", function () {
      var mobile = $.trim(_phone.val()).replace(/[^\d]/g, '');
      var code = $.trim(_txtMsgCode.val()).replace(/[^\d]/g, '');
      var pwd = $.trim($("#pwd").val());
      var pwd2 = $.trim($("#pwd2").val());
      if (mobile == "" || mobile.length != 11) {
        layer.open({
          content: '请输入正确手机号'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        _phone.val("").focus();
        return false;
      }
      if(pwd == "" || pwd == null || pwd.length<6 || pwd.length>20){
    	  layer.open({
              content: '请输入正确的密码'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            $("#pwd").val("").focus();
            return false;
      }
      if(pwd2 == "" || pwd2 == null){
    	  layer.open({
              content: '请重复密码'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            $("#pwd2").val("").focus();
            return false;
      }
      if(pwd2 != pwd){
    	  layer.open({
              content: '重复密码与新密码不一致，请重新输入'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            $("#pwd2").val("").focus();
            return false;
      }
      else if (code == "") {
        layer.open({
          content: '请输入验证码'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        _txtMsgCode.focus();
        return false;
      }
      else {
        findpwd(mobile,code,pwd);
      }
    });
  })();
}

var findpwd;
$(function () {
  findpwd = new FindPwd();
});