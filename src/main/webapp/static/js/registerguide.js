(function(){
  $(".question").click(function(){
    $(this).next().toggle();
    $(this).find("i").toggleClass("active");
  });
})();