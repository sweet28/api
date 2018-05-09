(function(){
  var user = $("#user");
  var pwd = $("#pwd");
  function loginverify(){
    loading.open();
    if(user.val()==""||user.val().replace(/\s/g,"")==""){
      loading.close();
      layer.open({
        content:"请输入用户名",
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
      url: getAPIURL() + "Account/Login",
      dataType: "json",
      contentType: "application/json",
      data:JSON.stringify({
        username:$.trim(user.val()),
        password:pwd.val()
      }),
      success:function(data){
        if(data.rtn == 0){
          localStorage.setItem("username",user.val());
          localStorage.setItem("token",data.token);
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