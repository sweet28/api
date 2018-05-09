/**
 * Created by tim on 2016/6/21.
 */
function Invest() {

  var self = this, _$invest;

  function comptime() {
    var uid = getUIDByJWT().unique_name;
    $.ajax({
      type: "GET",
      url: getAPIURL() + "User/" + uid + "/getrewardinvestmentlist?pageNumber=1&pageRows=2000",
      dataType: "json",
      data: null,
      success: function (data) {
        var list = data;
        if (list.length <= 0) {
          $(".recommendedRewards_wrap").html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_reward.png'></div></li></ul>");

        }
        else {
          var txt1 = '<thead>' +
            '<tr>' +
            '<td>发放时间</td>' +
            '<td>奖励金额(元)</td>' +
            '<td>奖励类型</td>' +
            '</tr>' +
            '</thead><tbody >';
          for (var i = 0; i < list.length; i++) {
            txt1 += '<tr>' +
              '<td class="first">' + list[i].tradetime + '</td>' +
              '<td class="rewardMoney">' + isWholeNumber(list[i].amount) + '</td>' +
              '<td class="last">' + list[i].catename + '</td>' +
              '</tr>';
          }
          txt1 += "</tbody>";
          _$invest.html(txt1);
        }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
  function isWholeNumber(num) {
    if(num.indexOf(".")==-1){
      num+=".00";
    }
    return num;
  }

  (function () {
    _$invest = $("#invest");
    comptime();
  })();
}

var invest;
$(function () {
  invest = new Invest();
});