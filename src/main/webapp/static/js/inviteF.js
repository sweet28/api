/**
 * Created by Administrator on 2017/8/11 0011.
 */
/*二维码*/
(function () {
  var qrcode = new QRCode(document.getElementById("qrcode"), {
    /*width:150,
     height:150,*/
    correctLevel: QRCode.CorrectLevel.H
  });

//	先判断是否登录
  var account = "";
  loading.open();
  var uid = getUIDByJWT().unique_name;
  if (uid == undefined) {
    return;
  }
  $.ajax({
    type: "GET",
    url: getAPIURL() + "securitysettings/" + uid,
    dataType: "json",
    success: function (data) {
      $(".invitePerson").show();
      $("#inviteName").html(data.account);
      localStorage.setItem("account",JSON.stringify(data.account));
      makeCode(data.account);
      loading.close();
    },
    error: function (data) {
      loading.close();
      if (data.status == 404) {
        layer.open({
          content: "请求资源不存在",
          skin: 'msg',
          time: 2 //2秒后自动关闭
        });
      }
      else {
        layer.open({
          content: JSON.parse(data.responseText).Message,
          skin: 'msg',
          time: 2 //2秒后自动关闭
        });
      }
    },
    headers: {
      "Authorization": "Bearer " + getTOKEN()
    }
  });
  function makeCode(account) {
    //  console.log(baseUrl() + "page/joinUs.html?recommander=" +encodeURI(account));
    qrcode.makeCode(baseUrl() + "page/joinUs.html?recommander=" + encodeURI(account));
  }

  $("#invitingBtn").on("click", function (e) {
    $("#wechatShare").fadeIn();
    e.stopPropagation();
  });
  $("#wechatShare").on("click",function () {
    $(this).hide();
  });
})();