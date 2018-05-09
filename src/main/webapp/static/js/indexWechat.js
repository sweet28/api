/**
 * Created by Administrator on 2017/9/6 0006.
 */
(function () {
  var search = window.location.search ? window.location.search : "";
  var content = "";
  if (search.indexOf("content") != -1) {
    content = request.QueryString("content");
    loading.open();
    otherPartyFn("wx", content)
  }
})();
function otherPartyFn(type, content) {
  $.ajax({
    type: "POST",
    url: getAPIURL() + "Account/OtherParty",
    dataType: "json",
    data: JSON.stringify({type: type, content: content}),
    contentType: "application/json",
    success: function (data) {
      loading.close();
      if (data.rtn == 0) {
        localStorage.setItem("token", data.token);
        window.location.href = "../page/index.html";
        return false;
      }
      else {
        window.location.href = "../page/activateAccount.html?type="+type+"&content="+content;
      }
    },
    error: function (data) {
      loading.close();
      if (data.status == 404) {
        layer.open({
          content: "请求资源不存在",
          skin: 'msg',
          time: 2 //2秒后自动关闭
        });
      }
      else {
        layer.open({
          content: JSON.parse(data.responseText).Message,
          skin: 'msg',
          time: 2 //2秒后自动关闭
        });
      }
    }
  })
}