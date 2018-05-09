/**
 * Created by Administrator on 2017/4/27 0027.
 */
function FundDetailFilte() {
  var self = this, _$searchInput, _$searchType;

  _next = function () {
    var pattern = /^[\u4e00-\u9fa5]*$/;
    _$searchType = $.trim(_$searchInput.val());
    if (_$searchType == "") {
      layer.open({
        content: '请输入资金类型'
        , skin: 'msg'
        , time: 2 //2秒后自动关闭
      });
      _$searchInput.focus();
      return false;
    }
    else if (!pattern.test(_$searchType)) {
      layer.open({
        content: '请输入汉字'
        , skin: 'msg'
        , time: 2 //2秒后自动关闭
      });
      _$searchInput.focus();
      return false;
    }
    else {
      window.location.href = "fund_filter_details.html#" + _$searchType;
    }
  };

  this.next = _next;

  (function () {
    _$searchInput = $("#searchInput");
    _$searchType = $.trim(_$searchInput.val());
  })()
}
var fundDetailFilter;
$(function () {
  fundDetailFilter = new FundDetailFilte();
});