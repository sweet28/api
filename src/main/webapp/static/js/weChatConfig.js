/**
 * Created by Administrator on 2017/9/6 0006.
 */
var wechatLoginObj = {
  redirect_uri: encodeURIComponent(getAPIURL() + "WeiXin/getaccesstokeninfo"),
  appid: "wx7dc262abfad4468e",
  state: "1"
};
/*wechat*/
function weChatLoginFn(wechatLoginObj) {
  window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
    "appid=" + wechatLoginObj.appid +
    "&redirect_uri=" + wechatLoginObj.redirect_uri +
    "&response_type=code" +
    "&scope=snsapi_base" +
    "&state=" + wechatLoginObj.state + "#wechat_redirect";
}