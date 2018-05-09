function LinkBank() {
  var _self = this, _changebank, _bankuser, _citypicker, _openbank, _cardnum, _cardnum2, _recharge, _hash;


  function comptime() {
    var uid = getUIDByJWT().unique_name;
    $.ajax({
      type: "GET",
      url: getAPIURL() + "User/" + uid + "/getwebbanklist",
      dataType: "json",
      data: null,
      success: function (data) {
        var bankList = data.map(function (d) {
          return {'title': d.bname, 'value': d.bcode};
        });
        _changebank.select({
          title: "选择银行",
          items: bankList
        });
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  (function () {
    var bankListImg = {
      "招商银行": "bankzs.png",
      "中国工商银行": "bankgs.png",
      "中国建设银行": "bankjs.png",
      "交通银行": "bankjt.png",
      "中国银行": "bankzg.png",
      "浦发银行": "bankpf.png",
      "平安银行": "bankpa.png",
      "中国民生银行": "bankms.png",
      "中国光大银行": "bankgd.png",
      "兴业银行": "bankxy.png",
      "中信银行": "bankzx.png",
      "中国农业银行": "bankny.png",
      "中国邮政储蓄银行": "bankyz.png",
      "广东发展银行": "bankgf.png",
      "汉口银行": "bankhk.png",
      "华夏银行": "bankhx.png",
      "北京银行": "bankbj.png",
      "上海银行": "banksh.png",
      "浙商银行": "bankzheshang.png"
    };
    _changebank = $("#changebank");
    if (window.location.search.length <= 0) {
      _changebank.val("请选择银行")
    } else {
      var searchArr = window.location.search.slice(1).split("&");
      var bcode = searchArr[0].split("=")[1];
      var bname = searchArr[1].split("=")[1];

      _changebank.val(decodeURI(bname));
      _changebank.css({
        "background": "url(../img/" + bankListImg[decodeURI(bname)] + ") no-repeat left center",
        "background-size": "30px",
        "padding-left": "40px"
      });
    }

    _hash = window.location.hash;//判断用户是否是从提现页面过来
    _changebank.parent().on("click", function () {
      if (_hash == "#withdraw") {
        window.location.href = "../page/addBankCard.html" + _hash;
      } else {
        window.location.href = "../page/addBankCard.html"
      }
    });

    _citypicker = $("#city-picker");
    _openbank = $("#openbank");
    _cardnum = $("#cardnum");
    _citypicker.on("click", function () {
      _cardnum.blur();
      _openbank.blur();
    });
    /* _cardnum2 = $("#cardnum2");*/
    _recharge = $("#recharge");
    /* comptime();*/
    _recharge.on("click", function () {
      var _this = $(this);
      $("#modal").show();
      var uid = getUIDByJWT().unique_name;
      _cardnum.val(_cardnum.val().replace(/[^\d]/g, ''));
      /* _cardnum2.val(_cardnum2.val().replace(/[^\d]/g, ''));*/
      var num1 = _cardnum.val();
      /*  var num2 = _cardnum2.val();*/
      /*获取银行code*/
      // var change = _changebank.attr("data-values");
      var city = _citypicker.val();

      var cityjudge = city.split(" ")[0];


      if (cityjudge == "北京" || cityjudge == "上海" || cityjudge == "天津" || cityjudge == "重庆") {
        city = cityjudge + "市" + "-" + city.split(" ")[1];
      }
      else {
        city = cityjudge + "省" + "-" + city.split(" ")[1];
      }

      if (_changebank.val() == "请选择银行") {
        $("#modal").hide();
        $.toast("未选择银行", "forbidden");
        return false;
      }

      if (num1 == "") {
        $("#modal").hide();
        $.toast("请输入银行卡号", "forbidden");
        return false;
      }
      /*开户行所在地*/
      if (city == "省-undefined") {
        $("#modal").hide();
        $.toast("请选择开户所在地", "forbidden");
        return false;
      }
      var open = _openbank.val();
      if (open == "") {
        $("#modal").hide();
        $.toast("请输入开户行", "forbidden");
        return false;
      }

      if (num1 != "") {
        _this.attr("disabled", "disabled");
        $.ajax({
          type: "POST",
          url: getAPIURL() + "User/" + uid + "/saveuserbank",
          contentType: "application/json",
          dataType: "json",
          data: JSON.stringify({
            "bankcode": bcode.toString(),
            "location": city.toString(),
            "bankName": open.toString(),
            "cardNumber": num1.toString()
          }),
          success: function (data) {
            //成功
            $("#modal").hide();
            _this.removeAttr("disabled");
            if (data.rtn == "0") {
              layer.open({
                content: '操作成功！'
                , skin: 'msg'
                , time: 2 //2秒后自动关闭,
                , success: function () {
                  setTimeout(function () {
                    //判断是否是从提现页面进入
                    if (_hash == "#withdraw") {
                      window.location.href = "../page/withdraw.html";
                    } else {
                      window.location = '../page/mybank_card.html';
                    }

                    //回退一个页面
                    //									location.href = document.referrer;
                  }, 200);
                }
              });
            }
            else if (data.rtn == "2") {
              layer.open({
                content: '银行卡关联失败！'
                , skin: 'msg'
                , time: 2 //2秒后自动关闭,

              });
            }
            else if (data.rtn == "5") {
              layer.open({
                content: '该银行卡已被关联！'
                , skin: 'msg'
                , time: 2 //2秒后自动关闭,

              });
            }
            else if (data.rtn == "6") {
              layer.open({
                content: '银行卡最多只能关联1个！'
                , skin: 'msg'
                , time: 2 //2秒后自动关闭,

              });
              setTimeout(function () {
                window.location = '../page/realName_authentication.html';
              }, 500);
            }
            else if (data.rtn == "7") {
              layer.open({
                content: '请进行实名认证！'
                , skin: 'msg'
                , time: 2 //2秒后自动关闭,
                , success: function () {
                  setTimeout(function () {
                    window.location = '../page/realName_authentication.html';
                  }, 1000);
                }
              });
            }
            else {
              layer.open({
                content: data.Message
                , skin: 'msg'
                , time: 2 //2秒后自动关闭,
              });
            }
          },
          error: function () {
            $("#modal").hide();
            $(this).removeAttr("disabled");
          },
          headers: {
            "Authorization": "Bearer " + getTOKEN()
          }
        });
      } else {
        $("#modal").hide();
        $.toast("输入的银行卡号必须一致", "forbidden");
      }
    });
  })();
}


var linkbank;
$(function () {
  linkbank = new LinkBank();
  var bankCode = "";
  getBankLimit();
  var newBankLimit = JSON.parse(sessionStorage.getItem("newBankLimit"));
  if (window.location.search.indexOf("bankcode") != -1) {
    if (newBankLimit == null) {
      getBankLimit(function () {
        bankCode = decodeURI(request.QueryString("bankcode"));
        $("#limit").html(newBankLimit[bankCode].join("     "));
      });
    }
    else {
      bankCode = decodeURI(request.QueryString("bankcode"));
      $("#limit").html(newBankLimit[bankCode].join("     "));
    }
  }
});
function getBankLimit(callback) {
  $.ajax({
    type: "GET",
    url: getAPIURL() + "NewPay/GetBanksPayInfo",
    dataType: "json",
    data: null,
    success: function (data) {
      if (data.rtn == 1) {
        var list = data.Data;
        var newBankLimit = {};
        for (var i = 0; i < list.length; i++) {
          var item = list[i];
          newBankLimit[item.Code] = ["单笔:" + item.SingleLimit+"W", "单日:" + item.OneDayLimit+"W"];
        }
        sessionStorage.setItem("newBankLimit", JSON.stringify(newBankLimit));
        if(callback){
          callback();
        }
      }
    },
    error: function (data) {
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
    }
  });
}