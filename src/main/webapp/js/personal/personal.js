(function () {
  //分离开整数和小数部分
  function seprate(number) {
    var arr = number.toString().split(".");
    var arr1 = [];
    var num = arr[0] + ".";
    arr1.push(num);
    var num1 = "";
    if (arr[1] == undefined) {
      num1 = "00"
    } else {
      num1 = arr[1];
    }
    arr1.push(num1);
    return arr1;
  }
  
//  $.ajax({
//	    type: "post",
//	    url: getAPIURL() + "user/fens/selectFensUserGrade",
//	    dataType: "json",
//	    data: {
//	    	"sh": localStorage.getItem("phone"),
//	    	 "uid": localStorage.getItem("uid")
//	    },
//	    success: function (data) {
//	    },
//	    error: function (XMLHttpRequest, textStatus, errorThrown) {
//
//	    }
//	  });

  //	先判断是否登录
  var flag = checkLogin();
  
  var tmp = getTimestamp();
  var rad = getRandom();
  var ton = getTom();
  var str = "uid="+localStorage.getItem("uid")+"tmp="+tmp+"rad="+rad+"tom="+ton;
  //	调用相关金额的接口
  $.ajax({
    type: "post",
    url: getAPIURL() + "user/qb/list",
    dataType: "json",
    data: {
    	"uid":localStorage.getItem("uid"),
        "tmp":tmp,
        "rad":rad,
        "tom":ton,
        "token":commingSoon1(str)
    },
    success: function (data) {
    	var dd = data.data;
    	if(data.status==200){
    		//可用余额
    	      var balance = dd.ableCpa;
    	      var balanceArr = seprate(balance);
    	      $("#balance_num").text(balanceArr[0]);
    	      $("#balance_dec").text(balanceArr[1]);
    	      //待收总额
    	      var waitNum = dd.lockCpa;
    	      var waitInterestArr = seprate(waitNum);
    	      $("#waitNum_num").text(waitInterestArr[0]);
    	      $("#waitNum_dec").text(waitInterestArr[1]);
    	      //累计收益
    	      var returnIn = dd.ableCpa + dd.lockCpa;
    	      var returnInterestArr = seprate(returnIn);
    	      $("#returnIn_num").text(returnInterestArr[0]);
    	      $("#returnIn_dec").text(returnInterestArr[1]);
    	      
    	      $("#uname").text("欢迎，"+localStorage.getItem("name"));
    	}else{
    		  $("#balance_num").text(0);
    	      $("#balance_dec").text(".00000");
    	      $("#waitNum_num").text(0);
    	      $("#waitNum_dec").text(".00000");
    	      $("#returnIn_num").text(0);
    	      $("#returnIn_dec").text(".00000");
    	}
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
      $("#balance_num").text(0);
      $("#balance_dec").text(".00000");
      $("#waitNum_num").text(0);
      $("#waitNum_dec").text(".00000");
      $("#returnIn_num").text(0);
      $("#returnIn_dec").text(".00000");

    }
  });
  
//  function comptime() {
  console.log(9999999);
  
    if(localStorage.getItem("fensteampower")!=null){
		$("#suanli").html(localStorage.getItem("fensteampower"));
	}
	if(localStorage.getItem("fensteamnum")!=null){
		$("#fensteamNum").html(localStorage.getItem("fensteamnum"));
	}
	if(localStorage.getItem("gradNum")!=null){
		  var grade2 = localStorage.getItem("gradNum");
	  	  if(grade2==0){
	  		  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade0.png">'+localStorage.getItem("fensgrade"));
	  	  }
	  	  else if(grade2==1){
	  		  grade2 = "普通节点";
	  		  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade1.png">'+localStorage.getItem("fensgrade"));
	  	  }
	  	  else if(grade2==2){
	  		  grade2 = "高级节点";
	  		  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade2.png">'+localStorage.getItem("fensgrade"));
	  	  }
	  	  else if(grade2==3){
	  		  grade2 = "超级节点";
	  		  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade3.png">'+localStorage.getItem("fensgrade"));
	  	  }
	}
	if(localStorage.getItem("endTime")!=null){
		$("#endTime").html(localStorage.getItem("endTime"));
	}
  
    $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/getFensUserGradeLittle",
	      dataType: "json",
	      data: {
	    	  "sh": localStorage.getItem("phone"),
	    	  "uid": localStorage.getItem("uid")
	      },
	      success: function (data) {
	    	  console.log(data);
			  $("#suanli").html(data.suanli);
			  $("#fensteamNum").html(data.fensteamNum);
			  
			  localStorage.setItem("fensteampower",data.suanli);
			  localStorage.setItem("fensteamnum",data.fensteamNum);
			  
	      },error:function(){
	    	  console.log(333);
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
    });
    
    $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/getFensUserGrade",
	      dataType: "json",
	      data: {
	    	  "sh": localStorage.getItem("phone"),
	    	  "uid": localStorage.getItem("uid")
	      },
	      success: function (data) {
	    	  console.log(data);
//			  $("#suanli").html(data.suanli);
			  //$("#grade").html(data.grade);
			  var grade = "精英粉丝";
			  var gradNum = data.grade;
			  if(data.grade==0){
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade0.png">'+grade);
			  }
			  else if(data.grade==1){
				  grade = "普通节点";
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade1.png">'+grade);
			  }
			  else if(data.grade==2){
				  grade = "高级节点";
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade2.png">'+grade);
			  }
			  else if(data.grade==3){
				  grade = "超级节点";
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade3.png">'+grade);
			  }
			  
//			  $("#fensteamNum").html(data.fensteamNum);
			  var endTime;
			  if(data.isGradeCan == 'yes'){
				  endTime = "冲击<"+data.nextgrade+">截止时间:"+data.endTime;
				  $("#endTime").html(endTime);
			  }else{
				  endTime = "在规定时间内无法冲击任何等级";
				  $("#endTime").html(endTime);
			  }
			  
//			  localStorage.setItem("fensteampower",data.suanli);
//			  localStorage.setItem("fensteamnum",data.fensteamNum);
			  localStorage.setItem("fensgrade",grade);
			  localStorage.setItem("endTime",endTime);
			  localStorage.setItem("gradNum",gradNum);
			  
	      },error:function(){
	    	  console.log(333);
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
  });
    
//    $("#grade").html("区块数据今天全球同步完成");
//    $("#grade").html("区块数据今天全球同步完成");
//    $("#suanli").html("区块数据今天全球同步完成");
//	$("#endTime").html("区块数据今天全球同步完成");
//	
//	localStorage.setItem("fensteampower","区块数据今天全球同步完成");
//    localStorage.setItem("fensteamnum","区块数据今天全球同步完成");
//    localStorage.setItem("fensgrade","区块数据今天全球同步完成");
//    localStorage.setItem("endTime","区块数据今天全球同步完成");

})();

