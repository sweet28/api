(function(){
//  var uid = getUIDByJWT().unique_name;
//  $.ajax({
//    type:"get",
//    url:getAPIURL()+ "user/"+uid+"/pointList?pageNumber=1"+"&pageRows=1",
//    dataType: "json",
//    contentType: "application/json",
//    success:function(data){
//      //无积分用户时
//      if(data.rtn == 3){
//        $(".vip_class").hide();
//      }else{
//        $(".vip_detail").text(data[0].UserLevel);
//      }
//
//    },
//    error:function(){
//      $(".vip_class").hide();
//    },
//    headers: {
//      "Authorization": "Bearer " + getTOKEN()
//    }
//  })
})();