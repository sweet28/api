(function(){
  $(function(){
    var self = this,_$indexlist,_$wrap_container,_$pageno=0,_$swiper_img,_$ligin_icon,_type,_pageRows,_index=0;
    //数字暂定全局变量，局部会有问题
    var integerNum,decimalsNum;
    var tab;//其他页面跳转过来的tab
    //var dataArr=[];//缓存数据
    _type = 1;//默认新手专区
    _pageRows = 10;
    _$indexlist = $('#sp_wrap').find('.sp_content');
    _$wrap_container = $('#sp_wrap');

    //选项卡
    $("#sp_header_navbar li").click(function() {
      _$pageno = 0;
      $(this).addClass("navbar_active").siblings().removeClass("navbar_active");
      _index = $(this).index();
      tab = _index + 1;
      window.location.href = 'invest.html?tab=' + tab;
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
    var dropload = _$wrap_container.dropload({
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
      loadUpFn: function (me) {
        /*重置页码数*/
        setTimeout(function(){
          _$pageno = 1;
          //上拉加载 先清空，再append数据
          _$indexlist.html('');
          console.log("------------------------------------------");
          
          $.ajax({
    	      type: "post",
    	      url: getAPIURL() + "miner/record/recordlist",
    	      dataType: "json",
    	      data:{
    	    	  "traderState":0,
              	  "traderType":parseInt(_type),
              	  "page":parseInt(_$pageno),
              	  "row":parseInt(_pageRows),
              	  "traderId":localStorage.getItem("uid")
    	      },
    	      success: function (data) {
                  var list = data.list;
                  if (_$pageno==1&&list.length == 0) {
                    $('#sp_wrap').html('<div style="text-align: center;width: 100%;">'
                    		+'<img style="width: 30%" src="../img/no_investment.png" alt=""></div>'
                    		+'<p style="padding: 0.3rem 0;text-align: center;font-size: 0.28rem;">暂无委托</p>');
                    return;
                  }
                  if (_$pageno == 1&&list.length<4) {
                    $('#sp_wrap .dropload-down .dropload-refresh').text('无更多记录');
                  }
                  if (list.length <= 0) {
                    var txtsNULL ="<p class='nothing'>无更多记录</p>";
                  } 
                  else {
                    var txt1,txt2='';
                    for (var i = 0; i < list.length; i++) {
                    	var cpatype ;
                      	var mm;
                      	
                      	if(list[i].traderType==2){
                      		cpatype = "售卖中";
                      		mm="卖";
                      	}
                      	if(list[i].traderType==1){
                      		cpatype = "买入中";
                      		mm="买"
                      	}
                      	
                      	var ahref;
                      	if(_type==1){
                      		ahref = "<a href='javascript:csCPA("+list[i].id+","+list[i].traderCount+");'>出售</a>";
                      	}
                      	if(_type==2){
                      		ahref = "<a href='javascript:mrCPA("+list[i].id+","+list[i].traderCount+");'>买入</a>";
                      	}
                      	if(_type==9){
                      		ahref = "<a href='javascript:cxCPA("+list[i].id+");'>撤销</a>";
                      	}
                      	
          			    txt1 += "<tr>" +
        	  			    		"<td>"+mm+(list[i].id)+"</td>" +
        	  			    		"<td>"+list[i].entrustPrice+"</td>" +
        	  			    		"<td>"+list[i].traderCount+"</td>" +
        	  			    		"<td>" + cpatype +"</td>" +
        	  			    		"<td>" + ahref +"</td>" +
          			    		"</tr>";
                        
          			    _$indexlist.append(txt1);
//                      _showMe("_detail"+list[i].BO_NO,list[i].progress);
                      //}
                      //txt2 = txt2 + txt1;
                    }
                    txt2 = txt2 + txt1;
                    $('#gift').html(txt2);
                  }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                  //var txtsNULL ="<p class='nothing'>暂无记录</p>";
                  //_$indexlist.append(txtsNULL);
                  //alert('出错了！');
                }
    	      ,headers: {
    	        "Authorization": "Bearer " + getTOKEN()
    	      }
          });
          dropload.resetload();
        },600);
      },
      loadDownFn: function (me) {
        setTimeout(function(){
          _$pageno++;
          comptime(_type,_$pageno);
          dropload.resetload();
        },600);
      },
      autoLoad : false
    });
    //};


    //转换type
    var switchType = function (_index) {
      switch (_index){
        case 0 :
//          _type = '9NEW';
        	_type = 1;
          break;
        case 1 :
//          _type = 'AIB';
        	_type = 2;
          break;
        case 2 :
        	_type = 9;
//          _type = '9HUI';
          break;
        case 3 :
        	_type = 10;
//          _type = 'TRS';
          break;
      }
    };


    // 检测其他页面是否传tab参数进来
    (function () {
      var v = parseUrl();
      if (v != undefined){
        tab = v['tab'];
        if (tab == 1) {
          //    默认
        }else if (tab == 2) {
          _type = 2;
          _index = 1;
          $("#sp_header_navbar").find('li').eq(_index).addClass('navbar_active').siblings().removeClass('navbar_active');
        }else if (tab == 3) {
          _type = 9;
          _index = 2;
          $("#sp_header_navbar").find('li').eq(_index).addClass('navbar_active').siblings().removeClass('navbar_active');
        }else if (tab == 4) {
          _type = 10;
          _index = 3;
          $("#sp_header_navbar").find('li').eq(_index).addClass('navbar_active').siblings().removeClass('navbar_active');
        }
      };
    })();

    var txt2='';
    //comptime();
    //    调取接口
    function comptime(type,page) {
      $.ajax({
    	  type: "post",
	      url: getAPIURL() + "miner/record/recordlist",
	      dataType: "json",
	      data:{
	    	  "traderState":0,
          	  "traderType":parseInt(_type),
          	  "page":parseInt(_$pageno),
          	  "row":parseInt(_pageRows),
          	  "traderId":localStorage.getItem("uid")
	      },
        success: function (data) {
        	 var list = data.list;
        	 console.log(data.list);
        	 console.log("---:::"+data);
          if (_$pageno==1&&list.length == 0) {
            $('#sp_wrap').html('<div style="text-align: center;width: 100%;margin-top:150px;"><img style="width: 30%" src="" alt=""></div>'
            		+'<p style="padding: 0.3rem 0;text-align: center;font-size: 0.28rem;">暂无订单委托</p>');
            return;
          }
          if (_$pageno == 1&&list.length<4) {
            $('#sp_wrap .dropload-down .dropload-refresh').text('无更多记录');
          }
          if (list.length <= 0) {
            var txtsNULL ="<p class='nothing'>无更多记录</p>";
            //_$indexlist.append(txtsNULL);
          } else {
            //define some variables
        	  var txt1 = '';//,txt2='';
              for (var i = 0; i < list.length; i++) {
              	var cpatype ;
              	var mm;
              	
              	if(list[i].traderType==2){
              		cpatype = "售卖中";
              		mm="卖";
              	}
              	if(list[i].traderType==1){
              		cpatype = "买入中";
              		mm="买"
              	}
              	
              	var ahref;
              	if(_type==1){
              		ahref = "<a href='javascript:csCPA("+list[i].id+","+list[i].traderCount+");'>出售</a>";
              	}
              	if(_type==2){
              		ahref = "<a href='javascript:mrCPA("+list[i].id+","+list[i].traderCount+");'>买入</a>";
              	}
              	if(_type==9){
              		ahref = "<a href='javascript:cxCPA("+list[i].id+");'>撤销</a>";
              	}
              	
  			    txt1 += "<tr>" +
	  			    		"<td>"+mm+(list[i].id)+"</td>" +
	  			    		"<td>"+list[i].entrustPrice+"</td>" +
	  			    		"<td>"+list[i].traderCount+"</td>" +
	  			    		"<td>" + cpatype +"</td>" +
	  			    		"<td>" + ahref +"</td>" +
  			    		"</tr>";
                
  			    _$indexlist.append(txt1);
//                _showMe("_detail"+list[i].BO_NO,list[i].progress);
                //}
                //txt2 = txt2 + txt1;
              }
              txt2 = txt2 + txt1;
//              _$indexlist.append(txt2);
              $('#gift').html(txt2);
          }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        }
      });
    }

    //comptime(_type,_$pageno);
    //处理上拉加载更多滚动条乱跳的问题
//      var touchPosition  = function () {
//          var positionScrollTop = $('#sp_wrap').find('.scroller').eq(0);
//          var scroller_scrollTop = positionScrollTop.height();
//          positionScrollTop.scrollTop = scroller_scrollTop;
//      }

    //截取小数点前后的数
    function splitNum_ (num) {
      var numString = num.toString();
      var a = numString.indexOf('.');
      if (a != -1) {
        integerNum = numString.substr(0,a);
        decimalsNum = numString.substr(a+1,numString.length-a);
        return integerNum,decimalsNum;
      } else {
        var new_num = Convert(num);
        splitNum_(new_num);
      }
    }

    //为数字添加千分位分隔符
    function Convert(money) {
      var s = money; //获取小数型数据
      s += "";
      if (s.indexOf(".") == -1) s += ".0"; //如果没有小数点，在后面补个小数点和0
      if (/\.\d$/.test(s)) s += "0";   //正则判断
      while (/\d{4}(\.|,)/.test(s))  //符合条件则进行替换
        // s = s.replace(/(\d)(\d{3}(\.|,))/, "$1,$2"); //每隔3位添加一个
        s = s.replace(".00" , "");
      return s;
    }


    //画圈圈
    var _showMe = function (id,shu) {
      circleProgress({
        id: id,
        progress: shu, // default: 100
        duration: 500,
        color: '#fe5e5f',
        bgColor: '#ffae95',
        textColor: '#f45a3f',
        progressWidth: 0.1,
        fontSize:24,
        toFixed: 1
      });
    };
  });
})();

function csCPA(id,count){
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		alert("未认证用户不能交易。");
		return false;
	}
	
	$.ajax({
		type: "post",
	      url: getAPIURL() + "bank/list",
	      dataType: "json",
	      data: {
	    	  "fensUserId":localStorage.getItem("uid"),
	    	  "pageSize":100,
	    	  "pageNum":0
	      },
	      success: function (data) {
	        var list = data.list;
	        console.log(list.length+"-------------dddd");
	        if (list.length <= 0) {
	        	console.log("没有账号信息");
	        	layer.open({
			          content: '银行卡未绑定不能交易挂单。'
			          , btn: '确定'
	  		      });
	  			return false;
	        }else{
	        	
	        	$.ajax({
	        	    type: "post",
	        	    url: getAPIURL() + "wallet/list",
	        	    dataType: "json",
	        	    data: {
	        	    	"fensUserId":localStorage.getItem("uid")
	        	    },
	        	    success: function (data) {
	        	    	var dd = data.data;
	        	    	if(data.status==200){
	        	    		//可用余额
	        	    		var yue = dd.ableCpa;
//	        	    		if(yue < 1 ){
//	        	    			layer.open({
//	        	    		          content: '账户钱包CPA余额不足。'
//	        	    		          , btn: '确定'
//	        	    		      });
//	        	    			return false;
//	        	    		}
	        	    		if(yue*0.8 >= count){
	        	    			window.location.href = "../page/my_invest.html?"+id+"&cs";
//	        	    			layer.open({
//	        	    		          content: '即将出售'+count+'个CPA，确定吗？'
//	        	    		          ,btn: '确定'
//	        	    		          ,yes :function(){
//	      	        		        	
//	        	    		          }
//	        	    		      });
	        	    		}else{
	        	    			layer.open({
	        	    		          content: '账户钱包CPA余额不足。'
	        	    		          , btn: '确定'
	        	    		      });
	        	    			return false;
	        	    		}
	        	    	}else{
	        	    		layer.open({
	          		          content: '账户钱包CPA余额不足。'
	          		          , btn: '确定'
	          		      	});
	            		    return false;
	        	    	}
	        	    },
	        	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	        	    	layer.open({
	        		          content: '账户钱包CPA余额不足，。'
	        		          , btn: '确定'
	          		      });
	        	    	flag = 0;
	          			return false;
	        	    }
	            });
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	console.log("没有账号信息2222");
        	layer.open({
		          content: '银行卡未绑定帮能交易挂单。'
		          , btn: '确定'
  		      });
  			return false;
	    }
    });
}

function mrCPA(id,count){
	var sec = localStorage.getItem("sec");
	if(sec!='1'){
		alert("未认证用户不能交易。");
		return false;
	}
	
	$.ajax({
		type: "post",
	      url: getAPIURL() + "bank/list",
	      dataType: "json",
	      data: {
	    	  "fensUserId":localStorage.getItem("uid"),
	    	  "pageSize":100,
	    	  "pageNum":0
	      },
	      success: function (data) {
	        var list = data.list;
	        console.log(list.length+"-------------dddd");
	        if (list.length <= 0) {
	        	console.log("没有账号信息");
	        	layer.open({
			          content: '银行卡未绑定不能交易挂单。'
			          , btn: '确定'
	  		      });
	  			return false;
	        }else{
	        	window.location.href = "../page/my_invest.html?"+id+"&mr";
	        }
	      },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	console.log("没有账号信息2222");
        	layer.open({
		          content: '银行卡未绑定帮能交易挂单。'
		          , btn: '确定'
  		      });
  			return false;
	    }
    });
}

