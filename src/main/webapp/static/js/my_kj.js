/**
 * Created by tim on 2016/6/21.
 */
function Gift() {
  var self = this, _$gift;
  var uid = localStorage.getItem("uid");

  function comptime() {
    $.ajax({
      type: "post",
      url: getAPIURL() + "fenuser/miner/minerAList",
      dataType: "json",
      data: {
    	  "fensUserId":uid
      },
      success: function (data) {
    	  console.log(data);
        var list = data;
        if (list.length <= 0) {
          $(".recommendedRewards_wrap").html("<ul><li class='nothing'><p>暂无记录</p><div class='noInformation'><img src='../img/no_reward.png'></div></li></ul>");
        } else {
          var txt1 = '<thead>' +
            '<tr>' +
            '<td >奖励日期</td>' +
            '<td>奖励产品</td>' +
            '<td>发放状态</td>' +
            '<td>物流编号</td>' +
            '</tr>' +
            '</thead><tbody >';
          for (var i = 0; i < list.length; i++) {
            txt1 += '<tr>' +
              '<td class="first">' + list[i].usedtime + '</td>' +
              '<td>' + list[i].name + '</td>' +
              '<td>' + list[i].start + '</td>' +
              '<td class="last">' + list[i].logisticsno + '</td>' +
              '</tr>';
          }
          txt1 += "</tbody>";
          _$gift.html(txt1);
        }
      }, headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }

  (function () {
    _$gift = $("#gift");
    comptime();
  })();
}
var gift;
$(function () {
  gift = new Gift();
});