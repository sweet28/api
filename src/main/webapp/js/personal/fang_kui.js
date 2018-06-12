var str;
var type;
(function(){
//	var test = window.location.search;
//	test = test.substr(test.length-1,1);
//	alert(test);
	var url = location.search; //获取url中"?"符后的字串 
	var theRequest = new Object(); 
	
	if (url.indexOf("?") != -1) { 
	   str = url.substr(1); 
	}
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "gd/"+str,
		 dataType: "json",
		 success:function(data){
			  if(data.status == 200){
				 var gd = data.data;
				 var html = "";
				 var time1 = gd.createDate;
				 time1 = fmtDate(time1);
				 $("#leixing").html(gd.type);
				 $("#danhao").html(gd.id);
				 $("#time").html(time1);
				 $("#conent").html(gd.problem);
				 type = gd.bak1;
				 shua();
			  }else{
				  alert(data.msg);
			  }
		 },
		 error:function(){
			 alert("系统错误");
		 }
	});
})();



function tijiao(){
	var conent = $("#jianyi").val();
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "gd/fan_kui",
		 dataType: "json",
		 data:{
			 "uid":localStorage.getItem("uid"),
			 "gd_id":str,
			 "fs_name":localStorage.getItem("name"),
			 "fk_tp":type,
			 "conent":conent
		 },
		 success:function(data){
			 if(data.status == 200){
				 shua();
				 alert("提交成功");
				 window.location.reload(); 
			 }else{
				 alert(data.msg);
			 }
		 },
		 error:function(){
			 alert("系统错误");
		 }
	});
}

//更新反馈
function shua(){
	$.ajax({
		 type: "post",
		 url: getAPIURL() + "gd/fk_list",
		 dataType: "json",
		 data:{
			 "gd_id":str,
		 },
		 success:function(data){
			 if(data.status == 200){
				 var list = data.data;
				 var html = "";
				 if(list != null){
					 for(var i = 0;i<list.length; i++){
						 var fasong = list[i].type;
						 //发送
						 if(fasong == 1){
							 html += "<div class='chat1 chat2' style='float:right;width:160px'>"+list[i].fankuiConent+"</div><br/>";
						 }else if(fasong == 2){//接受
							 html += "<div class='chat1'>"+list[i].fankuiConent+"</div><br/>";
						 }
					 }
				 }else{
//					 alert(data.msg);
				 }
				 $("#lie1").html(html);
			 }else{
				 $("#lie1").html("");
			 }
			
		 },
		 error:function(){
			 alert("系统错误");
		 }
	});
}

Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}