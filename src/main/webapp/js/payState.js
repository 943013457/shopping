$(function () {
    checkPay();


    function checkPay() {
        var ret = $.cookie('payStateCode');
        var payId_price = $.cookie('payID_price');
        if(null == ret || "" == ret || undefined == ret){
            ret="-1"
        }
        if (null == payId_price || "" == payId_price || undefined == payId_price) {
            ret = "-2";
        }
        var str = payId_price.split("&");
        var payId = str[0];
        var price = str[1];
        if ("SUCCESS" === ret) {
            $(".state_inmain").append("<div class=\"alert alert-success\" role=\"alert\">" +
                "<img class=\"pay_img\" src=\"/image/ture_icon.png\">" +
                "<span class=\"pay_font\">支付成功</span></div>" +
                "<div class=\"pay_list\">" +
                "<p><span>订单编号：</span><span class=\"pay_uuid\">" + payId + "</span></p>" +
                "<p><span>本单合计：</span><span class=\"pay_price\">" + price + "</span> <span>元</span></p></div>" +
                "<div class=\"pay_btn\">" +
                "<button type=\"button\" class=\"btn btn-default buy_btn\">继续购买</button>" +
                "<button type=\"button\" class=\"btn btn-default order_btn\">查看我的订单</button></div>")
        } else {
            $(".state_inmain").append("<div class=\"alert alert-warning\" role=\"alert\">" +
                "<img class=\"pay_img\" src=\"/image/false_icon.png\">" +
                "<p class=\"pay_error_font\">支付错误，请查询订单是否成功支付</p>" +
                "<p class=\"pay_error_font\">如有疑问请联系客服</p>" +
                "<p class=\"pay_error_font\">错误码：" + ret + "</p></div>" +
                "<div class=\"pay_btn\">" +
                "<button type=\"button\" class=\"btn btn-default order_btn\">查看我的订单</button>" +
                "</div>")
        }
    }

    $(".state_inmain").on("click",".order_btn",function () {
        window.location.href = "/myorder";
    })

    $(".state_inmain").on("click",".buy_btn",function () {
        window.location.href = "/index";
    })

})