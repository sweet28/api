/**
 * Created by Administrator on 2017/9/7 0007.
 */
var loading={
  content:'<div class="modal" id="modal"  style="display: block;"><img src="../img/loading.svg" class="loadingImg" ></div>',
  open:function () {
    $("body").append(this.content)
  },
  close:function () {
    $("#modal").remove();
  }
};