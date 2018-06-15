var uid = localStorage.getItem("uid");
var url;
(function(){
	$.ajax({
		type : "post",
		url : getAPIURL() + "/mq/count",
		dataType : "json",
		data : {
			"id" : 1
		},
		success:function(data){
			if(data.status==200){
				$("#yxing").html(data.data);
			}
		}
	});
	$.ajax({
		type : "post",
		url : getAPIURL() + "/mq/count",
		dataType : "json",
		data : {
			"id" : 2
		},
		success:function(data){
			if(data.status==200){
				$("#exing").html(data.data);
			}
		}
	});
	$.ajax({
		type : "post",
		url : getAPIURL() + "/mq/count",
		dataType : "json",
		data : {
			"id" : 3
		},
		success:function(data){
			if(data.status==200){
				$("#sxing").html(data.data);
			}
		}
	});
	$.ajax({
		type : "post",
		url : getAPIURL() + "/mq/count",
		dataType : "json",
		data : {
			"id" : 4
		},
		success:function(data){
			if(data.status==200){
				$("#ssxing").html(data.data);
			}
		}
	});
})();



function tangkuan(num){
	
	$("#hid1").hide();
	$("#hid2").hide();
	$("#hid3").hide();
	$("#hid4").hide();
	
	if(num == 1){
		url = "mq/yx";
	}else if(num == 2){
		url = "mq/ex"
	}else if(num == 3){
		url = "mq/sx"
	}else if(num == 4){
		url = "mq/sxx"
	}
	
//	swal("通知", "马上上线，请留意今天的通知");
	
	swal({
		title : '是否确定购买',
		type : 'success',
		showCancelButton : true,
		confirmButtonText : "确定",
		cancelButtonText : "取消",
		closeOnConfirm : false,
		closeOnCancel : true,
		

	// showCancelButton: true,
	// closeOnConfirm: false,
	// animation: "slide-from-top",
	},function(isConfirm) {
		if (isConfirm === false) return false;
		$.ajax({
			type : "post",
			url : getAPIURL() + url,
			dataType : "json",
			data : {
				"uid" : uid,
				"id" : num
			},
			success:function(data){
				if(data.status == 200){
					swal("认购成功，等待匹配订单", "");
					if(num == 1){
						$("#yxing").html(data.data);
					}else if(num == 2){
						$("#exing").html(data.data);
					}else if(num == 3){
						$("#sxing").html(data.data);
					}else if(num == 4){
						$("#ssxing").html(data.data);
					}
					$("#hid1").show();
					$("#hid2").show();
					$("#hid3").show();
					$("#hid4").show();
				}else{
					swal(data.msg, "");
					$("#hid1").show();
					$("#hid2").show();
					$("#hid3").show();
					$("#hid4").show();
				}
			},
			error:function(data){
				swal("请检查网络是否畅通", "");
				$("#hid1").show();
				$("#hid2").show();
				$("#hid3").show();
				$("#hid4").show();
				return false;
			}
		});
	});
}
