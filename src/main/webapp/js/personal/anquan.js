var allNumReg = /^\d+$/;
var allLetReg = /^[A-Za-z]*$/;
function addCard(){
	var old;
	var newpw;
	var newpwd;
	
	swal({   
		title: "原登录密码",   
		type: "input",   
		showCancelButton: true,   
		closeOnConfirm: false,   
		animation: "slide-from-top",   
		inputPlaceholder: "原登录密码" 
	}, function(inputValue){   
		if (inputValue === false) return false;      
		if (inputValue === "") {     
			swal.showInputError("请输入!");     
			return false;
		}
		old = inputValue;
		
		swal({   
			title: "新原登录密码",   
			type: "input",   
			showCancelButton: true,   
			closeOnConfirm: false,   
			animation: "slide-from-top",   
			inputPlaceholder: "密码长度在6-20个字符之间，不能有空格！" 
		}, function(inputValue){   
			if (inputValue === false) return false;      
			if (inputValue === "") {     
				swal.showInputError("请输入!");     
				return false;
			}
			newpw = inputValue;
			var pwdStr = newpw.split(" ");
			if (pwdStr.length != 1) {
				swal.showInputError("密码长度在6-20个字符之间，不能有空格！");  
		        return false;
			}
	        if(newpw.length < 6 || newpw.length > 20){
	        	swal.showInputError("密码长度在6-20个字符之间，不能有空格！");  
		        return false;
	        }else{
	        	if(allNumReg.test(newpw)){
	        		swal.showInputError("密码不能全部为数字！");  
			        return false;
	        	}
	        	if(allLetReg.test(newpw)){
	        		swal.showInputError("密码不能全部为字母！");  
			        return false;
	        	}
	        }
			swal({   
				title: "确认新密码",   
				type: "input",   
				showCancelButton: true,   
				closeOnConfirm: false,   
				animation: "slide-from-top",   
				inputPlaceholder: "确认新密码" 
			}, function(inputValue){   
				if (inputValue === false) return false;      
				if (inputValue === "") {     
					swal.showInputError("请输入!");     
					return false   
				}
				newpwd = inputValue;
				if(newpw != newpwd){
					swal.showInputError("确认密码与新密码不一致！");     
					return false;   
				}
				var tmp = getTimestamp();
			    var rad = getRandom();
			    var ton = getTom();
			    var uid = localStorage.getItem("uid");
			    var str = "uid="+uid+"pg=0"+"ts=100"+"tmp="+tmp+"rad="+rad+"tom="+ton;
				$.ajax({
				      type: "post",
				      url: getAPIURL() + "/user/fens/xgmm",
				      dataType: "json",
				      data: {
				        	"uid":uid,
				        	"new_mm":newpw,
				        	"old_mm":old,
				        	"tmp":tmp,
				            "rad":rad,
				            "tom":ton,
				            "token":commingSoon1(str)
				        },
				      success: function (data) {
				        if (data.status==200) {
				        	swal("修改成功！", "success"); 
				        } else {
				        	swal(data.msg, "error"); 
				        }
				      },
				      headers: {
				        "Authorization": "Bearer " + getTOKEN()
				      }
				    });
			});
		});
		
	});
}

function dx(){
	$.ajax({
		type : "POST",
		url : getAPIURL() + "sms/code/send",
		dataType : "json",
		data : {
			"mobile" : localStorage.getItem("phone")
		},
		success:function(data){
			if (data.status == "200") {
//				swal({ 
//					  title: "验证码发送成功！", 
//					  //text: "2秒后自动关闭。", 
//					  timer: 2000, 
//					  showConfirmButton: false 
//				});
				var time = 120;
				cutdownFlag = false;
				var timer = setInterval(function() {
//					$(".get_code");
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
}

function addAliPay(){
//	var oldPwd = $("#txtOldPwd").val();
//    var newPwd = $("#txtNewPwd").val();
	var oldPwd;
    var newPwd;
    var newpp;
    var code;
	
	swal({   
		title: "原交易密码",   
		type: "input",   
		showCancelButton: true,   
		closeOnConfirm: false,   
		animation: "slide-from-top",   
		inputPlaceholder: "请输入原交易密码如未设置则不需填写" 
	}, function(inputValue){   
		if (inputValue === false) return false;      
		oldPwd = inputValue;
		console.log("111:"+oldPwd);
		dx();
		swal({   
			title: "新交易密码",   
			type: "input",   
			showCancelButton: true,   
			closeOnConfirm: false,   
			animation: "slide-from-top",   
			inputPlaceholder: "新交易密码" 
		}, function(inputValue){   
			if (inputValue === false) return false;      
			if (inputValue === "") {     
				swal.showInputError("请输入!");     
				return false;
			}
			newPwd = inputValue;
			console.log("22222:"+newPwd);
			var pwdStr = newPwd.split(" ");
			if (pwdStr.length != 1) {
				swal.showInputError("密码长度在6-20个字符之间，不能有空格！");  
		        return false;
			}
	        if(newPwd.length < 6 || newPwd.length > 20){
	        	swal.showInputError("密码长度在6-20个字符之间，不能有空格！");  
		        return false;
	        }else{
	        	if(allNumReg.test(newPwd)){
	        		swal.showInputError("密码不能全部为数字！");  
			        return false;
	        	}
	        	if(allLetReg.test(newPwd)){
	        		swal.showInputError("密码不能全部为字母！");  
			        return false;
	        	}
	        }
	        
	        swal({   
				title: "确认新交易密码",     
				type: "input",   
				showCancelButton: true,   
				closeOnConfirm: false,   
				animation: "slide-from-top",   
				inputPlaceholder: "确认新交易密码"  
			},function(inputValue){   
				if (inputValue === false) return false;      
				if (inputValue === "") {     
					swal.showInputError("请输入!");     
					return false   
				}
				newpp = inputValue;
				if(newPwd != newpp){
					swal.showInputError("确认密码与新密码不一致！");     
					return false;   
				}
				
				
			swal({   
				title: "请输入短信验证码",   
				type: "input",   
				showCancelButton: true,   
				closeOnConfirm: false,   
				animation: "slide-from-top",   
				inputPlaceholder: "请输入短信验证码" 
			}, function(inputValue){   
				if (inputValue === false) return false;      
				if (inputValue === "") {     
					swal.showInputError("请输入!");     
					return false   
				}
				code = inputValue;
				
				var tmp = getTimestamp();
			    var rad = getRandom();
			    var ton = getTom();
			    var str = "uid="+localStorage.getItem("uid")+"old_jymm="+oldPwd+"new_jymm="+newPwd;
				$.ajax({
				      type: "post",
				      url: getAPIURL() + "/user/fens/xgjymm",
				      dataType: "json",
				      data:{
				    	  "uid":localStorage.getItem("uid"),
				    	  "old_jymm":oldPwd,
				    	  "new_jymm":newPwd,
				    	  "code":code,
				    	  "tmp":tmp,
				          "rad":rad,
				          "tom":ton,
				          "token":commingSoon1(str)
				      },
				      success: function (data) {
				        if (data.status==200) {
				        	swal("修改成功", "success"); 
				        } else {
				        	swal(data.msg, "error"); 
				        }
				      },
				      headers: {
				        "Authorization": "Bearer " + getTOKEN()
				      }
				    });
			   });
			});
		});
		
	});
}