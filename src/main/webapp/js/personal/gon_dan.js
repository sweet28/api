var imgUrl;

$("#file0").change(function() {
	var objUrl = getObjectURL(this.files[0]);
	if (objUrl) {
		var files = $("#file0").get(0).files[0]; // 获取file控件中的内容
		if (files.size < 1024 * 1024 * 2) {
			// 在这里修改图片的地址属性
			$("#img0").attr("src", objUrl);
			var fd = new FormData();
			fd.append("errPic", files);
			var url = getAPIURL() + "pic/upload2";
			$.ajax({
				type : "POST",
				contentType : false, // 必须false才会避开jQuery对 formdata 的默认处理 ,
				// XMLHttpRequest会对 formdata 进行正确的处理
				processData : false, // 必须false才会自动加上正确的Content-Type
				url : url,
				data : fd,
				dataType : "json",
				success : function(data) {
					if (data.error == 0) {
						imgUrl = data.url;
					}else{
						swal({
				      		  title: data.message,
				      		  icon: "error",
				      		  button: "确定",
			      	    });
					}
				},
				error : function(data) {
					alert.log("请输入jpg和png和jpeg格式图片");
				}
			});
		} else {
			alert("上传图片不能超过2M，请重新上传.");
		}
	}
});


function sb(){
	var leixin = $('#typecpa').val();
	var conent = $('#conent').val();
	var uid = localStorage.getItem("uid");
	
	if(leixin == null){
		alert("请选着问题类型");
	};
	if(leixin == "mr"){
		leixin = "1"
	}
	if(leixin == "mc"){
		leixin = "2"
	}
	if(leixin == "mt"){
		leixin = "3"
	}
	if(conent == null || conent == ''){
		alert("请输入问题描述");
	};
	$.ajax({
		 type: "post",
	     url: getAPIURL() + "/gd/add",
	     dataType: "json",
		 data: {
			 "uid" : uid,
			 "tp" : leixin,
			 "pb" : conent,
			 "img" : imgUrl
		 },
		 success:function(data){
			 if(data.status == 200){
			   alert("提交成功");
			 }else{
				 alert("提交失败");
			 }
		 },
		 error:function(data){
			 alert("系统错误");
		 }
	});
}

$("#tijiao").click(function(){
	sb();
});

//建立一個可存取到該file的url
function getObjectURL(file) {
	var url = null;
	// 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
	if (window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}
