/**
 * Created by wenjuanzhang on 2016/6/21.
 */
$(function () {
  $("#fp1").show();
  $("#fp2").hide();
});

function Bound1() {
  var _self = this,
    _$realmobile;
  //发短信
  var timenum = 120;
  var lock = true;

  function TimeTicker() {
    if (parseInt(timenum) == 0) {
      $("#sendCode").val("点击获取").removeAttr("disabled").css({"color":"#0197ff"});
      lock = true;
      //$("#voiceCode").show();
      clearTimeout(re);
    }
    else {
      timenum--;
      lock = false;
      $("#sendCode").val(timenum + "秒后重新发送").attr("disabled", true).css({"color":"#a7a7a7"});
      re = setTimeout(TimeTicker, 1000);
    }
  }

  function Init() {
    var uid = getUIDByJWT().unique_name;
    loading.open();
    $.ajax({
      type: "GET",
      url: getAPIURL() + "securitysettings/" + uid,
      dataType: "json",
      data: null,
      success: function (data) {
        loading.close();
        //未认证 正在审核中 认证失败 已认证 状态
        if (data.cid.length>=18) {
          $("#bound_title>div").click(function () {
            var index = $(this).index();
            $(this).addClass("active").siblings("div").removeClass("active");
            $(".bound_con>div").eq(index).show().siblings().hide();
          });
          $("#datacid").html(data.cid);
        }
        else if(data.cid=="未认证"){
          $("#bound_title>div").click(function () {
            var index = $(this).index();
            if (index == 1) {
              layer.open({
                content: '请先实名认证',
                btn: '我知道了'
              });
            }
          });
        }
        _$realmobile.html(data.mobile);
        _$realmobile.attr("phoneNum", data.realmobile);
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  (function () {
    _$realmobile = $("#realmobile");
    Init();

    /*获取验证码*/
    $("#sendCode").click(function () {
      var uid = getUIDByJWT().unique_name;
      loading.open();
      $.ajax({
        type: "POST",
        url: getAPIURL() + "securitysettings/" + uid + "/sendcode1?phone=" + _$realmobile.attr("phoneNum").toString(),
        dataType: "json",
        data: null,
        success: function (data) {
          loading.close();
          if (data.rtn == 1 || data.rtn == 2) {//发送验证码成功
            layer.open({
              content: '发送成功！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            timenum = 120;
            if (lock == true) {
              TimeTicker();
            }
            else {
              return false;
            }
          }
          else if (data.rtn == 3) {
            layer.open({
              content: '该手机号已经发送验证码，请稍后再试！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 4) {
            layer.open({
              content: '验证码发送失败，请稍后再试！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
        },
        headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      });
    });

    //修改手机 重置手机号
    $("#resetmobilebym").click(function () {
      var code = $("#txtMsgCode");
      var allNumReg = /^\d+$/;
      loading.open();
      if ($.trim(code.val()) == "") {
        loading.close();
        layer.open({
          content: '请输入短信验证码！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      else {
        if (!allNumReg.test($.trim(code.val())) || $.trim(code.val()).length != 6) {
          loading.close();
          layer.open({
            content: '短信验证码输入有误！'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
          });
          return false;
        }
      }
      var uid = getUIDByJWT().unique_name;
      $.ajax({
        type: "POST",
        url: getAPIURL() + "securitysettings/" + uid + "/resetmobilebym?phone=" + _$realmobile.attr("phoneNum").toString() + "&code=" + code.val().toString(),
        dataType: "json",
        data: null,
        success: function (data) {
          loading.close();
          if (data.rtn == 1) {
            window.location = 'Bound.html?login=true';
            $("#sendCode").val("点击获取").removeAttr("disabled");
          }
          else if (data.rtn == 2) {
            layer.open({
              content: '短信验证码无效！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 3) {
            layer.open({
              content: '手机号码重置失败！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 4) {
            layer.open({
              content: '手机号码错误！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 5) {
            layer.open({
              content: '短信验证码已失效，请重新点击发送！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 6) {
            layer.open({
              content: '短信验证码错误'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
        },
        error:function () {
          loading.close();
        },
        headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      });
    });

    //修改手机 身份证重置手机号
    $("#resetmobilebyc").click(function () {
      loading.open();
      var card = $("#txtIDCard");
      if ($.trim(card.val()) == "") {
        loading.close();
        layer.open({
          content: '请输入身份证号码！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      else {
        if ($.trim(card.val()).length != 18) {
          loading.close();
          layer.open({
            content: '身份证号码位数有误！'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
          });
          return false;
        }
      }

      var uid = getUIDByJWT().unique_name;
      $.ajax({
        type: "POST",
        url: getAPIURL() + "securitysettings/" + uid + "/resetmobilebyc?card=" + card.val().toString() + "&phone=" + _$realmobile.attr("phoneNum").toString(),
        dataType: "json",
        data: null,
        success: function (data) {
          loading.close();
          if (data.rtn == 1) {
            window.location = 'Bound.html?login=true';
          }
          else if (data.rtn == 2) {
            layer.open({
              content: '身份证号码错误！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 3) {
            layer.open({
              content: '手机号码重置失败！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 4) {
            layer.open({
              content: '手机号码重置参数错误！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          else if (data.rtn == 5) {
            layer.open({
              content: '身份证号码输入有误！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
        },
        error:function () {
          loading.close();
        },
        headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      });

    });
  })();
}

var bound1;
$(function () {
  bound1 = new Bound1();

});