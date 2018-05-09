(function () {
  //分离开整数和小数部分
  function seprate(number) {
    var arr = number.toString().split(".");
    var arr1 = [];
    var num = arr[0] + ".";
    arr1.push(num);
    var num1 = "";
    if (arr[1] == undefined) {
      num1 = "00"
    } else {
      num1 = arr[1];
    }
    arr1.push(num1);
    return arr1;
  }

  //	先判断是否登录
  var uid = getUIDByJWT().unique_name;
  if (uid == undefined) {
    //return false;
	return true;
  }
  /*v1.2.1充值方式*/
  $.ajax({
    type: "GET",
    url: getAPIURL() + "NewPay/GetCurrentPayMode",
    dataType: "json",
    success: function (data) {
      if(data.rtn==1){
        localStorage.setItem("GetCurrentPayMode",data.Data);
      }
    },
    error:function () {
      localStorage.setItem("GetCurrentPayMode",0);
    },
    headers: {
      "Authorization": "Bearer " + getTOKEN()
    }
  });

  //显示员工类型
  $.ajax({
    type: "GET",
    url: getAPIURL() + "user/" + uid,
    dataType: "json",
    success: function (data) {
      $(".account_type").text(data.nb_type);
    },
    headers: {
      "Authorization": "Bearer " + getTOKEN()
    }
  });
  //显示用户名
  $.ajax({
    type: "GET",
    url: getAPIURL() + "securitysettings/" + uid,
    dataType: "json",
    success: function (data) {
      $("#user_info").text(data.account);
    },
    headers: {
      "Authorization": "Bearer " + getTOKEN()
    }
  });

  //	调用相关金额的接口
  $.ajax({
    type: "GET",
    url: getAPIURL() + "User/" + uid + "/Invest/Info",
    dataType: "json",
    data: null,
    success: function (data) {
      //可用余额
      var balance = data.Balance;
      var balanceArr = seprate(balance);
      $("#balance_num").text(balanceArr[0]);
      $("#balance_dec").text(balanceArr[1]);
      //待收总额
      var waitNum = data.WaitInterest;
      var waitInterestArr = seprate(waitNum);
      $("#waitNum_num").text(waitInterestArr[0]);
      $("#waitNum_dec").text(waitInterestArr[1]);
      //累计收益
      var returnIn = data.ReturnInterest;
      var returnInterestArr = seprate(returnIn);
      $("#returnIn_num").text(returnInterestArr[0]);
      $("#returnIn_dec").text(returnInterestArr[1]);
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
//          layer.open({
//              content: "请登录",
//              skin: 'msg',
//              time: 2,
//              end: function () {
//                  window.location.href = 'login.html';
//              }
//          });
      $("#balance_num").text(0);
      $("#balance_dec").text(".00");
      $("#waitNum_num").text(0);
      $("#waitNum_dec").text(".00");
      $("#returnIn_num").text(0);
      $("#returnIn_dec").text(".00");

    },
    headers: {
      "Authorization": "Bearer " + getTOKEN()
    }
  });
  //点击提现按钮判断是否已经实名认证
  $(".withdraw_btn").click(function () {
    $.ajax({
      type: "GET",
      url: getAPIURL() + "securitysettings/" + uid,
      dataType: "json",
      success: function (data) {
        if (!data.realname) {
          layer.open({
            content: '您还未实名认证，请先去实名认证。'
            , btn: ['去认证', '取消']
            , yes: function (index) {
              window.location.href = "../page/realName_authentication.html";
            }
          });
        } else {
          window.location.href = "../page/withdraw.html";
        }
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  });

  /*点击绑定银行卡按钮判断是否已经实名认证*/
  $("#addBank").click(function () {
    $.ajax({
      type: "GET",
      url: getAPIURL() + "securitysettings/" + uid,
      dataType: "json",
      success: function (data) {
        if (!data.realname) {
          layer.open({
            content: '您还未实名认证，请先去实名认证。'
            , btn: ['去认证', '取消']
            , yes: function (index) {
              window.location.href = "../page/realName_authentication.html";
            }
          });
        } else {
          window.location.href = "../page/mybank_card.html";
        }
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  });
  /*显示奖励优惠张数*/
  $.ajax({
    type: "GET",
    url: getAPIURL() + "User/" + uid + "/getcashcouponlist?pageNumber=" + 1 + "&pageRows=1000",
    dataType: "json",
    data: null,
    success: function (data) {
      var total = 0;
      if (data.Message > 0) {
        for (var i = 0; i < data.list.length; i++) {
          if (data.list[i].CC_STATUS != 1) {
            total = total + 1;
          }
        }
        if (total >= 1) {
          $(".couponNum").html(total + "张")
        }

      }
    },
    headers: {
      "Authorization": "Bearer " + getTOKEN()
    }
  });

  /*点击充值按钮*/
  $("#recharge_btn").on("click", function () {
    //step1:是否实名
    $.ajax({
      type: "GET",
      url: getAPIURL() + "securitysettings/" + uid,
      dataType: "json",
      success: function (data) {
        if (!data.realname) {
          layer.open({
            content: '您还未实名认证，请先去实名认证。'
            , btn: ['去认证', '取消']
            , yes: function (index) {
              window.location.href = "../page/realName_authentication.html";
            }
          });
        } else {
          $.ajax({
            type: "GET",
            url: getAPIURL() + "NewPay/IsNewPaySupportBank",
            dataType: "json",
            success: function (data) {
              if (data.rtn == 1) {
                //step2:判断是否有银行卡
                if (data.Data.cardInfo == null) {
                  layer.open({
                    content: '您未绑定银行卡'
                    , btn: ['绑卡', '取消']
                    , yes: function (index) {
                      window.location.href = "../page/link_bankcard.html"
                    }
                  });
                  return false;
                }
                //step3:是否支持新生支付
                else if (data.Data.isSupport) {
                  //v1.2.1 新生支付页面   支持
                  //v1.2.1根据接口值判断是进入富友还是新生支付
                  if(JSON.parse(localStorage.getItem("GetCurrentPayMode"))==0){
                    window.location.href = "../page/newRecharge.html?" +
                      "isFirstTime="
                      + data.Data.isFirstTime
                      + "&BankName="
                      + data.Data.cardInfo.BankName
                      + "&CardNo="
                      + data.Data.cardInfo.CardNo
                      + "&MobileNo="
                      + data.Data.cardInfo.MobileNo
                      + "&BankCode="
                      + data.Data.cardInfo.BankCode;
                    return false;
                  }
                  else if(JSON.parse(localStorage.getItem("GetCurrentPayMode"))==1){
                    window.location.href = 'charge.html';
                  }
                }
                else {
                  //不支持
                  window.location.href = "../page/newRechargeNotification.html"
                }
              }
            },
            error: function (data) {
              if(data.status==404){
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
        }
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });


  })
})();