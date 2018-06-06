var name;
var cardid;
var cardUrl;
$("#file0").change(function() {

	var objUrl = getObjectURL(this.files[0]);
	if (objUrl) {
		var files = $("#file0").get(0).files[0]; // 获取file控件中的内容

		if (files.size < 1024 * 1024 * 2) {
			// 在这里修改图片的地址属性
			$("#img0").attr("src", objUrl);

			var fd = new FormData();
			fd.append("errPic", files);
			var url = getAPIURL() + "pic/upload2";
			$.ajax({
				type : "POST",
				contentType : false, // 必须false才会避开jQuery对 formdata 的默认处理 ,
				// XMLHttpRequest会对 formdata 进行正确的处理
				processData : false, // 必须false才会自动加上正确的Content-Type
				url : url,
				data : fd,
				dataType : "json",
				success : function(data) {
					if (data.error == 0) {
						cardUrl = data.url;
						$.ajax({
							type : "POST",
							dataType : "json",
							url : getAPIURL() + "cpa/facecard",
							data : {
								imgUrl : data.url,
							},
							success : function(data) {
								name = data.cards[0].name;
								cardid = data.cards[0].id_card_number;
								$('#uname').val(name);
								$('#cardnum').val(cardid);
							},
							error : function(data) {
								alert("系统错误");
							}
						});
					}else{
						swal({
				      		  title: data.message,
				      		  icon: "error",
				      		  button: "确定",
			      	    });
					}
				},
				error : function(data) {
					alert.log("请输入jpg和png和jpeg格式图片");
				}
			});
		} else {
			alert("上传图片不能超过2M，请重新上传.");
		}
	}
});

