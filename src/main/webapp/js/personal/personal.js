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
    $.ajax({
	      type: "post",
	      url: getAPIURL() + "user/fens/selectFensUserGrade",
	      dataType: "json",
	      data: {
	    	  "sh": localStorage.getItem("phone"),
	    	  "uid": localStorage.getItem("uid")
	      },
	      success: function (data) {
	    	  console.log(data);
			  $("#suanli").html(data.suanli);
			  //$("#grade").html(data.grade);
			  if(data.grade==0){
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade0.png">');
			  }
			  else if(data.grade==1){
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade1.png">');
			  }
			  else if(data.grade==2){
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade2.png">');
			  }
			  else if(data.grade==3){
				  $("#grade").html('<img alt="" src="'+getAPIURL()+'/imagenew/grade3.png">');
			  }
			  
			  $("#fensteamNum").html(data.fensteamNum);
			  $("#endTime").html(data.endTime);
	      },error:function(){
	    	  console.log(333);
	      }, headers: {
	        "Authorization": "Bearer " + getTOKEN()
	      }
    });
//	  }

})();

