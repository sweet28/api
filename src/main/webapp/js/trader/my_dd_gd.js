(function(){
  $(function(){
	  var tmp = getTimestamp();
      var rad = getRandom();
      var ton = getTom();
      var str = "trddi="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton;
      $.ajax({
    	  type: "post",
	      url: getAPIURL() + "kuangjy/jy/gdlb",
	      dataType: "json",
	      data:{
          	  "trddi":localStorage.getItem("uid"),
              "tmp":tmp,
              "rad":rad,
              "tom":ton,
              "token":commingSoon1(str)
	      },
        success: function (data) {
        	 var list = data;
          if (list.length <= 0) {
            var txtsNULL ="<p class='nothing'>无更多记录</p>";
            $("#a_miner").html(txtsNULL);
          } else {
        	  var txt1 = '';//,txt2='';
              for (var i = 0; i < list.length; i++) {
              	var cpatype ;
              	var mm;
              	
              	if(list[i].traderType==2){
              		mm="卖";
              	}
              	if(list[i].traderType==1){
              		mm="买"
              	}
              	if(list[i].traderState==1){
              		cpatype = "待付款";
              	}
              	if(list[i].traderState==2){
              		cpatype = "待确认收款";
              	}
              	if(list[i].traderState==3){
              		cpatype = "已完成";
              	}
              	if(list[i].traderState==0){
              		cpatype = "挂单中";
              	}
              	
              	var ahref;
          		ahref = "<a href='javascript:cxCPA("+list[i].id+");'>撤销</a>";
          		
              	
  			    txt1 += "<tr>" +
	  			    		"<td>"+mm+(list[i].id)+"</td>" +
	  			    		"<td>"+list[i].entrustPrice+"</td>" +
	  			    		"<td>"+list[i].traderCount+"</td>" +
	  			    		"<td>" + cpatype +"</td>" +
	  			    		"<td>" + ahref +"</td>" +
  			    		"</tr>";
              }
              $('#a_miner').html(txt1);
          }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){
        }
      });
  });
})();

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
	    		    	  "isDelete":1,
	    		    	  "attachment":localStorage.getItem("uid")
	    		      },
	    		      success: function (data) {
	    		        if (data.status==200) {
	    		          layer.open({
	    		            content: '操作成功。'
	    		            , btn: ['确定']
	    		            , yes: function (index) {
	    		              window.location.href = "../page/my_dd_gd.html?tab=3";
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