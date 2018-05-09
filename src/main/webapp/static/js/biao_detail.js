(function () {
  var _self = this,
    _$titleinfo, _$detailnum, _$coninfo, _$earnings,
    _$prodetail, _$inverst, BO_PERIODS, BO_RATES, BO_PAYMENT_WAY, _$inputnum, _$type, LAST_CANCOUNT;
  var _title,//标题
    bo_rates,//预期年化率
    bo_start_money,//起投金额
    bo_total_money,//总金额
    lockup_period,//锁定期
    identification_level,//认证级别
    total_invest_record_num,//投资记录人数
    bo_remain_money,//剩余金额
    bo_deadline,//截止日期
    bo_lock_dates,//锁定时间
    bo_interest_dates,//开始计息日期
    bo_recognizor,//审核认证单位
    bo_text_value,//要投资金额
    bo_progress;//进度

  var str_rates,//预期年化率html
    str_progress,//进度html
    str_time_html,//日期html
    str_identification_level,//认证级别html
    str_bo_recognizor,//审核认证单位htlm
    str_bo_invest_num,//投资记录人数
    str_total_money,//总金额html
    recordStr,//投资记录
    str_start_money;//起投金额html
  var boneID = window.location.search;
  var v = parseUrl();
  var balance;//可用余额
  var uid = getTOKEN();//token
  var _$uid;//uid
  var isPull = false;//是否满标
  if (uid == null) {
    //未登录
    $('#pi_login').text('登录');
  } else {
    $('#pi_login').text('');
  }
  $('#pi_login').on('click', function () {
    if (uid == null) {
      window.location.href = 'login.html';
    }
  });

  var r = /^\+?[1-9][0-9]*$/;//正整数
  _$detailnum = v['bono'];
  var _$money_val = v['money_val'];
  bo_text_value = $('#_invest_money').val(_$money_val);
  //_$detailnum = boneID.substr(6,boneID.length-6);//标ID

  function comptime() {
    $.ajax({
      type: "GET",
      url: getAPIURL() + "Invest/" + _$detailnum,
      dataType: "json",
      data: null,
      success: function (data) {
        //zq add转让标和新手标不现实红包字段
        if(data.BO_TYPE=="9NEW"||data.BO_TYPE=="TRS"){
          $(".avai_package").hide();
        }
        var bo_number = data.BO_ID;//标 id
        var detail_id = data.BO_NO;
        //缓存到localStorage
        window.localStorage.setItem('detail_id', detail_id);
        //title 部分
        _title = data.BO_TITLE;
        setTitle("#pi_common_header", {'title':_title});
        $('#pi_common_header').find('span').text(_title);

        //   rates
        if (data.BO_RATES) {
          _rates = Convert(data.BO_RATES);
          str_rates = '<p>预期年化率</p><p><span>' + _rates + '</span><span>%</span></p>';
          $('#pi_wrap .pi_year_rate').append(str_rates);
        }

        //  体验标样式
        if (data.BO_TYPE == 'DAYBID') {
          var tiyanStr = '<div class="tiyan_btn">' + '使用体验金投标' + '</div>';
          $('#pi_wrap .pi_invest_money').html(tiyanStr);
          if (data.progress == 100) {
            $('.pi_invest_money').css({border: '0'});
            $('.tiyan_btn').css({background: 'rgb(196, 194, 194)'});
            $('.tiyan_btn').text('已满标');
          }
          //体验投资按钮
          $('.pi_invest_money .tiyan_btn').on('click', function () {

            if (data.progress == 100) {
              layer.open({
                content: '已满标',
                time: 2,
                skin: 'msg'
              })
              return false;
            }
            window.location.href = 'invest_fixed_investment_yhj.html?bo_type=0';
          });
        }

        //未发布状态标
        if (data.short_time != '') {
          var openning_time = data.short_time;
          var openning_time_Str = '<div class="tiyan_btn">' + openning_time + '开标</div>';
          $('#pi_wrap .pi_invest_money').html(openning_time_Str);

          $('.pi_invest_money .tiyan_btn').on('click', function () {
            layer.open({
              content: '还未开标',
              time: 2,
              skin: 'msg'
            })
          })
        }


        //   起投金额 锁定期
        if (data.BO_EACH_AMOUNT && data.BO_PERIODS) {
          bo_start_money = data.BO_EACH_AMOUNT;//起投金额
          lockup_period = data.BO_PERIODS;//锁定期

          if(data.BO_TYPE == 'DAYBID'){
            var _$dataStr = data.BO_PERIODS + '天';
          }else {
            var _$dataStr = data.BO_PERIODS + '个月';
          }
          str_start_money = '<li><p>起投金额</p><p>' + bo_start_money + '元</p></li><li></li><li class="lock_time"><p>锁定期</p><p>' + _$dataStr + '</p></li>';
          $('#pi_header .pi_money ul').append(str_start_money);
        }
        // 进度条
        if (data.progress >= 0) {
          bo_progress = data.progress + '%';
          $('#pi_header .pi_percent .pi_percent_num').text(bo_progress);
          $('#pi_header .pi_percent .pi_percent_line_inner').animate({'width': bo_progress}, 1000);
        }

        //输入框起投金额提示
        if (data.BO_EACH_AMOUNT) {
          var placeholderStr = data.BO_EACH_AMOUNT + '元起投，请输入金额';
          $('#_invest_money').css({fontSize: '0.26rem'}).attr('placeholder', placeholderStr);
        }

        //    总额
        if (data.BO_AMOUNT) {
          bo_total_money = data.BO_AMOUNT;
          bo_remain_money = data.BO_CANCOUNT * data.BO_EACH_AMOUNT;

          if (bo_remain_money == 0) {
            isPull = true;
            $('#pi_commit_btn').css({background: '#c4c2c2'});
            $('#_invest_money').attr('disabled', 'disabled');
            $('#_invest_money').attr('placeholder', '已满标');
          } else {
            $('#pi_commit_btn').css({background: '#ff9413'});
            $('#_invest_money').removeAttr('disabled');
            isPull = false;
          }
          str_total_money = '<p class="clearfix"><span>总金额 ' + bo_total_money + '元</span><span>剩余金额 ' + bo_remain_money + '元</span></p>'
          $('#pi_header .pi_total_money').append(str_total_money);
        }

        // date 日期
        if (data.ENDDATE) {
          bo_deadline = data.ENDDATE;
          var StartInterestDATE = data.StartInterestDATE;
          var LockEndDATE = data.LockEndDATE;
          str_time_html = '<div class="cutdiv">' + bo_deadline + '</div><div class="cutdiv" style="text-align: center;text-indent: -0.5rem;">' + StartInterestDATE + '</div><div class="cutdiv" style="text-align: center;text-indent: 0.5rem;">' + LockEndDATE + '</div><div style="text-align: right" class="cutdiv">' + '1-3个工作日</div>'
          $('#pi_wrap .pi_progress .pi_bo_time').append(str_time_html);
        }
        //    审核时间
        //    if (data.BO_TIME) {
        //        var identify_time = data.BO_TIME.substr(0,10);
        //        $('#pi_wrap .pi_audit_information  .td_time').text(identify_time);
        //    }
        //  审核认证
        if (data.BO_TYPE == '9HUI' || data.BO_TYPE == '9NEW') {
          var identify_time = data.BO_TIME;
          bo_recognizor = data.BO_ABOUTME;
          str_bo_recognizor = '<tr onclick=' + 'window.location.href="check_identification.html?bo_time=' + identify_time + '&bo_id=' + _$detailnum + '"' + '>' + '<td class="bottom_border"><i class="pi_icon pi_icon_identi"></i><span class="font_bold">审核认证</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + '' + '<img src="../img1/zhankai.png"></td></tr>';
          $('#pi_wrap .pi_list table').prepend(str_bo_recognizor);
        }
        //    债券明细
        if (data.BO_TYPE == 'AIB') {
          var identify_time = data.BO_TIME;
          bo_recognizor = data.BO_ABOUTME;
          str_bo_recognizor = '<tr onclick=' + 'window.location.href="bond_list.html?bo_time=' + identify_time + '&bo_id=' + _$detailnum + '"' + '>' + '<td class="bottom_border"><i class="pi_icon pi_icon_identi"></i><span class="font_bold">债券明细</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + '' + '<img src="../img1/zhankai.png"></td></tr>';
          $('#pi_wrap .pi_list table').prepend(str_bo_recognizor);
        }

        //  认证级别 项目介绍
        if (data.EU_RATINGS != '') {
          identification_level = data.EU_RATINGS + '级认证保障';
        } else {
          identification_level = '';
        }
        if (data.BO_TYPE == '9NEW') {
          str_identification_level = '<tr onclick=' + 'window.location.href="production_introduce.html?bono=' + detail_id + '"><td class="bottom_border"><i class="pi_icon pi_icon_intro"></i><span class="font_bold">项目介绍</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + identification_level + '<img src="../img1/zhankai.png"></td></tr>';
        } else if (data.BO_TYPE == '9HUI') {
          str_identification_level = '<tr onclick=' + 'window.location.href="production_introduce.html?bono=' + detail_id + '"><td class="bottom_border"><i class="pi_icon pi_icon_intro"></i><span class="font_bold">项目介绍</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + identification_level + '<img src="../img1/zhankai.png"></td></tr>';
        } else if (data.BO_TYPE == 'AIB') {
          str_identification_level = '<tr onclick=' + 'window.location.href="production_introduce.html?bono=' + detail_id + '"><td class="bottom_border"><i class="pi_icon pi_icon_intro"></i><span class="font_bold">项目介绍</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + identification_level + '<img src="../img1/zhankai.png"></td></tr>';
        } else if (data.BO_TYPE == 'DAYBID') {
          str_identification_level = '<tr onclick=' + 'window.location.href="project_introduction.html"><td class="bottom_border"><i class="pi_icon pi_icon_intro"></i><span class="font_bold">项目介绍</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + identification_level + '<img src="../img1/zhankai.png"></td></tr>';
        }
        else {
          str_identification_level = '<tr onclick=' + 'window.location.href="changebiao_project.html?bono=' + detail_id + '"><td class="bottom_border"><i class="pi_icon pi_icon_intro"></i><span class="font_bold">项目介绍</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + identification_level + '<img src="../img1/zhankai.png"></td></tr>';
        }
        $('#pi_wrap .pi_list table').prepend(str_identification_level);

        //  投资记录
        if (data.InvestNum == '') {
          recordStr = '';
        } else {
          total_invest_record_num = data.InvestNum;
          recordStr = total_invest_record_num + '人已投';
        }
        str_bo_invest_num = '<tr onclick=' + 'window.location.href="invest_record.html?id=' + bo_number + '"><td class="bottom_border"><i class="pi_icon pi_icon_invest"></i><span class="font_bold">投资记录</span></td><td class="bottom_border" style="text-align: right;font-size: 0.26rem;color: #c1c1c1;">' + recordStr + '<img src="../img1/zhankai.png"></td></tr>';
        $('#pi_wrap .pi_list table').append(str_bo_invest_num);

      }, error: function (resp) {
        if (resp) {
          layer.open({
            content: '出错了哦～',
            time: 2,
            skin: 'msg'
          })
        }
      }
    });

  }

  comptime();

  var request = {
    QueryString: function (val) {
      var uri = window.location.search;
      var re = new RegExp("" + val + "=([^&?]*)", "ig");
      return ((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1)) : null);
    }
  };

  //区分标型
  function switchType(type) {
    switch (type) {
      case '9NEW':
        //干点什么
        break;
      case '9HUI':
        break;
      case 'AIB':
        break;
      case 'TRS':
        break;
      default :
        '9NEW'
    }
  }


  //为数字添加千分位分隔符
  function Convert(money) {
    var s = money; //获取小数型数据
    s += "";
    if (s.indexOf(".") == -1) s += ".0"; //如果没有小数点，在后面补个小数点和0
    if (/\.\d$/.test(s)) s += "0";   //正则判断
    while (/\d{4}(\.|,)/.test(s))  //符合条件则进行替换
      //s = s.replace(/(\d)(\d{3}(\.|,))/, "$1,$2"); //每隔3位添加一个
      s = s.replace(".00", "");
    return s;
  }
  //先判断用户是否登录来显示红包可用个数
  var token_new = localStorage.getItem("token");
  if(!token_new){
    $(".avai_package").hide();
  }else{
    /*显示奖励优惠张数*/
    $.ajax({
      type: "GET",
      url: getAPIURL()+"User/"+getUIDByJWT().unique_name+"/getcashcouponlist?pageNumber=" + 1 + "&pageRows=1000",
      dataType: "json",
      data: null,
      success: function (data) {
        console.log(data);
        var total=0;
        if(data.Message>0){
          for(var i = 0; i < data.list.length;i++){
            if(data.list[i].CC_STATUS != 1){
              total=total+1;
            }
          }
          if(total>=1){
            $(".couponNum").html(total+"个红包");
          }else{
            $(".avai_package").hide();
          }

        }else{
          $(".avai_package").hide();
        }
      },
      headers:{
        "Authorization":"Bearer "+getTOKEN()
      }
    });
  }
  //        截取小数点后两位
  function cutOutNum (num) {
    var result;
    var s = num.toString();
    var a = s.indexOf(".");
//            var b = s.charAt(".");
    if (a != -1) {
      result = s.substr(0,a+3);
      return result;
    }else {
      return s;
    }
  }
  //    点击立即投资（非体验）
  //给立即投资按钮一个标识值
  var investflag = true;
  $(".close_btn").click(function(){
    $(".investinput_wrap").hide();
    investflag = true;
    $(".invest_val").val("");
    $(".earning_val").html("");
  });
  //给理财金额一个清空事件
  $(".clear_btn").click(function(){
    $(".invest_val").val("");
    $(".earning_val").html("");
  });
  //给理财金额输入框一个事件，计算预期收益
  $(".invest_val").keyup(function(){
    var inputval = this.value;
    if(inputval < 100){
      $(".earning_val").html("");
      return;
    }
    var endval = cutOutNum(((inputval*_rates/100)*lockup_period/12));
    $(".earning_val").html(endval);
  });
  $('#pi_commit_btn').on('click', function () {
    if (isPull) {
      layer.open({
        content: '该标已满',
        time: 2,
        skin: 'msg'
      });
      return false;
    }
//  	第一次点
    if(investflag){
      $(".investinput_wrap").show();
      investflag = false;
      return;
    }
    _$uid = getUIDByJWT().unique_name;
    //if (bo_remain_money >0 && bo_text_value != ''){
    //};
    if (_$uid != undefined) {
      goComfirmInvest(_$uid);
    }
  });

  function goComfirmInvest(_$uid) {
    uid = getTOKEN();
    bo_text_value = $('#_invest_money').val();
    if (uid == null) {
      //window.location.href = 'login.html';
      layer.open({
        content: "请登录后操作",
        btn: '确定',
        yes: function () {
          window.location.href = 'login.html';
        }
      });
    }
    else {
      // 判断用户是否认证
      $.ajax({
        type: "GET",
        url: getAPIURL() + "securitysettings/" + _$uid,
        dataType: "json",
        data: null,
        success: function (data) {
          if (data.cid == '未认证') {
            layer.open({
              content: "您还未认证，请去实名认证！",
              time: 2,
              skin: 'msg',
              end: function () {
                window.location.href = 'realName_authentication.html'+window.location.search+'#promptly_invest.html';
              }
            });
          } else {
            //获取可用余额
            $.ajax({
              type: "GET",
              url: getAPIURL() + "User/" + _$uid + "/Invest/Info",
              dataType: "json",
              data: null,
              success: function (data) {
                balance = data.Balance;

                if (r.test(bo_text_value / bo_start_money)) {
                  if (Number(bo_text_value) > Number(balance)) {
                    layer.open({
                      content: '可用余额不足',
                      time: 2,
                      skin: 'msg',
                      end: function () {
                        getCurrentPayModeFn(getTOKEN());
                      }
                    })
                  } else if (Number(bo_text_value) > Number(bo_remain_money)) {
                    if (bo_remain_money == 0) {
                      layer.open({
                        content: '已满标！',
                        time: 2,
                        skin: 'msg'
                      });
                      return false;
                    } else {
                      layer.open({
                        content: '投资金额不能超过剩余金额！',
                        time: 2,
                        skin: 'msg'
                      })
                    }
                  } else {
                    window.location.href = 'invest_fixed_investment_yhj.html?money_val=' + bo_text_value;
                  }
                  //bo_text_value > bo_remain_money ? layer.open({content:'投资金额不能超过剩余金额！', time:2}) : window.location.href = 'invest_fixed_investment_yhj.html?money_val=' + bo_text_value;
                } else if (bo_text_value == '') {
                  layer.open({
                    content: '投资金额不能为空！',
                    time: 2,
                    skin: 'msg'
                  })
                } else {
                  layer.open({
                    content: '投资金额必须为' + bo_start_money + '的倍数!',
                    time: 2,
                    skin: 'msg'
                  })
                }
              },
              error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.open({
                  content: "您还未登录，请登录",
                  btn: '确定',
                  yes: function () {
                    window.location.href = 'login.html';
                  }
                });
              },
              headers: {
                "Authorization": "Bearer " + getTOKEN()
              }
            });
          }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
          layer.open({
            content: "请登录",
            time: 2,
            skin: 'msg',
            end: function () {
              window.location.href = 'login.html';
            }
          });
        },
        headers: {
          "Authorization": "Bearer " + getTOKEN()
        }
      });

    }
  }


  //    点击计算器
  $('#jump_calculator_btn').on('click', function () {
    bo_text_value = $('#_invest_money').val();
    console.log(bo_text_value);
    //if (r.test(bo_text_value/100)) {
    window.location.href = 'invest_calculator.html?money_val=' + bo_text_value + '&rates_val=' + _rates + '&lockup_period=' + lockup_period;
    //}else {
    //    layer.open({
    //        content:'投资金额必须为100的倍数!',
    //        time:2
    //    })
    //}
  });

  //dealEvent();

  /*新生支付模式*/
  function getCurrentPayModeFn(token) {
    $.ajax({
      type: "GET",
      url: getAPIURL() + "NewPay/GetCurrentPayMode",
      dataType: "json",
      success: function (data) {
        if(data.rtn==1){
          if(data.Data==0){
            newPayFn(getTOKEN());
            return false;
          }
          else if(data.Data==1){
            /*富友*/
            window.location.href = 'charge.html';
          }
        }
      },
      error:function () {
        newPayFn(getTOKEN());
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
  /*是否支持新生支付*/
  function newPayFn(token) {
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
        "Authorization": "Bearer " + token
      }
    });
  }
}());