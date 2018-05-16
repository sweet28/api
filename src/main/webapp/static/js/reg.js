(function () {
  //var username = $("#username");
  var password = $("#password");
  var phonenum = $("#phonenum");
  var valicode = $("#valicode");
  var recommend_p = $("#recommend_p");
  var cardnum = $("#cardnum");
  var uname = $("#uname");
  
  var cutdownFlag = true;
  
  var yqr = window.location.search.substring(7);
  var numb = parseInt(yqr.toString(8),8);
  
  var reg11 = /^(\+?86)?(1[34578]\d{9})$/;
  
  if(reg11.test(numb)){
	  var phoneyq = $("#recommend_p");
	  phoneyq.val(numb);
	  phoneyq.attr("disabled","disabled");
  }

  /*0612 推荐人*/
  $("#referenceTitle").on("click", function () {
    $(this).hide().next().slideDown();
  });

  function reg(step) {
    //第二步
    var phone_arr = phonenum.val().split(" ");
    var password_arr = password.val().split(" ");
    var reg1 = /^(\+?86)?(1[34578]\d{9})$/;
    var reg2 = /^[\x00-\xff]{6,20}$/;
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
   // var pattern = /(^\d{15}$)|(^\d{18}$)/; 
    
    if (step == 2) {
    	var startTime = 8;
    	var endTime = 21;
    	var myDate = new Date();
    	var nowTime=myDate.getHours();
    	
    	if(nowTime >= endTime || nowTime <= startTime){

    		layer.open({
                content: "开放注册时间为每天"+startTime+"点至"+endTime+"点，请您在注册时间内注册。",
                btn: '确定'
              });
    		return false;
    	}
        //姓名验证
        if (uname.val() == "") {
          layer.open({
            content: "请输入姓名",
            btn: '确定'
          });
          return false;
        }
        if (uname.length != 1) {
          layer.open({
            content: "请输入姓名，不能含有空格！",
            btn: '确定'
          });
          return false;
        }
      //手机号做验证
      if (phonenum.val() == "") {
        layer.open({
          content: "请输入手机号码",
          btn: '确定'
        });
        return false;
      }
      if (phone_arr.length != 1) {
        layer.open({
          content: "请输入手机号，不能含有空格！",
          btn: '确定'
        });
        return false;
      }
      if (!reg1.test(phonenum.val())) {
        layer.open({
          content: "手机号码格式输入有误！",
          btn: '确定'
        });
        return false;
      }
    //身份证号做验证
      if (cardnum.val() == "") {
        layer.open({
          content: "请输入身份证号",
          btn: '确定'
        });
        return false;
      }
      if (cardnum.length != 1) {
        layer.open({
          content: "请输入身份证号，不能含有空格！",
          btn: '确定'
        });
        return false;
      }
      if (!pattern.test(cardnum.val())) {
          layer.open({
            content: "身份证号格式输入有误！",
            btn: '确定'
          });
          return false;
        }
      //密码验证
      if (password.val() == "") {
        layer.open({
          content: "密码不能为空！",
          btn: '确定'
        });
        return false;
      }
      if (password_arr.length != 1) {
        layer.open({
          content: "密码不能含有空格！",
          btn: '确定'
        });
        return false;
      }
      //0630修改注册手机号正则
      if (!reg2.test(password.val())) {
        layer.open({
          content: "请输入6-20个字母或符号组合的密码！",
          btn: '确定'
        });
        return false;
      }else 
      {
        if (cutdownFlag) {
          $("#captcha_div").html("").css("margin"," 0.6rem auto 0");
//          var opts = {
//            "element": "captcha_div", // 可以是验证码容器id，也可以是HTMLElement
//            //"captchaId": "54cb27f913864c059e486f0be047477f", // 这里填入申请到的验证码id
//            "width": 280, // 验证码组件显示宽度
//            "verifyCallback": function (ret) { // 用户只要有拖动/点击，就会触发这个回调
//              if (ret['value']) { // true:验证通过 false:验证失败
//                // 通过 ret["validate"] 可以获得二次校验数据
//                var validate=ret.validate;
                $.ajax({
                  type: "POST",
                  url: getAPIURL() + "sms/verification_code/send",
                  dataType: "json",
                  //contentType: "application/json",
                  data:{
                    "mobile": phonenum.val(),
                    "code_type":"reg_code"
                  } ,
                  success: function (data) {
                    if (data.error_code == "0") {
                      layer.open({
                        content: '验证码发送成功',
                        skin: 'msg',
                        time: 2, //2秒后自动关闭
                        end: function(){
//                          $("#captcha_div").html("").css("margin"," 0 auto");
//                          $("#register_btn").removeAttr("disabled").removeClass("disabled"); // 用户完成拖动之后再启用提交按钮
                        }
                      });
                      var time = 120;
                      cutdownFlag = false;
                      var timer = setInterval(function () {
                        $(".get_code");
                        $("#get_valicode").html(time + "s后重新获取");
                        time--;
                        if (time < 0) {
                          clearInterval(timer);
                          cutdownFlag = true;
                          $("#get_valicode").html("重新获取");
                        }
                      }, 1000);
                    }else if (data.error_code == "101") {
                        
                          var time = 120;
                          cutdownFlag = false;
                          var timer = setInterval(function () {
                            $(".get_code");
                            $("#get_valicode").html(time + "s后重新获取");
                            time--;
                            if (time < 0) {
                              clearInterval(timer);
                              cutdownFlag = true;
                              $("#get_valicode").html("重新获取");
                            }
                          }, 1000);
                          layer.open({
                              content: '验证码生成成功,允许填入',
                              skin: 'msg',
                              time: 2, //2秒后自动关闭
                              end: function(){
                              	valicode.val(data.error_msg);
                              }
                            });
                        }else{
                    	layer.open({
                            content: data.error_msg,
                            btn: '确定'
                          });
                        return false;
                    }
                  }
                });

//              }
//            }
//          };
          //new NECaptcha(opts);
        } else {

        }

      }
    }
    //第三步
    if (step == 3) {
    	var startTime = 8;
    	var endTime = 21;
    	var myDate = new Date();
    	var nowTime=myDate.getHours();
    	
    	if(nowTime >= endTime || nowTime <= startTime){

    		layer.open({
                content: "开放注册时间为每天"+startTime+"点至"+endTime+"点，请您在注册时间内注册。",
                btn: '确定'
              });
    		return false;
    	}
    	//姓名验证
        if (uname.val() == "") {
          layer.open({
            content: "请输入姓名",
            btn: '确定'
          });
          return false;
        }
        if (uname.length != 1) {
          layer.open({
            content: "请输入姓名，不能含有空格！",
            btn: '确定'
          });
          return false;
        }
      //手机号做验证
      if (phonenum.val() == "") {
        layer.open({
          content: "请输入手机号码",
          btn: '确定'
        });
        return false;
      }
      if (phone_arr.length != 1) {
        layer.open({
          content: "请输入手机号，不能含有空格！",
          btn: '确定'
        });
        return false;
      }
      if (!reg1.test(phonenum.val())) {
        layer.open({
          content: "手机号码格式输入有误！",
          btn: '确定'
        });
        return false;
      }
    //身份证号做验证
      if (cardnum.val() == "") {
        layer.open({
          content: "请输入身份证号",
          btn: '确定'
        });
        return false;
      }
      if (cardnum.length != 1) {
        layer.open({
          content: "请输入身份证号，不能含有空格！",
          btn: '确定'
        });
        return false;
      }
      if (!pattern.test(cardnum.val())) {
          layer.open({
            content: "身份证号格式输入有误！",
            btn: '确定'
          });
          return false;
        }
      //密码验证
      if (password.val() == "") {
        layer.open({
          content: "密码不能为空！",
          btn: '确定'
        });
        return false;
      }
      if (password_arr.length != 1) {
        layer.open({
          content: "密码不能含有空格！",
          btn: '确定'
        });
        return false;
      }
      //0630修改注册手机号正则
      if (!reg2.test(password.val())) {
        layer.open({
          content: "请输入6-20个字母或符号组合的密码！",
          btn: '确定'
        });
        return false;
      }
      if(valicode.val().trim()==""){
        layer.open({
          content: "请输入验证码！",
          btn: '确定'
        });
        return false;
      }
      var codeReg = /^[A-Za-z0-9]*$/;
      if (!codeReg.test(valicode.val().replace(/^\s\s*/, '').replace(/\s\s*$/, '')) || valicode.val().replace(/^\s\s*/, '').replace(/\s\s*$/, '').length != 4) {
        layer.open({
          content: "请输入正确格式的验证码！",
          btn: '确定'
        });
        return false;
      }
      else {
        $("#modal").show();
        $.ajax({
          type: "post",
          url: getAPIURL() + "fenuser/register",
          dataType: 'json',
          //contentType: "application/json; charset=utf-8",
          //cache: false,
          //async: false,
          data: {
            "phone": phonenum.val(),
            "code": valicode.val(),
            "name": uname.val(),
            "cardNumber":cardnum.val(),
            "code_type":"reg_code",
            "refereePhone":recommend_p.val(),
            "pwd":password.val()
          },
          success: function (data) {
	            if (data.status == "200") {
	            	$("#modal").hide();
	            	layer.open({
	            	    content: "注册成功，活动期间赠送的矿机发放中",
	            	    skin: 'msg',
	            	    time: 4, //2秒后自动关闭
	            	    end: function () {
	            	      location.href = '../page/login.html?z';
	            	    }
	            	  });
//		              layer.open({
//		                content: "注册成功",
//		                btn: '确定'
//		              });
//		              return false;
	            }else{
	            	$("#modal").hide();
		              layer.open({
		                content: data.msg,
		                btn: '确定'
		              });
		              return false;
	            }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                $("#modal").hide();
                if(XMLHttpRequest.status == 400) {
                  var obj = JSON.parse(XMLHttpRequest.responseText);
                  layer.open({
                    content:obj.Message,
                    btn:'确定'
                  });
                }
              }
//            //判断是否有推荐人
//            var recommendPerson = $.trim(recommend_p.val());
//            var from = "";
//            if (recommendPerson) {
//              from = "RECOMMENDED";
//            } else {
//              from = "INTERNETMEDIA";
//            }
//            $.ajax({
//              type: "post",
//              url: getAPIURL() + "Register/Register",
//              dataType: 'json',
//              contentType: "application/json; charset=utf-8",
//              data: JSON.stringify({
//                "account": phonenum.val(),
//                "pwd": password.val(),
//                "confirmpwd": password.val(),
//                "mobile": phonenum.val(),
//                "from": from,
//                "recommender": recommendPerson,
//                "validcode": valicode.val()
//              }),
//              success: function (data) {
//                if (data.rtn == -1) {
//                  $("#modal").hide();
//                  layer.open({
//                    content: data.Message,
//                    btn: '确定'
//                  });
//                }
//                if (data.rtn == 1) {
//                  $("#modal").hide();
//                  layer.open({
//                    content: '注册成功',
//                    skin: 'msg',
//                    time: 2 //2秒后自动关闭
//                  });
//                  $("#modal").show();
//                  $.ajax({
//                    type: "POST",
//                    url: getAPIURL() + "Account/Login",
//                    dataType: "json",
//                    contentType: "application/json",
//                    data: JSON.stringify({
//                      username: phonenum.val(),
//                      password: password.val()
//                    }),
//                    success: function (data) {
//                      $("#modal").hide();
//                      localStorage.setItem("token", data.token);
//                      $(".user").text("恭喜" + phonenum.val() + "注册成功!");
//                      $(".register_part2").hide();
//                      $(".login_btn").hide();
//                      $(".register_part3").show();
//                    },
//                    error: function(XMLHttpRequest, textStatus, errorThrown) {
//                      $("#modal").hide();
//                      if(XMLHttpRequest.status == 400) {
//                        var obj = JSON.parse(XMLHttpRequest.responseText);
//                        layer.open({
//                          content:obj.Message,
//                          btn:'确定'
//                        });
//                      }
//                    }
//                  });
//                }
//              }
//            })
//          }
        });
      }
    }
  }
//	点击获取验证码按钮
  $("#get_valicode").click(function () {
    reg(2);
  });
//  注册按钮
  $("#register_btn").click(function () {
    reg(3);
  });
  //切换密码的可见状态
  $(".icon_eye").click(function () {
    var arr = this.src.split("/");
    if (arr[arr.length - 1] == "bkj.png") {
      this.src = "../img/kj.png";
      $(this).prev().attr("type", "text");
    } else {
      this.src = "../img/bkj.png";
      $(this).prev().attr("type", "password");
    }

  });
})();