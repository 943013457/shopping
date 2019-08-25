$(function () {
    $.ajax({
        url: "/getMyorder",
        type: "get",
        datatype: "text",
        success: function (data) {
            var jsons = $.parseJSON(data);
            for (var index in jsons) {
                showItems(jsons[index]);
            }
        }
    });

    //订单
    function showItems(json) {
        var id = json['OrderId'];
        var CreateDate = json['CreateDate'];
        var image = json['image'];
        var name = json['name'];
        var productId = json['ProductId'];
        var Price = json['Price'];
        var Number = json['Number'];
        var StateTitle;
        var classify;
        //PAYMENT 待付款 SEND 待发货 AFFIRM 确认收货 REVIEW 待评价
        var state = json['State'];
        switch (state) {
            case "PAYMENT":
                StateTitle = "待付款";
                classify = $("#fk");
                break;
            case "SEND":
                StateTitle = "待发货";
                classify = $("#fh");
                break;
            case "AFFIRM":
                StateTitle = "确认收货";
                classify = $("#sh");
                break;
            case "REVIEW":
                StateTitle = "待评价";
                classify = $("#pj");
                break;
        }

        var text =
            "<div class='panel panel-default item_content'>" +
            "<div class='panel-heading title_text'>" +
            "<span class='ProductId_font'>订单号：</span>" +
            "<span class='ProductId_number'>" + id + "</span>" +
            "<span class='CreateTime'>" + CreateDate + "</span>" +
            "</div>" +
            "<div class='panel-body'>" +
            "<div class='product_item_img'>" +
            "<img src=" + image + ">" +
            "</div>" +
            "<div class='product_item_title'>" +
            "<a href='/items/" + productId + "'>" + name + "</a>" +
            "</div>" +
            "<div class='product_item_price'>￥" + Price +
            "</div>" +
            "<div class='product_item_count'>" + Number +
            "</div>" +
            "<div class='product_item_all_price'>￥" + parseFloat(Price) * parseInt(Number) +
            "</div>" +
            "<div class='product_item_btn'>" +
            "<input class='state' type='hidden' value=" + state + "/>" +
            "<button type=button class='btn btn-info payment'>" + StateTitle +
            "</button>" +
            "</div>" +
            "</div>" +
            "</div>";

        $("#all").append(text);
        $(classify).append(text);

    }


    $(".tab-content").on("click", ".payment", function () {
        var orderId = $(this).parentsUntil(".item_content").siblings(".title_text").children(".ProductId_number").text();
        window.location.href = "/pay/alipay/" + orderId;
    })
})