function cxCPA(id){
	
	$.ajax({
	    type: "post",
	    url: getAPIURL() + "miner/record/detail",
	    dataType: "json",
	    data: {
	    	"id":id
	    },
	    success: function (data) {
	    	console.log(data);
	    	state = data.traderState;
	    	isDelete = data.isDelete;
	    	if(state!=0){
	    		layer.open({
			          content: '该单已被抢，不能撤销。'
			          , btn: '确定'
			      	});
			    return false;
	    	}else if(data.traderId != localStorage.getItem("uid")){
	    		layer.open({
			          content: '不能撤销他人订单。'
			          , btn: '确定'
			      	});
			    return false;
	    	}else{
	    		$.ajax({
	    		      type: "post",
	    		      url: getAPIURL() + "miner/record/updateRecord",
	    		      dataType: "json",
	    		      data:{
	    		    	  "id":id,
	    		    	  "isDelete":1
	    		      },
	    		      success: function (data) {
	    		        if (data.status==200) {
	    		          layer.open({
	    		            content: '操作成功。'
	    		            , btn: ['确定']
	    		            , yes: function (index) {
	    		              window.location.href = "../page/invest.html?tab=3";
	    		            }
	    		          });
	    		        } else {
	    		        	layer.open({
	    			            content: '操作失败，请检查网络服务。'
	    			            , btn: ['确定']
	    			            , yes: function (index) {
	    			  	          window.location.href = "../page/index.html";
	    			            }
	    			          });
	    		        }
	    		      },
	    		      headers: {
	    		        "Authorization": "Bearer " + getTOKEN()
	    		      }
	    		});
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	    	layer.open({
		          content: '交易拥堵，请稍后重新购买。1'
		          , btn: '确定'
		      	});
		    return false;
	    }
	});
}
