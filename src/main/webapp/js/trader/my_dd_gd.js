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
        	 console.log(data);
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
          		ahref = "<a style='font-weight:bold;color: #fff;' href='javascript:cxCPA("+list[i].id+");'>撤销</a>";
          		
              	var totalPriceUSA = list[i].traderCount * list[i].entrustPrice;

  			    txt1 += "<li>" + ( i + 1) +
  			    			"<p>类型：" + mm + "单</p>" +
	  			    		"<span>订单号："+ (list[i].orderNumber) +"</span>" +
	  			    		"<p>数目价格：" + list[i].traderCount + "CPA*" + list[i].entrustPrice + "$=" + totalPriceUSA + "$</p>" +
	  			    		"<span>总计人民币：" + totalPriceUSA * 6.5 + "元</span>" +
	  			    		"<span style='float:right;background: #E91E63;display: inline-block;width: 20%;height: 30px;text-align: center;line-height: 30px;'>" + ahref +"</span>" +
  			    		"</li>&nbsp;&nbsp;";
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
	    	state = data.traderState;
	    	isDelete = data.isDelete;
	    	if(state!=0){
	    		swal({
	    			  title: '该单已被抢，不能撤销。',
	    			  icon: "error",
	    			  button: "确定",
	    		});
			    return false;
	    	}else if(data.traderId != localStorage.getItem("uid")){
	    		swal({
	    			  title: '不能撤销他人订单。',
	    			  icon: "error",
	    			  button: "确定",
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
	    		        	swal({
		    		          	  title: '操作成功。',
		    		          	  type: 'success',
		    		          	  showCancelButton: true,
		    		          	  confirmButtonText: "确定", 
		    		          	  cancelButtonText: "取消",
		    		          	  closeOnConfirm: true, 
		    		          	  closeOnCancel: true
		    		          	}).then(function(isConfirm) {
		    		          	  if (isConfirm === true) {
		    		          		  window.location.href = "traderMyGD";
		    		          	  } else if (isConfirm === false) {
		    		          		  window.location.href = "traderMyGD";
		    		          	  } else {
		    		          		  window.location.href = "traderMyGD";
		    		          	  }
	    		          	});
	    		        } else {
	    		        	swal({
		    		          	  title: '操作失败，请检查网络服务',
		    		          	  type: 'error',
		    		          	  showCancelButton: true,
		    		          	  confirmButtonText: "确定", 
		    		          	  cancelButtonText: "取消",
		    		          	  closeOnConfirm: true, 
		    		          	  closeOnCancel: true
		    		          	}).then(function(isConfirm) {
		    		          	  if (isConfirm === true) {
		    		          		  window.location.href = "traderMyGD";
		    		          	  } else if (isConfirm === false) {
		    		          		  window.location.href = "traderMyGD";
		    		          	  } else {
		    		          		  window.location.href = "traderMyGD";
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
	    	swal({
	  			  title: '交易拥堵，请稍后重新购买。1',
	  			  icon: "error",
	  			  button: "确定",
	  		});
		    return false;
	    }
	});
}