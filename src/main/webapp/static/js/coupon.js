(function () {
	
	var flag = checkLogin();
	
  var hash = location.hash.substr(1);
  if(hash=="tier"){
    $(".sp").hide();
  }
  
  var phone = Number(getPhone());
//  console.log("phone:::"+phone);
  var yqURL = "http://cpa.artforyou.cn:8088/api/static/page/reg.html?yqurl=";
  var yqValue = $("#yaoqing_url");
  
  if(phone!=null){
	  yqValue.html(yqURL + phone.toString(8));
  }
  
//  var uid = getUIDByJWT().unique_name;
//  $.ajax({
//    type: "GET",
//    url: getAPIURL() + "User/" + uid + "/getcashcouponlist?pageNumber=" + 1 + "&pageRows=1000",
//    dataType: "json",
//    data: null,
//    success: function (data) {
//      var html = "";
//      if (data.list.length <= 0) {
//        $(".no_coupon").show();
//        $(".has_coupon").hide();
//        $(".my_coupon").text("我的优惠券（0）");
//      }
//      else {
//        $(".my_coupon").text("我的优惠券（" + data.Message + "）");
//        //判断是否使用
//        for (var i = 0; i < data.list.length; i++) {
//          //已经使用
//          if (data.list[i].CC_STATUS == 1) {
//            html += "<li><a href='javascript:;' class='coupon_used clearFix'><span class='left'>";
//          } else {
//            html += "<li><a href='javascript:;' class='coupon_unused clearFix'><span class='left'>";
//          }
//          if (data.list[i].CC_TYPE != "加息券") {
//            html += "<span class='coupon_top'><span class='coupon_money'>" + data.list[i].CC_CASH + "元</span></span>";
//
//          } else {
//            html += "<span class='coupon_top'><span class='coupon_money'>" + data.list[i].CC_CASH + "%</span></span>";
//          }
//          /*if(data.list[i].CC_TYPE == "红包"){
//           html += "<span class='coupon_top'><span class='coupon_money'>"+data.list[i].CC_CASH+"元</span></span>";
//           }else if(data.list[i].CC_TYPE=="加息劵"){
//           html += "<span class='coupon_top'><span class='coupon_money'>"+data.list[i].CC_CASH+"%</span></span>";
//           }else {
//           html += "<span class='coupon_top'><span class='coupon_money'>"+data.list[i].CC_CASH+"元</span></span>";
//           }*/
//
//          html += "<span class='coupon_bot'><span class='coupon_name'>" + data.list[i].CC_TYPE + "</span></span></span>";
//          html += "<span class='left'><span class='coupon_top'><span class='coupon_desc'><span>" + data.list[i].CC_DESC;
//          html += "</span></span><span class='coupon_icon'></span></span>";
//          var nowTime = new Date().getTime();
//          var endTime = new Date(data.list[i].CC_END_TIME).getTime();
//          var disDay = Math.ceil((endTime - nowTime) / (24 * 60 * 60 * 1000));
//          /*使用未使用 0 未使用  1 使用*/
//          if (data.list[i].CC_STATUS == 1) {
//            html += "<span class='coupon_bot'><span class='coupon_timeLimit'><span></span>";
//            html += "<span>使用时间：" + data.list[i].CC_USED_TIME.substring(0, 10) + "</span></span></span></span></a></li>";
//
//          }
//          else {
//            if (disDay <= 0) {
//              html += "<span class='coupon_bot'><span class='coupon_timeLimit'><span></span>";
//            } else if (disDay > 0 && disDay <= 7) {
//              html += "<span class='coupon_bot'><span class='coupon_timeLimit'><span>剩余" + disDay + "天过期</span>";
//            } else {
//              html += "<span class='coupon_bot'><span class='coupon_timeLimit'><span></span>";
//            }
//            html += "<span>有效期至：" + data.list[i].CC_END_TIME.substring(0, 10) + "</span></span></span></span></a></li>";
//          }
//
//          /*   if(disDay <= 0){
//           html += "<span class='coupon_bot'><span class='coupon_timeLimit'><span></span>";
//           }else if(disDay >0 && disDay <= 7 ){
//           html += "<span class='coupon_bot'><span class='coupon_timeLimit'><span>剩余"+disDay+"天过期</span>";
//           }else{
//           html += "<span class='coupon_bot'><span class='coupon_timeLimit'>";
//           }*/
//          /*if(data.list[i].CC_STATUS == 1){
//           html += "<span>使用时间："+ data.list[i].CC_USED_TIME.substring(0,10)+"</span></span></span></span></a></li>";
//           }else{
//           html += "<span>有效期至："+ data.list[i].CC_END_TIME.substring(0,10)+"</span></span></span></span></a></li>";
//           }*/
//
//        }
//        $(".has_coupon > ul").append(html);
//      }
//    },
//    headers: {
//      "Authorization": "Bearer " + getTOKEN()
//    }
//  });
})();