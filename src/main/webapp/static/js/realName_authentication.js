/**
 * Created by tim on 2016/6/20.
 */
function Verified() {
  var _self = this, _$name, _$cardid, _$cardid_confirm, _hash;

  _hash = window.location.hash;

  _next = function (_this) {
    if(!_this.hasClass("disabled")){
      _this.addClass("disabled");
      loading.open();
      if ($.trim(_$name.val()) == "") {
        loading.close();
        _this.removeClass("disabled");
        layer.open({
          content: '请输入您的真实姓名！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      else {
        if ($.trim(_$name.val()).length < 2 || $.trim(_$name.val()).length > 10) {
          loading.close();
          _this.removeClass("disabled");
          layer.open({
            content: '真实姓名长度有误！'
            , skin: 'msg'
            , time: 2 //2秒后自动关闭
          });
          return false;
        }
        else {
          var nameRule = /^[\u4E00-\u9FA5]+$/;
          if (!nameRule.test($.trim(_$name.val()))) {
            loading.close();
            _this.removeClass("disabled");
            layer.open({
              content: '姓名含有非法字符！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
            return false;
          }
        }
      }
      if ($.trim(_$cardid.val()) == "") {
        loading.close();
        _this.removeClass("disabled");
        layer.open({
          content: '请输入正确的身份证号！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      else if ($.trim(_$cardid_confirm.val()) == "") {
        loading.close();
        _this.removeClass("disabled");
        layer.open({
          content: '请再次输入身份证号！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      //  var cardRule = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
      if ($.trim(_$cardid.val()).length != 18 || $.trim(_$cardid_confirm.val()).length != 18) {
        loading.close();
        _this.removeClass("disabled");
        layer.open({
          content: '身份证号码位数有误！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      else if ($.trim(_$cardid.val()) != $.trim(_$cardid_confirm.val())) {
        loading.close();
        _this.removeClass("disabled");
        layer.open({
          content: '身份证号确认有误！'
          , skin: 'msg'
          , time: 2 //2秒后自动关闭
        });
        return false;
      }
      var uid = getUIDByJWT().unique_name;
      $.ajax({
        type: "Post",
        url: getAPIURL() + "securitysettings/" + uid + "/bindidcard?realname=" + _$name.val().toString() + "&idcard=" + _$cardid.val().toString(),
        data: null,
        dataType: 'json',
        timeout:6000,
        cache: false,
        async: false,
        success: function (data) {
          loading.close();
          _this.removeClass("disabled");
          if (data.rtn == "1") {
            layer.open({
              content: '实名认证成功！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
              , end: function () {
                setTimeout(function () {
                  if (_hash == "#promptly_invest.html") {
                    // window.location = "../page/" + _hash.slice(1);
                    window.location = "../page/" +_hash.slice(1)+ window.location.search;
                  }else if(_hash == "#safe_center.html"){
                    window.location = "../page/" + _hash.slice(1);
                  }
                  else {
                    window.location = "../page/personal.html";
                  }
                }, 2000);
              }
            });
          }
          if (data.rtn == "2") {
            layer.open({
              content: '您输入的名字与身份证号不匹配,请核对您的认证信！',
              skin: 'msg',
              time: 2 //2秒后自动关闭
            });
          }
          if (data.rtn == "3") {
            layer.open({
              content: '该身份证号码已有认证！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          if (data.rtn == "4") {
            layer.open({
              content: '身份证号码输入有误！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          if (data.rtn == "5") {
            layer.open({
              content: '实名认证上传成功，等待审核！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          if (data.rtn == "6") {
            layer.open({
              content: '您输入的身份证号有误,请核对您的认证信息！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
          if (data.rtn == "7") {
            layer.open({
              content: '实名认证次数已到上限，请明天再试！'
              , skin: 'msg'
              , time: 2 //2秒后自动关闭
            });
          }
        },
        error : function(XMLHttpRequest,textStatus){
          loading.close();
          _this.removeClass("disabled");
          if(textStatus=='timeout'){
            layer.open({
              content:"操作超时",
              btn:'确定'
            });
          }
          else{
            if(XMLHttpRequest.status == 400) {
              var obj = JSON.parse(XMLHttpRequest.responseText);
              layer.open({
                content:obj.Message,
                btn:'确定'
              });
            }
          }
        },
        headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      })
    }
  };

  this.next = _next;

  (function () {
    _$name = $("#name");
    _$cardid = $("#cardid");
    _$cardid_confirm = $("#cardid_confirm");
  })();
}

var verified;
$(function () {
  verified = new Verified();
});