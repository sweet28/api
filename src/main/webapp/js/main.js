var uid = localStorage.getItem("uid");

function tangkuan(){
	swal({
		title : '是否确定购买',
		type : 'success',
		showCancelButton : true,
		confirmButtonText : "确定",
		cancelButtonText : "取消",
		closeOnConfirm : true,
		closeOnCancel : true
	}).then(function(isConfirm) {
		if (isConfirm === false) return false;
		
		var id = 1;
		$.ajax({
			type : "post",
			url : getAPIURL() + "mq/yx",
			dataType : "json",
			data : {
				"uid" : uid,
				"id" : id
			},
			success:function(data){
				if(data.status == 200){
					swal({
						title : "认购成功，等待确认订单",
						icon : "success",
						button : "确定",
					});
				}else{
					swal({
						title : data.msg,
						icon : "error",
						button : "确定",
					});
					return false;
				}
			},
			error:function(data){
				swal({
					title : "请检查网络是否畅通",
					icon : "error",
					button : "确定",
				});
				return false;
			}
		});
	});
}
