/**
 * Created by Administrator on 2017/8/8 0008.
 */
function siteTime() {
  setTimeout(siteTime, 1000);
  var seconds = 1000;
  var minutes = seconds * 60;
  var hours = minutes * 60;
  var days = hours * 24;
  var years = days * 365;
  var today = new Date();
  var todayYear = today.getFullYear();
  var todayMonth = today.getMonth() + 1;
  var todayDate = today.getDate();
  var todayHour = today.getHours();
  var todayMinute = today.getMinutes();
  var todaySecond = today.getSeconds();
  /* Date.UTC() -- 返回date对象距世界标准时间(UTC)1970年1月1日午夜之间的毫秒数(时间戳)
   year - 作为date对象的年份，为4位年份值
   month - 1-12之间的整数，做为date对象的月份
   day - 1-31之间的整数，做为date对象的天数
   hours - 0(午夜24点)-23之间的整数，做为date对象的小时数
   minutes - 0-59之间的整数，做为date对象的分钟数
   seconds - 0-59之间的整数，做为date对象的秒数
   microseconds - 0-999之间的整数，做为date对象的毫秒数 */
  var t1 = Date.UTC(2013, 09, 10, 00, 00, 00);  // 北京时间2016-12-1 00:00:00，注：由于上面定义的todayMonth已+1，所以此处的月份为实际月份（如：12月则设置为12）
  var t2 = Date.UTC(todayYear, todayMonth, todayDate, todayHour, todayMinute, todaySecond);
  var diff = t2 - t1;
  var diffYears = Math.floor(diff / years);
  var diffDays = Math.floor((diff / days) - diffYears * 365);
  var diffHours = Math.floor((diff - (diffYears * 365 + diffDays) * days) / hours);
  var diffMinutes = Math.floor((diff - (diffYears * 365 + diffDays) * days - diffHours * hours) / minutes);
  var diffSeconds = Math.floor((diff - (diffYears * 365 + diffDays) * days - diffHours * hours - diffMinutes * minutes) / seconds);
  $(".year").html(diffYears);
  diffDays = diffDays.toString();
  if(diffDays<10){
    $(".days3").show().html(diffDays);
  }
  else if(diffDays>=10&&diffDays<100){
    $(".days2").show().html(diffDays.slice(0, 1));
    $(".days3").show().html(diffDays.slice(1, 2));
  }
  else {
    $(".days1").show().html(diffDays.slice(0, 1));
    $(".days2").show().html(diffDays.slice(1, 2));
    $(".days3").show().html(diffDays.slice(2));
  }
  if (diffHours < 10) {
    diffHours = '0' + diffHours;
  }
  else {
    diffHours = diffHours.toString();
  }
  $(".hours1").html(diffHours.slice(0, 1));
  $(".hours2").html(diffHours.slice(1));
  if (diffMinutes < 10) {
    diffMinutes = '0' + diffMinutes;
  } else {
    diffMinutes = diffMinutes.toString();
  }
  $(".minutes1").html(diffMinutes.slice(0, 1));
  $(".minutes2").html(diffMinutes.slice(1));
  if (diffSeconds < 10) {
    diffSeconds = '0' + diffSeconds;
  }
  else {
    diffSeconds = diffSeconds.toString();
  }
  $(".seconds1").html(diffSeconds.slice(0, 1));
  $(".seconds2").html(diffSeconds.slice(1));
  //document.getElementById("sitetime").innerHTML=security+diffYears+"年 "+diffDays+"天 "+diffHours+"时 "+diffMinutes+"分 "+diffSeconds+"秒";
}

// 调用函数
siteTime();


var num1=91548;
var num2=69548;
var times=0;
var timer;

(function () {
  var date=new Date();
  var timestamp = Date.parse(date)/1000;
  $("#safeDataImg").attr("src","../img/safeDataImg"+(date.getMonth()+1)+".png?timestamp="+timestamp);
  $.ajax({
    type: "GET",
    url: getAPIURL() + '/home/datas',
    data:{timestamp:timestamp},
    dataType: "json",
    success: function (data) {
      if (data.Datas != undefined) {
        timer=setInterval(function () {
          times+=10;
          if(times>=200){
            clearInterval(timer);
            $("#turnover").html(data.Datas.turnover + "元");
            $("#income").html(data.Datas.income + "元");
            return false;
          }
          num1+=Math.floor(Math.random()*657+4);
          num2+=Math.floor(Math.random()*924+2);
          $("#turnover").html(num1 + "元");
          $("#income").html(num2 + "元");
        }, 60);
      }
      if (data.Reports != undefined && data.Reports.length > 0) {
        var content = '';
        for (var i = 0; i < data.Reports.length; i++) {
          var url="javascript:;";
          if(data.Reports[i].url!=""){
            url=data.Reports[i].url
          }
          content += '<li>' +
            '<a href="' + url+ '">' +
            '<img src="' + data.Reports[i].pic + '">' +
            '</a>' +
            '</li>';
        }
        $("#planList").html(content);
      }
    }
  });

  var str = window.navigator.userAgent;
  if (str.indexOf("jojojr") != -1) {
    $("#platformSection").css({top: 0})
  }
})();