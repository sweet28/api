
function click(){
	var phone = $("#phone").val();
	console.log
	$.ajax({
		type : "post",
		url : getAPIURL() + "quanbaoli/sms/code/send",
		dataType : "json",
		data:{
			"mobile":phone
		},
		success:function(data){
			console.log(data);
		},
		error:function(){
			alert("接口调用出错");
		}
	});
}

$("#tijiao").click(function(){
	click();
});