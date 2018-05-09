(function(){
  $("#txgz_btn").click(function(){
    $(".withdraw_bounce").show();
  })
  $(".close_btn").click(function(){
    $(".withdraw_bounce").hide();
  });
  $(".close_btn1").click(function(){
    $(".withdraw_bounce1").hide();
  });
  //银行卡数组
  var bankListImg={
    "招商银行":"bankzs.png",
    "中国工商银行":"bankgs.png",
    "中国建设银行":"bankjs.png",
    "交通银行":"bankjt.png",
    "中国银行":"bankzg.png",
    "浦发银行":"bankpf.png",
    "平安银行":"bankpa.png",
    "中国民生银行":"bankms.png",
    "中国光大银行":"bankgd.png",
    "兴业银行":"bankxy.png",
    "中信银行":"bankzx.png",
    "中国农业银行":"bankny.png",
    "中国邮政储蓄银行":"bankyz.png",
    "广东发展银行":"bankgf.png",
    "汉口银行":"bankhk.png",
    "华夏银行":"bankhx.png",
    "北京银行":"bankbj.png",
    "上海银行":"banksh.png",
    "浙商银行":"bankzheshang.png"
  };
//	提现界面
  var uid = getUIDByJWT().unique_name;
  //银行卡号
  var bank_name = "";
  var bank_num = "";
  var bdflag = false;
  $.ajax({
    type: "GET",
    url: getAPIURL()+"User/"+uid+"/Withdraw/Info",
    dataType: "json",
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      if(data.rtn==0){
        bank_name = data.BankCardName;
        bank_num = data.BankCardNo;
        var bankNo = "尾号"+data.BankCardNo.slice(-4);
        $(".bank_logo").attr("src","../img/"+bankListImg[bank_name]);
        $(".card_class").text(data.BankCardName);
        $(".card_num").text(bankNo);
        $(".balance_avai").text(data.AVAILABLE_BALANCE);
        $(".no_fee").text("(不追加手续费部分"+data.REMARK+"元)");
      }
      if(data.rtn == 1){
        bdflag = true;
        $(".balance_avai").text(data.AVAILABLE_BALANCE);
        $(".no_fee").text("(不追加手续费部分"+data.REMARK+"元)");
        layer.open({
          content:"提现请先绑定银行卡！",
          btn:'确定',
        });
        $(".add_card").show();
        $(".bank_card").hide();
      }
    },
    headers: {
      "Authorization":"Bearer "+getTOKEN()
    }
  });
  //判断是否绑定了银行卡
//	$.ajax({
//          type: "GET",
//          url: getAPIURL()+"User/"+uid+"/getuserbanklist",
//          dataType: "json",
//          data: null,
//          success: function (data) {
//              var list = data;
//              if(list.length <=0)
//              {
//              	$(".add_card").show();
//              	$(".bank_card").hide();
//              	layer.open({
//						content:"请先绑定银行卡",
//						btn:'确定'
//					});
//              	return false;
//              }
//            	bank_name = list[0].bname;
//            	bank_num = list[0].bnum;
//            	$(".card_class").text(bank_name);
//            	$(".card_num").text(bank_num);
//          },headers: {
//              "Authorization":"Bearer "+getTOKEN()
//          }
//  });
//  //获取可用余额
//  $.ajax({
//		type: "GET",
//		url: getAPIURL() + "User/" + uid + "/Invest/Info",
//		dataType: "json",
//		data: null,
//		success: function(data){
//			console.log(data);
//			var balance = data.Balance;
//			$(".balance_avai").text(balance);
//		},
//		headers: {
//			"Authorization": "Bearer " + getTOKEN()
//		}
//	});
  //手续费变量
  var ymoney = 0;
  //实际到账变量
  var smoney = 0;
  //进入页面让体现金额输入框获得焦点
  $("#txje").focus();
  //根据失去焦点判断手续费
  function fee(){
    var money = $("#txje").val();

    //可用余额
    var av_money = $(".balance_avai").text().replace(/,/g,"");
    if(money<100){
      layer.open({
        content:"提现金额不能小于100",
        skin:"msg",
        time:2,
      });
      return false;
    }else if(av_money-money < 0){
      layer.open({
        content:"提现金额不能超过可提现金额",
        skin:"msg",
        time:2,
      });
      return false;
    }else{
      $('#jymm').attr('readonly',false);
      $.ajax({
        type: "GET",
        url: getAPIURL()+"User/"+uid+"/Withdraw/Fees?amount="+money,
        dataType: "json",
        data: null,
        success: function (data) {
          ymoney=data.WithdrawalsFees.replace(/,/g,"");
          smoney = money - ymoney;
          $("#ymoney").text("手续费："+ymoney+"元");
          $("#smoney").text(smoney);
          //给提现弹出层赋值
          $(".shouxu_fee").text(ymoney);
          $(".return_fee").text(smoney);
        },headers: {
          "Authorization":"Bearer "+getTOKEN()
        }
      });
      return true;
    }
  }
  //提交步骤
  function submit(){
    $.ajax({
      type: "POST",
      url: getAPIURL()+"User/"+uid+"/webWithdraw",
      data: JSON.stringify({"bankname":bank_name,"bankno":bank_num,"password":$("#jymm").val(),"amount":$("#txje").val()}),
      dataType: 'json',
      contentType: "application/json",
      cache: false,
      async: false,
      success: function (data){
        if(data.rtn=="0")
        {
          layer.open({
            content: '申请提现成功'
            ,skin: 'msg'
            ,time: 2 //2秒后自动关闭
            ,end:function(){
              window.location.href = "withdraw_record.html";
            }
          });
        }
        else
        {
          layer.open({
            content:data.Message,
            btn:'确定'
          });

        }
      },
      error: function(data){
        layer.open({
          content: '登录已过期，请重新登录'
          ,skin: 'msg'
          ,time: 2 //2秒后自动关闭
          ,end:function(){
            window.location.href = "login.html";
          }
        });
      },
      headers: {
        "Authorization":"Bearer "+getTOKEN()
      }

    });
  }
  $("#txje").blur(function(){
    fee();
  });
  //点击提现按钮时
  $(".withdraw_btn").click(function(){
    if(bdflag){
      layer.open({
        content:"请先绑定银行卡",
        skin:"msg",
        time:2,
      });
      return false;
    }
    if($("#txje").val() ==""){
      layer.open({
        content:"请输入提现金额！",
        skin: 'msg',
        time: 2 //2秒后自动关闭
      });
      return false;
    }
    if($("#jymm").val() == ""){
      layer.open({
        content:"请输入交易密码！",
        skin: 'msg',
        time: 2 //2秒后自动关闭
      });
      return false;
    }
    if(!fee()){
      return false;
    }
    //点击提现按钮时弹出提现提示
    $(".withdraw_bounce1").show();
    $(".btn_qd").click(function(){
      $(".withdraw_bounce1").hide();
      submit();
    });
    $(".btn_cancel").click(function(){
      $(".withdraw_bounce1").hide();
      return;
    });
  });
})();