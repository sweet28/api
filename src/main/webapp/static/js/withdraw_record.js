(function() {
  $(".close_btn1").click(function(){
    $(".cancel_bounce").hide();
  });
//	refresher.init({
//		id: "wrapper",
//		pullDownAction: function() {
//			setTimeout(function() {
//				with_record(true);
//				wrapper.refresh();
//			}, 1000);
//		},
//		pullUpAction: function() {
//			setTimeout(function() {
//				with_record();
//				wrapper.refresh();
//			}, 1000);
//		}
//	});
  var uid = getUIDByJWT().unique_name;
  var pageNum = 0;
  var dropload = $("#wrapper").dropload({
    domUp: {
      domClass: 'dropload-up',
      domRefresh: '<div class="dropload-refresh">↓下拉刷新</div>',
      domUpdate: '<div class="dropload-update">↑释放更新</div>',
      domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
    },
    domDown: {
      domClass: 'dropload-down',
      domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
      domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
      domNoData: '<div class="dropload-noData">暂无数据</div>'
    },
    loadUpFn:function(me){
      with_record(true);
    },
    loadDownFn: function (me){
      with_record();
    },
//      autoLoad : false
  });
  //取消提现函数
  function cancel_withdraw(cwid){
    $.ajax({
      type: "post",
      url: getAPIURL() + "User/" + uid + "/CancelWithdraw",
      dataType: "json",
      contentType: "application/json",
      data:JSON.stringify({
        CW_ID:cwid
      }),
      success: function(data1){
        if(data1.rtn == 1){
          layer.open({
            content: "提现取消成功",
            skin: 'msg',
            time: 2 //2秒后自动关闭
          });
          with_record(true);
        }else{
          layer.open({
            content: data1.Message,
            skin: 'msg',
            time: 2 //2秒后自动关闭
          });
        }
      },
      headers: {
        "Authorization":"Bearer "+getTOKEN()
      }
    });
  }
  //提现记录列表
  function with_record(flag){
    if(flag){
      pageNum = 1;
    }else{
      pageNum++
    }
    $.ajax({
      type: "get",
      url: getAPIURL() + "User/" + uid + "/Withdraw/List?pageNumber="+pageNum+"&pageRows=20",
      dataType: "json",
      contentType: "application/json",
      success: function(data) {
        if(pageNum <= 1){
          if(data.length <= 0){
            $("#wrapper").hide();
            $(".no_withdrawrecord").show();
          }
        }

        var html = "";
        //提现金额
        for(var i = 0;i < data.length;i++){
          var timeArr = data[i].cwtime.split(" ");
          html += "<li data-id="+data[i].cwid+" class='clearfix'>"+"<div class='item'>"+"<span>"+timeArr[0]+"</span>";
          html += "<span>"+timeArr[1]+"</span></div>";
          html += "<div class='item'>"+"<span class='tx_ed'>提现"+data[i].amount+"</span>";
          html += "<span class='tx_sx'>手续费"+data[i].fees+"</span></div>";
//					html +="<div class='item'>"+"<span>"+data[i].bankName+"</span>";
//					html += "<span>**"+data[i].bankNo.slice(-4)+"</span></div>";
          html += "<div class='item'>"+"<span>"+data[i].status+"</span>";
          if(data[i].action){
            html += "<span class='cancel_withdraw'>取消提现</span>";
          }
          html += "</div></li>";
        }
        if(flag){
          setTimeout(function(){
            $(".detail").empty().append(html);
            // 每次数据加载完，必须重置
            dropload.resetload();
            $(".cancel_withdraw").click(function(){
              var cwid = $(this).parent().parent().data("id");
              var txed = $(this).parent().parent().find(".tx_ed").text();
              var txed_value = txed.slice(2);
              $(".return_fee").text(txed_value);
              $(".cancel_bounce").show();
              $(".btn_cancel").click(function(){
                $(".cancel_bounce").hide();
                return;
              });
              $(".btn_qd").click(function(){
                $(".cancel_bounce").hide();
                cancel_withdraw(cwid);
              });
            });
          },500);
        }else{
          setTimeout(function(){
            $(".detail").append(html);
            // 每次数据加载完，必须重置
            dropload.resetload();
            if(data.length < 20){
              $(".dropload-refresh").text("无更多内容");
            }
            $(".cancel_withdraw").click(function(){
              var cwid = $(this).parent().parent().data("id");
              var txed = $(this).parent().parent().find(".tx_ed").text();
              var txed_value = txed.slice(2);
              $(".return_fee").text(txed_value);
              $(".cancel_bounce").show();
              $(".btn_cancel").click(function(){
                $(".cancel_bounce").hide();
                return;
              });
              $(".btn_qd").click(function(){
                $(".cancel_bounce").hide();
                cancel_withdraw(cwid);
              });
            });
          },500);
        }
//				$(".cancel_withdraw").click(function(){
//					var cwid = $(this).parent().parent().data("id");
//					var txed = $(this).parent().parent().find(".tx_ed").text();
//					var txed_value = txed.slice(2);
//					$(".return_fee").text(txed_value);
//					$(".cancel_bounce").show();
//					$(".btn_cancel").click(function(){
//						$(".cancel_bounce").hide();
//						return;
//					});
//					$(".btn_qd").click(function(){
//						$(".cancel_bounce").hide();
//						cancel_withdraw(cwid);
//					});
//				});
      },
      error:function(){
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
//	with_record();

})();