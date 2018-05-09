/**
 * Created by tim on 2016/6/21.
 */
function Recommend() {
  var self = this, _$recommend;

  function comptime() {
    var uid = getUIDByJWT().unique_name;
    $.ajax({
      type: "GET",
      url: getAPIURL() + "User/" + uid + "/getrewardpromoteList?pageNumber=1&pageRows=200",
      dataType: "json",
      data: null,
      success: function (data) {
        var list = data;
        if (list.length <= 0) {
          $(".recommendedRewards_wrap").html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_reward.png'></div></li></ul>");
        } else {
          var txt1 = '<thead>' +
            '<tr>' +
            '<td>奖励日期</td>' +
            '<td>奖励比例</td>' +
            '<td>奖励金额（元）</td>' +
            '</tr>' +
            '</thead>' +
            '<tbody>';
          for (var i = 0; i < list.length; i++) {
            txt1 += '<tr>' +
              '<td class="first">'+ list[i].rewardtime +'</td>' +
              '<td>'+list[i].rate+'</td>' +
              '<td class="last">￥ '+isWholeNumber(list[i].amount)+'</td>' +
              '</tr>';
          }
          txt1+='</tbody>';
          _$recommend.html(txt1);
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
    _$recommend = $("#recommend");
    comptime();
  })();
}
var recommend;
$(function () {
  recommend = new Recommend();
});