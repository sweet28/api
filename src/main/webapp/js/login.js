(function(){
  var user = $("#phone");
  var pwd = $("#pwd");
  function loginverify(){
    var tmp = getTimestamp();
    var rad = getRandom();
    var ton = getTom();
    var str = "sh="+$.trim(user.val())+"mn="+pwd.val()+"tmp="+tmp+"rad="+rad+"tom="+ton;
    if(user.val()==""||user.val().replace(/\s/g,"")==""){
      swal({
		  title: "请输入手机号(用户名)!",
		  // text: "请输入手机号(用户名)!",
		  icon: "error",
		  button: "确定",
	  });
      return;
    }
    if(pwd.val()==""){
      swal({
		  title: "请输入密码!",
		  icon: "error",
		  button: "确定",
	  });
      return;
    }
    
    $.ajax({
      type: "POST",
      url: getAPIURL() + "user/fens/dl",
      dataType: "json",
      //contentType: "application/json",
      data:{
        "sh":$.trim(user.val()),
        "mn":pwd.val(),
        "tmp":tmp,
        "rad":rad,
        "tom":ton,
        "token":commingSoon1(str)
      },
      success:function(data){
    	if(data.status == 222){
        	layer.open({
                content:data.msg,
                btn:'确定'
              });
        	swal({ 
    		  title: data.msg, 
    		  //text: "2秒后自动关闭。", 
    		  timer: 2000, 
    		  showConfirmButton: false 
    		});
    	}else if(data.status == 200){
          localStorage.setItem("username",user.val());
          localStorage.setItem("name",data.data.name);
          localStorage.setItem("token",data.data.bak1);
          localStorage.setItem("phone",data.data.phone);
          localStorage.setItem("uid",data.data.id);
          localStorage.setItem("ucard",data.data.bak2);
          localStorage.setItem("sec",data.data.attachment);
          
          swal({
        	  title: '登录成功',
        	  type: 'success',
        	  showCancelButton: true,
        	  confirmButtonText: "确定", 
        	  cancelButtonText: "取消",
        	  closeOnConfirm: true, 
        	  closeOnCancel: true
        	}).then(function(isConfirm) {
        	  if (isConfirm === true) {
        		  window.location.href = "cpa/main";
        	  } else if (isConfirm === false) {
        	   
        	  } else {
        	    // Esc, close button or outside click
        	    // isConfirm is undefined
        	  }
        	}); 
          
        }else if(data.status == 20023){
        	swal({
      		  title: "密码错误!",
      		  icon: "error",
      		  button: "确定",
      	  	});
        }else if(data.status == 20022){
        	swal({
        		  title: "请注册后再登录!",
        		  icon: "error",
        		  button: "确定",
        	 });
        }else{
        	swal({
      		  title: "请检查网络是否畅通!",
      		  icon: "error",
      		  button: "确定",
      	    });
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        if(XMLHttpRequest.status == 400) {
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
  $("#login_btn").click(function(){
    loginverify();
  });
  //切换密码的可见状态
  $(".icon_eye").click(function(){
    var arr = this.src.split("/");
    if(arr[arr.length-1]=="bkj.png"){
      this.src = "../img/kj.png";
      $(this).prev().attr("type","text");
    }else{
      this.src = "../img/bkj.png";
      $(this).prev().attr("type","password");
    }

  });
  
  $("#reg_btn").click(function(){
	  window.location.href = "reg.jsp";
  });

})();