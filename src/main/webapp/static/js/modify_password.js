/**
 * Created by tim on 2016/6/20.
 */
function Safechange(hash) {
  var _self = this, _$txtOldPwd, _$txtNewPwd, _$txtReNewPwd;
  this.hash=hash;
  _next = function () {
    var allNumReg = /^\d+$/;
    var allLetReg = /^[A-Za-z]*$/;
    var txt = "";
    var type = "";
    /*_hash==1 修改登录密码  _hash==2 修改交易密码*/
    //先做验证
    if (hash == "1") {
      txt = "登录";
    }
    else if (hash == "2") {
      txt = "交易";
    }
    loading.open();
    if ($.trim(_$txtOldPwd.val()) == "" && hash == "1") {
      loading.close();
      layer.open({
        content: '请输入原' + txt + '密码',
        btn: '我知道了'
      });
      return false;
    }
    if ($.trim(_$txtNewPwd.val()) == "") {
      loading.close();
      layer.open({
        content: '请输入新的' + txt + '密码！'
        , btn: '我知道了'
      });
      return false;
    }
    else {
      var pwdStr = $.trim(_$txtNewPwd.val()).split(" ");
      if (pwdStr.length != 1) {
        loading.close();
        layer.open({
          content: '密码长度在6-20个字符之间，不能有空格！'
          , btn: '我知道了'
        });
        return false;
      }
      else {
        if ($.trim(_$txtNewPwd.val()).length < 6 || $.trim(_$txtNewPwd.val()).length > 20) {
          loading.close();
          layer.open({
            content: '密码长度在6-20个字符之间，不能有空格！'
            , btn: '我知道了'
          });
          return false;
        }
        else {
          if (allNumReg.test($.trim(_$txtNewPwd.val()))) {
            loading.close();
            layer.open({
              content: '密码不能全部为数字！'
              , btn: '我知道了'
            });
            return false;
          }
          if (allLetReg.test($.trim(_$txtNewPwd.val()))) {
            loading.close();
            layer.open({
              content: '密码不能全部为字母！'
              , btn: '我知道了'
            });
            return false;
          }
        }
      }
    }
    if ($.trim(_$txtReNewPwd.val()) != $.trim(_$txtNewPwd.val())) {
      loading.close();
      layer.open({
        content: '确认密码与新密码不一致！'
        , btn: '我知道了'
      });
      return false;
    }
    
//    var uid = getUIDByJWT().unique_name;
    var uid =  localStorage.getItem("uid");
    if (hash == "1") {
      var oldPwd = $("#txtOldPwd").val();
      var newPwd = $("#txtNewPwd").val();
      console.log(getAPIURL() + "fenuser/updatePwd");
      $.ajax({
        type: "POST",
        url: getAPIURL() + "fenuser/updatePwd",
        data: {
        	fensUserId:uid,
        	newPwd:newPwd,
        	OldPwd:oldPwd
        },
        dataType: 'json',
        success: function (data) {
          loading.close();
          if (data.status == 200) {
            layer.open({
              content: '登录密码修改成功!'
              , btn: ['重新登录','取消']
              ,yes:function () {
                window.location = '../page/login.html?login=true';
              }
              ,no:function () {
                window.location = '../page/safe_center.html';

              }
            });
          }else if (data.status == 500) {
              alert(data.msg);
           }
        },
        error: function (data) {
          loading.close();
          if (data.status == 500) {
            layer.open({
              content: data.msg,
              skin: 'msg',
              time: 5 //2秒后自动关闭
            });
          }
        },
        headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      })
    }
    else if (hash == "2") {
      var oldPwd = $("#txtOldPwd").val();
      var newPwd = $("#txtNewPwd").val();
      $.ajax({
        type: "POST",
        url: getAPIURL() + "fenuser/updateJiaoYi",
        data: {
        	fensUserId: uid,
        	oldCapitalPwd:oldPwd,
        	newCapitalPwd:newPwd
        },
        dataType: 'json',
        success: function (data) {
          if (data.status == 200) {
//            layer.open({
//              content: '交易密码修改成功!'
//              , skin: 'msg'
//              , time: 2 //2秒后自动关闭
//              ,success:function () {
//                setTimeout(function () {
//                  window.location.href='../page/safe_center.html';
//                },3000)
//              }
//            });
        	  alert("修改成功");
        	window.location.href='../page/safe_center.html';
            $("#txtOldPwd").val("");
            $("#txtNewPwd").val("");
            $("#txtReNewPwd").val("");

          }else if (data.status == 500) {
              alert(data.msg);
              window.location.reload();
          }
        }, headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      })
    }
  };
  this.next = _next;
  (function () {
    _$txtOldPwd = $("#txtOldPwd");
    _$txtNewPwd = $("#txtNewPwd");
    _$txtReNewPwd = $("#txtReNewPwd");
  })();
}

var safechange;
$(function () {
  safechange = new Safechange(window.location.hash.slice(1));
});