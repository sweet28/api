var uid = localStorage.getItem("uid");
var url;
function tangkuan(num){
	if(num == 1){
		url = "mq/yx";
	}else if(num == 2){
		url = "mq/yx"
	}else if(num == 3){
		url = "mq/sx"
	}
	swal({
		title : '是否确定购买',
		type : 'success',
		showCancelButton : true,
		confirmButtonText : "确定",
		cancelButtonText : "取消",
		closeOnConfirm : false,
		closeOnCancel : true,
		
//		showCancelButton: true,   
//		closeOnConfirm: false,   
//		animation: "slide-from-top",
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
					swal("认购成功，等待确认订单", "");
				}else{
					swal(data.msg, "");
				}
			},
			error:function(data){
				swal("请检查网络是否畅通", "");
				return false;
			}
		});
	});
}
