/**
 * Created by Administrator on 2017/9/18 0018.
 */
/*WeChat分享*/
//step1 配置
(function () {
  $.ajax({
    type: "GET",
    url: getAPIURL() + "WeiXin/getticketinfo?surl=" + encodeURIComponent(location.href.split('#')[0]),
    dataType: "json",
    success: function (data) {
      if ($.trim(data.timestamp) != "" && $.trim(data.nonceStr) != "" && $.trim(data.signature) != "") {
        wx.config({
          debug: false,
          appId: 'wx7dc262abfad4468e',
          timestamp: data.timestamp,
          nonceStr: data.nonceStr,
          signature: data.signature,
          jsApiList: [
            // 所有要调用的 API 都要加到这个列表中
            'checkJsApi',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone',
            'hideMenuItems',
            'showMenuItems'
          ]
        });
      }
    },
    error: function (data) {
      loading.close();
      if (data.status == 404) {
        layer.open({
          content: "请求资源不存在",
          skin: 'msg',
          time: 2 //2秒后自动关闭
        });
      }
      else {
        layer.open({
          content: JSON.parse(data.responseText).Message,
          skin: 'msg',
          time: 2 //2秒后自动关闭
        });
      }
    }
  });

//step2 判断当前客户端版本是否支持指定JS接口
  wx.checkJsApi({
    jsApiList: ['checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    success: function (res) {
      // 以键值对的形式返回，可用的api值true，不可用为false
      // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
    }
  });

  wx.ready(function () {
    wx.showMenuItems({
      menuList: ["menuItem:share:appMessage", "menuItem:share:timeline", "menuItem:share:qq", "menuItem:share:weiboApp", "menuItem:favorite", "menuItem:share:QZone"] // 要显示的菜单项，所有menu项见附录3
    });
    wx.hideMenuItems({
      menuList: ["menuItem:editTag", "menuItem:delete", "menuItem:originPage", "menuItem:readMode", "menuItem:share:email", "menuItem:share:brand"]
    });
    //分享到朋友圈
    wx.onMenuShareTimeline({
      title: '【投呗生活】我每个月的伙食费全免，各种费用全免攻略，进投呗生活来看看！', // 分享标题
      link: baseUrl() + "page/joinUs.html?recommander=" +encodeURI(JSON.parse(localStorage.getItem("account"))),
      imgUrl: baseUrl() + 'img/shareLinkIcon.png', // 分享图标
      success: function () {
        // 用户确认分享后执行的回调函数
      },
      cancel: function () {
        // 用户取消分享后执行的回调函数
      }
    });
    //分享给朋友
    wx.onMenuShareAppMessage({
      title: '投呗生活', // 分享标题
      link: baseUrl() + "page/joinUs.html?recommander=" +encodeURI(JSON.parse(localStorage.getItem("account"))),
      desc: '【投呗生活】我每个月的伙食费全免，各种费用全免攻略，进投呗生活来看看！',
      imgUrl: baseUrl() + 'img/shareLinkIcon.png', // 分享图标
      type: 'link', // 分享类型,music、video或link，不填默认为link
      dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
      success: function () {
        // 用户确认分享后执行的回调函数
      },
      cancel: function () {
        // 用户取消分享后执行的回调函数
      }
    });
    //分享给QQ好友
    wx.onMenuShareQQ({
      title: '投呗生活', // 分享标题
      link: baseUrl() + "page/joinUs.html?recommander=" + encodeURI(JSON.parse(localStorage.getItem("account"))),
      desc: '【投呗生活】我每个月的伙食费全免，各种费用全免攻略，进投呗生活来看看！',
      imgUrl: baseUrl() + 'img/shareLinkIcon.png', // 分享图标
      success: function () {
        // 用户确认分享后执行的回调函数
      },
      cancel: function () {
        // 用户取消分享后执行的回调函数
      }
    });
    //分享到腾讯微博
    wx.onMenuShareWeibo({
      title: '投呗生活', // 分享标题
      link: baseUrl() + "page/joinUs.html?recommander=" + encodeURI(JSON.parse(localStorage.getItem("account"))),
      desc: '【投呗生活】我每个月的伙食费全免，各种费用全免攻略，进投呗生活来看看！',
      imgUrl: baseUrl() + 'img/shareLinkIcon.png', // 分享图标
      success: function () {
        // 用户确认分享后执行的回调函数
      },
      cancel: function () {
        // 用户取消分享后执行的回调函数
      }
    });
    //分享到QQ空间
    wx.onMenuShareQZone({
      title: '投呗生活', // 分享标题
      link: baseUrl() + "page/joinUs.html?recommander=" + encodeURI(JSON.parse(localStorage.getItem("account"))),
      desc: '【投呗生活】我每个月的伙食费全免，各种费用全免攻略，进投呗生活来看看！',
      imgUrl: baseUrl() + 'img/shareLinkIcon.png', // 分享图标
      success: function () {
        // 用户确认分享后执行的回调函数
      },
      cancel: function () {
        // 用户取消分享后执行的回调函数
      }
    });
  });

//验证失败
  wx.error(function (res) {

  });
})();