// 建立一個可存取到該file的url
function getObjectURL(file) {
	var url = null;
	// 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
	if (window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}

(function() {
	// var username = $("#username");
	var password = $("#password");
	var phonenum = $("#phonenum");
	var valicode = $("#valicode");
	var recommend_p = $("#recommend_p");
	var cardnum = $("#cardnum");
	var uname = $("#uname");

	var cutdownFlag = true;

	var yqr = window.location.search.substring(7);
	var numb = parseInt(yqr.toString(8), 8);

	var reg11 = /^(\+?86)?(1[34578]\d{9})$/;

	if (reg11.test(numb)) {
		var phoneyq = $("#recommend_p");
		phoneyq.val(numb);
		phoneyq.attr("disabled", "disabled");
	}

	/* 0612 推荐人 */
	$("#referenceTitle").on("click", function() {
		$(this).hide().next().slideDown();
	});

	function reg(step) {
		// 第二步
		var phone_arr = phonenum.val().split(" ");
		var password_arr = password.val().split(" ");
		var reg1 = /^(\+?86)?(1[34578]\d{9})$/;
		var reg2 = /^[\x00-\xff]{6,20}$/;
		var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		// var pattern = /(^\d{15}$)|(^\d{18}$)/;

		if (step == 2) {
			var startTime = 6;
			var endTime = 24;
			var myDate = new Date();
			var nowTime = myDate.getHours();

			if (!(nowTime <= endTime && nowTime >= startTime)) {
				swal({
		      		  title: "开放注册时间为每天" + startTime + "点至" + endTime
						+ "点，请您在注册时间内注册。",
		      		  icon: "info",
		      		  button: "确定",
	      	    });

				return false;
			}
			// 手机号做验证
			if (phonenum.val() == "") {
				swal({
		      		  title: "请输入手机号码",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (phone_arr.length != 1) {
				swal({
		      		  title: "请输入手机号，不能含有空格！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (!reg1.test(phonenum.val())) {
				swal({
		      		  title: "手机号码格式输入有误！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 姓名验证
			if (uname.val() == "") {
				swal({
		      		  title: "请上传身份证图片！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (uname.length != 1) {
				swal({
		      		  title: "请上传身份证图片！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}

			// 身份证号做验证
			if (cardnum.val() == "") {
				swal({
		      		  title: "请上传身份证图片！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (cardnum.length != 1) {
				swal({
		      		  title: "请上传身份证图片！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (!pattern.test(cardnum.val())) {
				swal({
		      		  title: "请上传身份证图片！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 密码验证
			if (password.val() == "") {
				swal({
		      		  title: "密码不能为空！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (password_arr.length != 1) {
				swal({
		      		  title: "密码不能为空！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 0630修改注册手机号正则
			if (!reg2.test(password.val())) {
				swal({
		      		  title: "请输入6-20个字母或符号组合的密码！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			} else {
				if (cutdownFlag) {
					$("#captcha_div").html("").css("margin", " 0.6rem auto 0");
					$.ajax({
						type : "POST",
						url : getAPIURL() + "sms/code/send",
						dataType : "json",
						data : {
							"mobile" : phonenum.val()
						},
						success : function(data) {
							if (data.status == "200") {
								swal({ 
									  title: "验证码发送成功！", 
									  //text: "2秒后自动关闭。", 
									  timer: 2000, 
									  showConfirmButton: false 
								});
								var time = 120;
								cutdownFlag = false;
								var timer = setInterval(function() {
									$(".get_code");
									$("#get_valicode").html(time + "s后重新获取");
									time--;
									if (time < 0) {
										clearInterval(timer);
										cutdownFlag = true;
										$("#get_valicode").html("重新获取");
									}
								}, 1000);
							} else {
								swal({
						      		  title: data.error_msg,
						      		  icon: "error",
						      		  button: "确定",
					      	    });
								return false;
							}
						}
					});

				} else {

				}

			}
		}
		// 第三步
		if (step == 3) {
			var startTime = 6;
			var endTime = 24;
			var myDate = new Date();
			var nowTime = myDate.getHours();

			if (nowTime >= endTime && nowTime <= startTime) {

				swal({
		      		  title: "开放注册时间为每天" + startTime + "点至" + endTime
						+ "点，请您在注册时间内注册。",
		      		  icon: "info",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 手机号做验证
			if (phonenum.val() == "") {
				swal({
		      		  title: "请输入手机号码",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (phone_arr.length != 1) {
				swal({
		      		  title: "请输入手机号，不能含有空格！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (!reg1.test(phonenum.val())) {
				swal({
		      		  title: "手机号码格式输入有误！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 姓名验证
			if (uname.val() == "") {
				swal({
		      		  title: "请上传身份证图片",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (uname.length != 1) {
				swal({
		      		  title: "请上传身份证图片",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 身份证号做验证
			if (cardnum.val() == "") {
				swal({
		      		  title: "请上传身份证图片",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (cardnum.length != 1) {
				swal({
		      		  title: "请上传身份证图片",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (!pattern.test(cardnum.val())) {
				swal({
		      		  title: "请上传身份证图片",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 密码验证
			if (password.val() == "") {
				swal({
		      		  title: "密码不能为空！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (password_arr.length != 1) {
				swal({
		      		  title: "密码不能含有空格！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			// 0630修改注册手机号正则
			if (!reg2.test(password.val())) {
				swal({
		      		  title: "请输入6-20个字母或符号组合的密码！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			if (valicode.val().trim() == "") {
				swal({
		      		  title: "请输入验证码！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			}
			var codeReg = /^[A-Za-z0-9]*$/;
			if (!codeReg.test(valicode.val().replace(/^\s\s*/, '').replace(
					/\s\s*$/, ''))
					|| valicode.val().replace(/^\s\s*/, '').replace(/\s\s*$/,
							'').length != 4) {
				swal({
		      		  title: "请输入验证码！",
		      		  icon: "error",
		      		  button: "确定",
	      	    });
				return false;
			} else {
				$("#modal").show();
				$.ajax({
					type : "post",
					url : getAPIURL() + "user/fens/zc",
					dataType : 'json',
					// contentType: "application/json; charset=utf-8",
					// cache: false,
					// async: false,
					data : {
						"sh" : phonenum.val(),
						"ym" : valicode.val(),
						"xn" : uname.val(),
						"sf_cd" : cardnum.val(),
						"yan_ma" : "reg_code",
						"yqrph" : recommend_p.val(),
						"mn" : password.val(),
						"img" : cardUrl
					},
					success : function(data) {
						if (data.status == "200") {
							$("#modal").hide();
							swal({
					        	  title: '注册成功',
					        	  type: 'success',
					        	  showCancelButton: true,
					        	  confirmButtonText: "确定", 
					        	  cancelButtonText: "取消",
					        	  closeOnConfirm: true, 
					        	  closeOnCancel: true
					        	}).then(function(isConfirm) {
					        	  if (isConfirm === true) {
					        		  location.href = 'index.jsp';
					        	  } else if (isConfirm === false) {
					        		  location.href = 'index.jsp';
					        	  } else {
					        		  location.href = 'index.jsp';
					        	  }
				        	}); 
							
						} else {
							$("#modal").hide();
							swal({
					      		  title: data.msg,
					      		  icon: "error",
					      		  button: "确定",
				      	    });
							return false;
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						$("#modal").hide();
						if (XMLHttpRequest.status == 400) {
							var obj = JSON.parse(XMLHttpRequest.responseText);
							swal({
					      		  title: obj.Message,
					      		  icon: "error",
					      		  button: "确定",
				      	    });
						}
					}
				});
			}
		}
	}
	// 点击获取验证码按钮
	$("#get_valicode").click(function() {
		reg(2);
	});
	// 注册按钮
	$("#register_btn").click(function() {
		reg(3);
	});
	// 切换密码的可见状态
	$(".icon_eye").click(function() {
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