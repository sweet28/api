(function(){
//	控制积分规则弹出层
  $(".jfgz").click(function(){
    $(".myjf_bounce").show();
  });
  $(".close_btn").click(function(){
    $(".myjf_bounce").hide();
  });
  var uid = getUIDByJWT().unique_name;
//	refresher.init({
//		id: "wrapper",
//		pullDownAction: function() {
//			setTimeout(function() {
//				pageNum = 0;
//				load(true);
//				wrapper.refresh();
//			}, 1000);
//		},
//		pullUpAction: function() {
//			setTimeout(function() {
//				load();
//				wrapper.refresh();
//			}, 2000);
//		}
//	});
  var pageNum = 0;
  var dropload = $("#wrapper").dropload({
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
    loadUpFn:function(me){
      pageNum = 0;
      load(true);
//      	dropload.resetload();
    },
    loadDownFn: function (me){
      load();
//      	dropload.resetload();
    },
//      autoLoad : false
  });

//	load();
  function load(flag){
    pageNum++;
    $.ajax({
      type:"get",
      url:getAPIURL()+ "user/"+uid+"/pointList?pageNumber="+pageNum+"&pageRows=8",
      dataType: "json",
      contentType: "application/json",
      success:function(data){
        //总积分和会员级别
        console.log(data);
        if(data.rtn == 3){
          if(pageNum==1){
            $(".vip").hide();
            $("#wrapper").hide();
            $(".no_jfrecord").show();
          }
        }else{
          $(".total_score").text(data[0].abliavailable+"分");
          $(".vip_class").text(data[0].UserLevel);
        }
//				var str = <li class="item clearfix">
//						<div class="part1">
//							<div class="plus_score"><span class="big">+20</span>分</div>
//							<div>2017-03-15</div>
//							<div>09:50</div>
//						</div>
//						<div class="part2">
//							投资流转标20170105000000001获得投资积分
//						</div>
//						<div class="part3">
//							<img src="../img/jfp.png" class="jf_img" />
//						</div>
//					</li>
//					<li class="item clearfix">
//						<div class="part1">
//							<div class="reduce_score"><span class="big">+20</span>分</div>
//							<div>2017-03-15</div>
//							<div>09:50</div>
//						</div>
//						<div class="part2">
//							投资流转标20170105000000001获得投资积分
//						</div>
//						<div class="part3">
//							<img src="../img/jfr.png" class="jf_img" />
//						</div>
//					</li>
        var html = "";
        for(var i = 1;i < data.length;i++){
          var score = data[i].point;
          var content = data[i].content;
          if(content == null){
            content = " ";
          }
          html += "<li class='item clearfix'><div class='part1'>";
          if(score > 0){
            score = "+"+score;
            html += "<div class='plus_score'>"
          }else{
            html += "<div class='reduce_score'>"
          }
          html += "<span class='big'>"+score+"</span>分</div>";
          html += "<div class='time'>"+data[i].ptime+"</div>";
          html += "<div class='time'>"+data[i].ptimehhmm+"</div></div>";
          html += "<div class='part2'>"+content+"</div><div class='part3'>";
          if(score > 0){
            html += "<img src='../img/jfp.png' class='jf_img' />";
          }else{
            html += "<img src='../img/jfr.png' class='jf_img' />";
          }
          html += "</div></li>";
        }
        if(flag){
          $(".bottom").empty().append(html);
          dropload.resetload();
          if(data.length < 9){
            $(".dropload-refresh").text("无更多内容");
          }
        }else{
          $(".bottom").append(html);
          dropload.resetload();
          if(data.rtn==3&&pageNum!=1){
            $(".dropload-refresh").text("无更多内容");
          }
          if(data.length < 9){
            $(".dropload-refresh").text("无更多内容");
          }
        }

      },
      headers: {
        "Authorization": "Bearer " + getTOKEN()
      }
    });
  }
})();