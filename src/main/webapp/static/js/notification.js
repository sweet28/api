(function () {
  $(".returntop").click(function(){
    $(".mescroll").animate({
      scrollTop:0
    });
  });
  var hash = location.hash.substr(1);
  if(hash=="personal"){
    $(".top_titles > span").eq(1).addClass("active").siblings().removeClass("active");
    $(".list").hide();
    $(".list").eq(1).fadeIn();
    var mescroll = new MeScroll("mescroll", {
      down: {
        callback: downCallback //下拉刷新的回调
      },
      up: {
        page:{
          size:12
        },
        callback: upCallback //上拉加载回调,简写callback:function(page){upCallback(page);}
      }
    });
  }
  else{
    var getToken=getTOKEN();
    if(getToken==null){
      $("#notice_list").addClass("nodata").html('<img src="../img/nonews.png"><p>暂无最新消息哦~</p>');
    }
    else {
      var mescroll = new MeScroll("mescroll", {
        down: {
          callback: downCallback //下拉刷新的回调
        },
        up: {
          page:{
            size:12
          },
          callback: upCallback //上拉加载回调,简写callback:function(page){upCallback(page);}
        }
      });
    }
  }
  //tab切换代码
  var spans = $(".top_titles > span");
  spans.click(function(){
    var index = $(this).index();
    $(this).addClass("active").siblings().removeClass("active");
    $(".list").hide();
    $(".list").eq(index).fadeIn();
  });
  //投呗公告接口
  var pageNum = 1;
  function tbpublic(){
    $.ajax({
      type:"get",
      url:getAPIURL()+"home/Notices?pageNumber=1&pageRows=10",
      dataType: "json",
      success:function(data){
        if(data.length <= 0){
          $(".publiclist_wrap").addClass("public_listnodata");
        }
        var str = "";
        for(var i =0;i < data.length;i++){
          str += "<li><a href='public_detail.html?data="+data[i].Url+"'><p class='title'>"+data[i].Title+"</p>";
          str += "<p class='date'>"+data[i].Date+"</p><img class='to_detail' src='../img/public_arrow.png'/>";
          str += "</a></li>"
        }
        mescroll1.endSuccess(data.length);
        pageNum = 1;
        $(".public_list").html(str);
        if(data.length>0&&data.length < 10){
          $(".publiclist_wrap .mescroll-upwarp").css("visibility","visible");
          $(".mescroll-upwarp").html("无更多记录");
        }
      },
      error: function(data) {
        //联网失败的回调,隐藏下拉刷新的状态
        mescroll1.endErr();
      }
    });
  }
  function tbpublic1(){
    pageNum++;
    $.ajax({
      type:"get",
      url:getAPIURL()+"home/Notices?pageNumber="+pageNum+"&pageRows=10",
      dataType: "json",
      success:function(data){
        var str = "";
        for(var i =0;i < data.length;i++){

          str += "<li><a href='public_detail.html?data="+data[i].Url+"'><p class='title'>"+data[i].Title+"</p>";
          str += "<p class='date'>"+data[i].Date+"</p><img class='to_detail' src='../img/public_arrow.png'/>";
          str += "</a></li>"
        }
        mescroll1.endSuccess(data.length);
        $(".public_list").append(str);
      },
      error: function(e) {
        //联网失败的回调,隐藏下拉刷新和上拉加载的状态
        mescroll1.endErr();
      }
    });
  }
  var mescroll1 = new MeScroll("mescroll1", {
    down: {
      callback: downCallback1 //下拉刷新的回调
    },
    up: {
      callback: upCallback1 //上拉加载回调,简写callback:function(page){upCallback(page);}
    }
  });
  function downCallback1(){
    tbpublic();
  }
  function upCallback1(){
    tbpublic1();
  }

  $.ajax({
    type:"get",
    url:getAPIURL()+"home/Notices?pageNumber=1&pageRows=100000",
    dataType: "json",
    success:function(data){
      if(data.length>0){
        window.sessionStorage.setItem("tbpublicArr",JSON.stringify(data));
      }
    },
    error: function(data) {
    }
  });

  var _$pageno = 1, _$fBtn, _$oUl, _$fBtnTxt, _flag = false;
  /*是否全选*/
  _$fBtn = $("#f_btn");
  _$oUl = $("#notification_list");
  _$fBtnTxt = _$fBtn.text();

  //下拉刷新的回调
  function downCallback(){
    comptime(_flag);
  }
  function upCallback(){
    loadMore(_flag);
  }


  function comptime() {
    var uid = getUIDByJWT().unique_name;
    $.ajax({
      type: "GET",
      url: getAPIURL() + "securitysettings/" + uid + "/message?pageNumber=1&pageRows=12",
      dataType: "json",
      data: null,
      success: function (data) {
        var list = data;
        var _$notice_list = $("#notice_list");
        if (list instanceof Array == false) {
          $(".notification_list").html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_information.png'></div></li></ul>");
          $("#wrapper").remove();
        }
        else {
          var content = "";
          /*判断当前是否是全选状态，如果是就要显示复选框*/
          if (_$fBtn.text() == "全选") {
            for (var i = 0; i < list.length; i++) {
              list[i].im_message_send_time = list[i].im_message_send_time.substr(0, 16);
              content += '<li class="list_item done" data-mid="' + list[i].im_id + '">' +
                '<label class="clearfix ">' +
                '<span class="checkbox_btn ">' +
                '<span></span>' +
                '<input type="checkbox"/>' +
                '</span>';
//                          if (list[i].im_status == "NOREAD") {
//                              content += '<span class="desc_wrap read">';
//                          } else {
              content += '<span class="desc_wrap ">';
//                          }
              content += '<span class="title">' +list[i].im_message_title+ '</span>' +
                '<span class="sub_title">' + list[i].im_message_send_time + '</span>' +
                '</span>' +
//                              '<time class="time">' + list[i].im_message_send_time + '</time>' +
//								"<img class='to_detail' src='../img/public_arrow.png'>" +
                '</label>' +
                '</li>';
            }
          }
          else {
            for (var i = 0; i < list.length; i++) {
              list[i].im_message_send_time = list[i].im_message_send_time.substr(0, 16);
              content += '<li class="list_item" data-mid="' + list[i].im_id + '">' +
                '<label class="clearfix ">' +
                '<span class="checkbox_btn ">' +
                '<span></span>' +
                '<input type="checkbox"/>' +
                '</span>';
//                          if (list[i].im_status == "NOREAD") {
//                              content += '<span class="desc_wrap read">';
//                          } else {
              content += '<span class="desc_wrap ">';
//                          }
              content += '<span class="title">'+list[i].im_message_title+ '</span>' +
                '<span class="sub_title">' + list[i].im_message_send_time + '</span>' +
                '</span>' +
//                              '<time class="time">' + list[i].im_message_send_time + '</time>' +
//								"<img class='to_detail' src='../img/public_arrow.png'>" +
                '</label>' +
                '</li>';
            }
          }
          _$pageno = 1;
          mescroll.endSuccess(data.length);
          if(data.length < 12){
            $(".mescroll-upwarp").css("visibility","visible");
            $(".notification_list .mescroll-upwarp").html("无更多记录");
          }
          _$notice_list.html(content);
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        if (XMLHttpRequest.status == 400) {
          mescroll.endErr();
          $("#notice_list").html("<li class='nothing'><p>哎呀，出错啦！</p><div class='noInformation'><img src='../img/wrong.png'></div></li>");
          $("#wrapper").remove();
        }
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  function loadMore() {
    _$pageno++;
    var uid = getUIDByJWT().unique_name;
    $.ajax({
      type: "GET",
      url: getAPIURL() + "securitysettings/" + uid + "/message?pageNumber=" + _$pageno + "&pageRows=12",
      dataType: "json",
      data: null,
      success: function (data) {
        var list = data;
        var _$notice_list = $("#notice_list");
        if (list instanceof Array == false) {
          if( _$pageno==1){
            $("#notification_list").html("<ul>" + $(".notification_list ul").html() + "<li class='nothing' style='line-height: 40px;font-size: 14px;'></li>" + "</ul>");
            $("#wrapper").remove();
          }else{
            mescroll.endSuccess(2);
          }

        }
        else {
          var content = "";
          if (_$fBtn.text() == "全选") {
            for (var i = 0; i < list.length; i++) {
              list[i].im_message_send_time = list[i].im_message_send_time.substr(0, 16);
              content += '<li class="list_item done" data-mid="' + list[i].im_id + '">' +
                '<label class="clearfix ">' +
                '<span class="checkbox_btn ">' +
                '<span></span>' +
                '<input type="checkbox"/>' +
                '</span>';
//                          if (list[i].im_status == "NOREAD") {
//                              content += '<span class="desc_wrap read">';
//                          } else {
              content += '<span class="desc_wrap ">';
//                          }
              content += '<span class="title">' + list[i].im_message_title + '</span>' +
                '<span class="sub_title">' + list[i].im_message_send_time + '</span>' +
                '</span>' +
//                              '<time class="time">' + list[i].im_message_send_time + '</time>' +
//								"<img class='to_detail' src='../img/public_arrow.png'>" +
                '</label>' +
                '</li>';
            }
          } else {
            for (var i = 0; i < list.length; i++) {
              list[i].im_message_send_time = list[i].im_message_send_time.substr(0, 16);
              content += '<li class="list_item" data-mid="' + list[i].im_id + '">' +
                '<label class="clearfix ">' +
                '<span class="checkbox_btn ">' +
                '<span></span>' +
                '<input type="checkbox"/>' +
                '</span>';
//                          if (list[i].im_status == "NOREAD") {
//                              content += '<span class="desc_wrap read">';
//                          } else {
              content += '<span class="desc_wrap ">';
//                          }
              content += '<span class="title">' +list[i].im_message_title + '</span>' +
                '<span class="sub_title">' + list[i].im_message_send_time + '</span>' +
                '</span>' +
//                              '<time class="time">' + list[i].im_message_send_time + '</time>' +
//								"<img class='to_detail' src='../img/public_arrow.png'>" +
                '</label>' +
                '</li>';
            }
          }
          mescroll.endSuccess(data.length);
          _$notice_list.append(content);
        }
      },
      error: function (XMLHttpRequest, textStatus, errorThrown) {
        console.log("babbaba");
        if (XMLHttpRequest.status == 400) {
          mescroll.endErr();
          $("#notice_list").html("<li class='nothing'><p>哎呀，出错啦！</p><div class='noInformation'><img src='../img/wrong.png'></div></li>");
          $("#wrapper").remove();
        }
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  /*单击li的时候 判断是显示弹窗还是复选框*/
  _$oUl.on("click", "li", function () {
    /*弹窗*/
    var _this=$(this);
    var uid = getUIDByJWT().unique_name;
    var mid = $(this).attr("data-mid");
    $.ajax({
      type: "POST",
      url: getAPIURL() + "securitysettings/" + uid + "/message/" + mid,
      contentType: "application/json",
      dataType: "json",
      data: null,
      success: function (data) {
        layer.open({
          title: [
            data.im_message_title, /*动态*/
            'background-color: #fff; color:#464646;font-size: 0.36rem;padding:0;height: 1.08rem;line-height: 1.08rem;font-weight: bolder;'
          ],
          content: data.im_message_content, /*动态*/
          btn: '确定',
          yes: function (index) {
            if (_this.find(".desc_wrap").hasClass("read")) {
              _this.find(".desc_wrap").removeClass("read");
            }
            layer.close(index)
          }
        });
      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  });



})();