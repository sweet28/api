(function() {
  //解析query字符串
  var request = {
    QueryString: function(val) {
      var uri = window.location.search;
      var re = new RegExp("" + val + "=([^&?]*)", "ig");
      return((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1)) : null);
    }
  };
  var detail_num = request.QueryString("bono");
  $.ajax({
    type: "GET",
    url: getAPIURL() + "Invest/" + detail_num,
    dataType: "json",
    data: null,
    success: function(data) {
      if (data.BO_TYPE == 'TRS') {
        var list = data.LendRewordTypes;
        if (list.length != 0){
          for (var i=0;i<list.length;i++){
            var str =' <li style="border-bottom: 1px solid #cccccc;"><div class="pi_border_list1">'
              + '<div class="pi_margin_top_style">'+ list[i].DESCRIPTION +'</div></div><div class="pi_border_list2" style="border-bottom: 0;">'
              + '<div class="pi_right_list_style">'+ list[i].EDITORVALUE +'</div></div></li>';
            $('#pi_wrap .pi_content_table').append(str);
          }
        }else {
          var str = '<li><div style="width: 100%;">'+ '<p style="text-align: center;margin-top: 0.1rem;font-size: 0.18rem;">暂无数据</p>' + '<div style="width: 100%;text-align: center"><img src="../img1/pic02.png" alt="" style="width: 40%;"></div>' +'</div></li>';
          $('#pi_wrap .pi_content_table').append(str);
        }
      } else {
        $("#proname").text(data.BO_TITLE);
        $(".start_money").text(data.BO_EACH_AMOUNT);
        // $("#jx_type").html("满标次日或募集结束次日起计息。" + data.BO_PAYMENT_WAY);
        $('#jx_type').html('<p>满标次日或募集结束次日起计息。'+ data.BO_PAYMENT_WAY +'</p>')
        $("#zj_yt").html(data.BO_FOR);
        for (var i=0;i<data.SLendRewordTypesTxt.length;i++){
          $("#projectinfo").html(data.SLendRewordTypesTxt[i].EDITORVALUE);
          var description = data.SLendRewordTypesTxt[i].DESCRIPTION;
          var dsStr = data.SLendRewordTypesTxt[i].EDITORVALUE;
          var resultStr = '<tr><td class="pi_tr">' +description + '</td><td class="pi_tr">' + dsStr +
            '</td></tr>';
          $('#intro_con').append(resultStr);
        }
      }
    }
  });
})();