(function(){
//	先判断是否登录
	var flag = checkLogin();
  
  $(".last").click(function(){
	  layer.open({
          content: '您的余额不足。'
          , btn: '确定'
      });
  })
  
  
  $(function(){
    var self = this,_$indexlist,_$wrap_container,_$pageno=0,_$swiper_img,_$ligin_icon,_type,_pageRows,_index=0;
    //数字暂定全局变量，局部会有问题
    var integerNum,decimalsNum;
    var tab;//其他页面跳转过来的tab
    //var dataArr=[];//缓存数据
    _type = '9NEW';//默认新手专区
    _pageRows = 5;
    _$indexlist = $('#sp_wrap').find('.sp_content');
    _$wrap_container = $('#sp_wrap');

    //选项卡
    $("#sp_header_navbar li").click(function() {
      _$pageno = 0;
      $(this).addClass("navbar_active").siblings().removeClass("navbar_active");
      _index = $(this).index();
      tab = _index + 1;
      window.location.href = 'invest_kj.html?tab=' + tab;
      _$wrap_container.html('<ul class="sp_content">'+ '</ul>');
      _$indexlist = $('#sp_wrap').find('.sp_content');//切换tab需重新找dom节点
      //$("#sp_wrap ul").eq(_index).css("display","block").siblings().css("display","none");
      switchType(_index);
      //没数据才调接口
      //if (_$wrap_container.children('li').length == 0){
    });


    //var loadDate = function () {
    //_$activeList = $("#sp_wrap .sp_content");
    //_$activeWrap=$("#sp_wrap");
    /*插件*/
//    var dropload = _$wrap_container.dropload({
//      domUp: {
//        domClass: 'dropload-up',
//        domRefresh: '<div class="dropload-refresh">↓下拉刷新</div>',
//        domUpdate: '<div class="dropload-update">↑释放更新</div>',
//        domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
//      },
//      domDown: {
//        domClass: 'dropload-down',
//        domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
//        domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
//        domNoData: '<div class="dropload-noData">暂无数据</div>'
//      },
//      loadUpFn: function (me) {
//        /*重置页码数*/
//        setTimeout(function(){
//          _$pageno = 1;
//          //上拉加载 先清空，再append数据
//          _$indexlist.html('');
//          $.ajax({
//            type: "GET",
//            url: getAPIURL()+"InvestKJ/getcrowd?pageNumber=" + _$pageno + "&pageRows=" + _pageRows + '&type=' + _type,
//            dataType: "json",
//            data: null,
//            success: function (data) {
//              var list = data;
//              //var dataBox='';
//              if (_$pageno==1&&list.length == 0) {
//                $('#sp_wrap').html('<div style="text-align: center;width: 100%;"><img style="width: 30%" src="../img1/2the%20default%20page@2x.png" alt=""></div><p style="padding: 0.3rem 0;text-align: center;font-size: 0.28rem;">暂无可投资标的</p>');
//                return;
//              }
//              if (_$pageno == 1&&list.length<4) {
//                $('#sp_wrap .dropload-down .dropload-refresh').text('无更多记录');
//              }
//              if (list.length <= 0) {
//                var txtsNULL ="<p class='nothing'>无更多记录</p>";
//                //_$indexlist.append(txtsNULL);
//              } else {
//                //define some variables
//                var txt1,txt2='';
//                for (var i = 0; i < list.length; i++) {
//                  var labels_html='',openning_timeStr='';
//                  var labels = list[i].LendLable;
//
//
//                  //处理labels
//                  if (labels.length != 0){
//                    for (var j=0;j<labels.length;j++){
//                      var _label = '<span class="overflow_omit">' + labels[j].lab_name + '</span>'
//                      labels_html += _label;
//                    }
//                  } else {
//                    labels_html = '';
//                  }
//
//                  //处理整数，小数
//                  splitNum_(list[i].BO_RATES);
//                  //concact rates
//                  rates_html = '<p class="sp_list_rate"><span>' + integerNum + '</span><span>' + '.' + decimalsNum +'</span><span>%</span></p>';
//                  //处理开标时间
//                  if (list[i].short_time != '') {
//                    openning_timeStr = '<div class="sp_list_opennig_time"><span class="opennig_time_style">'+ list[i].short_time +'开标</span></div>';
//                  }else {
//                    openning_timeStr = '';
//                  }
//                  txt1 ='<a class="sp_list" href="' + 'promptly_invest.html?' + 'bono=' + list[i].BO_NO + '" ><div class="sp_split"></div><div class="sp_list_top"><div class="sp_list_title"><span>'
//                    + list[i].BO_TITLE + '</span></div><div class="sp_list_labels">' + labels_html +'</div>'+ openning_timeStr +'</div><div class="sp_list_bottom"><div class="sp_list_bottom_left">' +
//                    rates_html + '<p>预期年化率</p></div><div class="sp_list_bottom_middle">'+ '<img src="../img1/line2.png" style="height:72%;position: absolute;top: 14%;left: 0;" >' +'<p><span style="margin-right:0.40rem">' + list[i].BO_PERIODS + '个月</span><span>'
//                    + list[i].eachAmount + '元起投' + '</span></p><p><img src="../img1/syje.png" style="height: 0.24rem;vertical-align: middle;margin-right: 0.1rem;margin-bottom: 0.05rem;color:#999999"/>' + '剩余金额' + list[i].ResidualAmount + '元</p>'
//                    + '</div><div class="sp_list_bottom_right" id="sp_circle"><canvas id="_detail' + list[i].BO_NO +'" width="60" height="60"></canvas></div></div></a>';
//                  _$indexlist.append(txt1);
//                  _showMe("_detail"+list[i].BO_NO,list[i].progress);
//                  //}
//                  //txt2 = txt2 + txt1;
//                }
//              }
//            },
//            error: function(XMLHttpRequest, textStatus, errorThrown){
//              //var txtsNULL ="<p class='nothing'>暂无记录</p>";
//              //_$indexlist.append(txtsNULL);
//              //alert('出错了！');
//            }
//          });
//          dropload.resetload();
//        },1000);
//      },
//      loadDownFn: function (me) {
//        setTimeout(function(){
//          _$pageno++;
//          comptime(_type,_$pageno);
//          dropload.resetload();
//        },1000);
//      },
//      autoLoad : false
//    });
    //};


//    //转换type
//    var switchType = function (_index) {
//      switch (_index){
//        case 0 :
//          _type = '9NEW';
//          break;
//        case 1 :
//          _type = 'AIB';
//          break;
//        case 2 :
//          _type = '9HUI';
//          break;
//        case 3 :
//          _type = 'TRS';
//          break;
//      }
//    };


//    // 检测其他页面是否传tab参数进来
//    (function () {
//      var v = parseUrl();
//      if (v != undefined){
//        tab = v['tab'];
//        if (tab == 1) {
//          //    默认
//        }else if (tab == 2) {
//          _type = 'AIB';
//          _index = 1;
//          $("#sp_header_navbar").find('li').eq(_index).addClass('navbar_active').siblings().removeClass('navbar_active');
//        }else if (tab == 3) {
//          _type = '9HUI';
//          _index = 2;
//          $("#sp_header_navbar").find('li').eq(_index).addClass('navbar_active').siblings().removeClass('navbar_active');
//        }else if (tab == 4) {
//          _type = 'TRS';
//          _index = 3;
//          $("#sp_header_navbar").find('li').eq(_index).addClass('navbar_active').siblings().removeClass('navbar_active');
//        }
//      };
//    })();
//
//    //comptime();
//    //    调取接口
//    function comptime(type,page) {
//      $.ajax({
//        type: "GET",
//        url: getAPIURL()+"InvestKJ/getcrowd?pageNumber=" + _$pageno + "&pageRows=" + _pageRows + '&type=' + _type,
//        dataType: "json",
//        data: null,
//        success: function (data) {
//          var list = data;
//          if (_$pageno==1&&list.length == 0) {
//            $('#sp_wrap').html('<div style="text-align: center;width: 100%;margin-top:150px;"><img style="width: 30%" src="../img1/2the%20default%20page@2x.png" alt=""></div><p style="padding: 0.3rem 0;text-align: center;font-size: 0.28rem;">暂无可投资标的</p>');
//            return;
//          }
//          if (_$pageno == 1&&list.length<4) {
//            $('#sp_wrap .dropload-down .dropload-refresh').text('无更多记录');
//          }
//          if (list.length <= 0) {
//            var txtsNULL ="<p class='nothing'>无更多记录</p>";
//            //_$indexlist.append(txtsNULL);
//          } else {
//            //define some variables
//            var txt1,txt2='';
//            for (var i = 0; i < list.length; i++) {
//              var labels_html='',openning_timeStr='';
//              var labels = list[i].LendLable;
//
//
//              //处理labels
//              if (labels.length != 0){
//                for (var j=0;j<labels.length;j++){
//                  var _label = '<span class="overflow_omit">' + labels[j].lab_name + '</span>'
//                  labels_html += _label;
//                }
//              } else {
//                labels_html = '';
//              }
//
//              //处理整数，小数
//              splitNum_(list[i].BO_RATES);
//              //concact rates
//              rates_html = '<p class="sp_list_rate"><span>' + integerNum + '</span><span>' + '.' + decimalsNum +'</span><span>%</span></p>';
//              //处理开标时间
//              if (list[i].short_time != '') {
//                openning_timeStr = '<div class="sp_list_opennig_time"><span class="opennig_time_style">'+ list[i].short_time +'开标</span></div>';
//              }else {
//                openning_timeStr = '';
//              }
//              if(list[i].BO_TYPE == '体验'){
//                var _$dataStr = list[i].BO_PERIODS + '天';
//              }else {
//                var _$dataStr = list[i].BO_PERIODS + '个月';
//              }
//              txt1 ='<a class="sp_list" href="' + 'promptly_invest.html?' + 'bono=' + list[i].BO_NO + '" ><div class="sp_split"></div><div class="sp_list_top"><div class="sp_list_title"><span>'
//                + list[i].BO_TITLE + '</span></div><div class="sp_list_labels">' + labels_html +'</div>'+ openning_timeStr +'</div><div class="sp_list_bottom"><div class="sp_list_bottom_left">' +
//                rates_html + '<p>预期年化率</p></div><div class="sp_list_bottom_middle">'+ '<img src="../img1/line2.png" style="height:72%;position: absolute;top: 14%;left: 0;" >' +'<p><span style="margin-right:0.40rem">' + _$dataStr + '</span><span>'
//                + list[i].eachAmount + '元起投' + '</span></p><p><img src="../img1/syje.png" style="height: 0.24rem;vertical-align: middle;margin-right: 0.1rem;margin-bottom: 0.05rem;color:#999999"/>' + '剩余金额' + list[i].ResidualAmount + '元</p>'
//                + '</div><div class="sp_list_bottom_right" id="sp_circle"><canvas id="_detail' + list[i].BO_NO +'" width="60" height="60"></canvas></div></div></a>';
//              _$indexlist.append(txt1);
//              _showMe("_detail"+list[i].BO_NO,list[i].progress);
//              //}
//              //txt2 = txt2 + txt1;
////                          setTimeout(function () {
////                              dropload.resetload();
////                          },2000)
//            }
//            //_$wrap_container.html('<ul class="sp_content">' + txt2 + '</ul>')
//          }
//        },
//        error: function(XMLHttpRequest, textStatus, errorThrown){
//          //var txtsNULL ="<p class='nothing'>暂无记录</p>";
//          //_$indexlist.append(txtsNULL);
//          //alert('出错了！');
//        }
//      });
//    }

    //comptime(_type,_$pageno);



    //处理上拉加载更多滚动条乱跳的问题
//      var touchPosition  = function () {
//          var positionScrollTop = $('#sp_wrap').find('.scroller').eq(0);
//          var scroller_scrollTop = positionScrollTop.height();
//          positionScrollTop.scrollTop = scroller_scrollTop;
//      }

    //截取小数点前后的数
//    function splitNum_ (num) {
//      var numString = num.toString();
//      var a = numString.indexOf('.');
//      if (a != -1) {
//        integerNum = numString.substr(0,a);
//        decimalsNum = numString.substr(a+1,numString.length-a);
//        return integerNum,decimalsNum;
//      } else {
//        var new_num = Convert(num);
//        splitNum_(new_num);
//      }
//    }
//
//    //为数字添加千分位分隔符
//    function Convert(money) {
//      var s = money; //获取小数型数据
//      s += "";
//      if (s.indexOf(".") == -1) s += ".0"; //如果没有小数点，在后面补个小数点和0
//      if (/\.\d$/.test(s)) s += "0";   //正则判断
//      while (/\d{4}(\.|,)/.test(s))  //符合条件则进行替换
//        // s = s.replace(/(\d)(\d{3}(\.|,))/, "$1,$2"); //每隔3位添加一个
//        s = s.replace(".00" , "");
//      return s;
//    }
//
//    function goumai(){
//    	layer.open({
//    	    content: "您的余额不足",
//    	    skin: 'msg',
//    	    time: 2
//    	  });
//    }
//
//    //画圈圈
//    var _showMe = function (id,shu) {
//      circleProgress({
//        id: id,
//        progress: shu, // default: 100
//        duration: 500,
//        color: '#fe5e5f',
//        bgColor: '#ffae95',
//        textColor: '#f45a3f',
//        progressWidth: 0.1,
//        fontSize:24,
//        toFixed: 1
//      });
//    };
  });
})();