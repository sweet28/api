(function(){
    $("#xzgj").change(function(v){
          
    	var x = $("#xzgj");  
        x.val("volvo"); 
        
        layer.open({
            content: '您的国家是中国。'
            , skin: 'msg'
            , time: 3 //2秒后自动关闭
            ,end: function(){
            	x.val("volvo"); 
            }
        });
        
    });
    
  var user = $("#phone");
  var pwd = $("#pwd");
  function loginverify(){
    loading.open();
    if(user.val()==""||user.val().replace(/\s/g,"")==""){
      loading.close();
      layer.open({
        content:"请输入手机号(用户名)",
        btn:'确定'
      });
      return;
    }
    if(pwd.val()==""){
      loading.close();
      layer.open({
        content:"请输入密码",
        btn:'确定'
      });
      return;
    }
    $.ajax({
      type: "POST",
      url: getAPIURL() + "fenuser/login",
      dataType: "json",
      //contentType: "application/json",
      data:{
        "phone":$.trim(user.val()),
        "pwd":pwd.val()
      },
      success:function(data){
    	  console.log(user.val()+"::"+pwd.val());
    	  console.log(data.status+";;;;;;;;;;;;;;;");
        if(data.status == 200){
        	console.log(data.data+":::"+data.msg+":name:"+data.data.name+":token:"+data.data.bak1+":phone:"+data.data.phone);
          localStorage.setItem("username",user.val());
          localStorage.setItem("name",data.data.name);
          localStorage.setItem("token",data.data.bak1);
          localStorage.setItem("phone",data.data.phone);
          
          console.log("::::name:"+localStorage.getItem("name")+":token:"+localStorage.getItem("token")+":phone:"+localStorage.getItem("phone"));
          
          loading.close();
          layer.open({
            content: '登录成功'
            ,skin: 'msg'
            ,time: 2 //2秒后自动关闭
            ,end:function(){
              //判断直接从登录页进去，跳到个人中心
              console.log(history.length);
              if(history.length == 1){
                window.location.href = "../page/personal.html";
                return false;
              }
              if(window.location.search!=""){
                window.location.href = "../page/personal.html";
              }else {
                var value = document.referrer;
                if(value.indexOf("login.html")!=-1){
                  window.location.href = "../page/personal.html";
                  return;
                }
                if(!value){
                  window.location.href = "../page/personal.html";
                  return;
                }
                location.href = document.referrer;
                //解决safari不支持的问题//go(-1)ios不会刷新页面
                return false;
              }
            }
          });
        }else if(data.status == 20023){
        	loading.close();
        	layer.open({
                content:"密码错误",
                btn:'确定'
              });
        }else if(data.status == 20022){
        	loading.close();
        	layer.open({
                content:"请注册后再登录",
                btn:'确定'
              });
        }else{
        	loading.close();
        	layer.open({
                content:"请检查网络是否畅通",
                btn:'确定'
              });
        }
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        loading.close();
        if(XMLHttpRequest.status == 400) {
          var obj = JSON.parse(XMLHttpRequest.responseText);
          layer.open({
            content:obj.Message,
            btn:'确定'
          });
        }
      }
    });
  }
  $(".login_btn").click(function(){
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

})();