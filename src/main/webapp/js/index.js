$(function () {


    $(".list_li").hover(function () {
        $(this).addClass("selected");
    }, function () {
        $(this).removeClass("selected");
    })
})