
function FundList() {
  var _$pageno = 0, _$fund_details_list, _$fund_details_wrap;
  (function () {
    _$fund_details_list = $("#fund_details_list");
    _$fund_details_wrap = $("#fund_details_wrap");
    var dropload = _$fund_details_wrap.dropload({
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
        _$pageno = 1;
        var uid = getUIDByJWT().unique_name;
        $.ajax({
          type: 'GET',
          url: getAPIURL() + "User/" + uid + "/Capital?pageNumber=" + _$pageno + "&pageRows=10",
          dataType: "json",
          data: null,
          success: function (data) {
            var list = data;
            var content = "";
            if (list.length <= 0) {
              _$fund_details_wrap.html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_fund.png'></div></li></ul>");
            }
            else {
              for (var i = 0; i < list.length; i++) {
                if (list[i].BoName == "+") {
                  content += '<li class="income list_item">';
                }
                else {
                  content += '<li class="defray list_item">';
                }
                content += '<div><span>' + list[i].TradeTime_Daysyymmdd + '</span><span>' + list[i].TradeTime_Dayshhmm + '</span></div>' +
                  '<div><span class="fund_details_icon"></span></div>' +
                  '<div  class="fund_details_money">' + list[i].TradeAmount + '元</div>' +
                  '<div><button class="fund_details_btn">' + list[i].TradeType + '</button></div>' +
                  '</li>';
              }
              setTimeout(function () {
                _$fund_details_list.html(content);
                // 每次数据加载完，必须重置
                dropload.resetload();
              }, 2000)
            }
          },
          error: function () {
            _$fund_details_wrap.html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_fund.png'></div></li></ul>");
            // 即使加载出错，也得重置
            dropload.resetload();
          },
          headers: {
            "Authorization":"Bearer "+getTOKEN()
          }
        });
      },
      loadDownFn: function (me) {
        _$pageno++;
        var uid = getUIDByJWT().unique_name;
        $.ajax({
          type: 'GET',
          url: getAPIURL() + "User/" + uid + "/Capital?pageNumber=" + _$pageno + "&pageRows=10",
          dataType: 'json',
          data: null,
          success: function (data) {
            var list = data;
            var content = "";
            if (list.length <= 0) {
              dropload.noData(true);
              dropload.resetload();
            }
            else {
              for (var i = 0; i < list.length; i++) {
                if (list[i].BoName == "+") {
                  content += '<li class="income list_item">';
                } else {
                  content += '<li class="defray list_item">';
                }
                content += '<div><span>' + list[i].TradeTime_Daysyymmdd + '</span><span>' + list[i].TradeTime_Dayshhmm + '</span></div>' +
                  '<div><span class="fund_details_icon"></span></div>' +
                  '<div  class="fund_details_money">' + list[i].TradeAmount + '元</div>' +
                  '<div><button class="fund_details_btn">' + list[i].TradeType + '</button></div>' +
                  '</li>';
              }
              _$fund_details_list.append(content);
              // 每次数据加载完，必须重置
              dropload.resetload();
            }
          },
          error: function (xhr, type) {
            /*加载到最后没有数据*/
            if(_$pageno==1){
              _$fund_details_wrap.html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_fund.png'></div></li></ul>");
            }else {
              dropload.noData(true);
            }

            // 即使加载出错，也得重置
            dropload.resetload();
          },
          headers: {
            "Authorization":"Bearer "+getTOKEN()
          }
        });
      }
    });
  })();
}

var fundList;
$(function () {
  fundList = new FundList();
});