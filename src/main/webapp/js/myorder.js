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
        var btn_class;
        var delete_btn = "";

        //PAYMENT 待付款 SEND 待发货 AFFIRM 确认收货 REVIEW 待评价 FINISH完成
        var state = json['State'];
        switch (state) {
            case "PAYMENT":
                StateTitle = "去付款";
                classify = $("#fk");
                btn_class = "btn btn-danger btn_dfk";
                delete_btn = "<span class='deleteOrder'><a href='javascript:;'>删除</a></span>";
                break;
            case "SEND":
                StateTitle = "待发货";
                classify = $("#fh");
                btn_class = "btn btn-primary btn_dfh";
                break;
            case "AFFIRM":
                StateTitle = "确认收货";
                classify = $("#sh");
                btn_class = "btn btn-warning btn_dsh";
                break;
            case "REVIEW":
                StateTitle = "待评价";
                classify = $("#pj");
                btn_class = "btn btn-success btn_dpj";
                break;
            case "FINISH":
                StateTitle = "订单完成";
                btn_class = "btn btn-default";
                delete_btn = "<span class='deleteOrder'><a href='javascript:;'>删除</a></span>";
                break;
        }
        var text =
            "<div class='panel panel-default item_content'>" +
            "<div class='panel-heading title_text'>" +
            "<span class='ProductId_font'>订单号：</span>" +
            "<span class='ProductId_number'>" + id + "</span>" +
            "<span class='CreateTime'>" + CreateDate + "</span>" +
            delete_btn + "</div>" +
            "<div class='panel-body'>" +
            "<div class='product_item_img'>" +
            "<img src=" + image + "></div>" +
            "<div class='product_item_title'>" +
            "<a href='/items/" + productId + "'>" + name + "</a></div>" +
            "<div class='product_item_price'>￥" + Price +
            "</div>" +
            "<div class='product_item_count'>" + Number +
            "</div>" +
            "<div class='product_item_all_price'>￥" + parseFloat(Price) * parseInt(Number) +
            "</div>" +
            "<div class='product_item_btn'>" +
            "<button type=button class='" + btn_class + "'>" + StateTitle +
            "</button></div></div>" +
            "<input class='orderId_input' type='hidden' value=" + id + "/></div>";

        $("#all").append(text);
        if ("" !== classify || null != classify) {
            $(classify).append(text);
        }

    }

    $(".tab-content").on("click", ".btn_dfk", function () {
        var orderId = $(this).parentsUntil(".item_content").siblings(".orderId_input").attr("value");
        window.location.href = "/pay/alipay/" + orderId;
    })

    $(".tab-content").on("click", ".btn_dsh", function () {
        //TODO 确认收货
    })

    $(".tab-content").on("click", ".deleteOrder", function () {
        var id = $(this).parentsUntil(".item_content").siblings(".orderId_input").attr("value");
        $("#order_id").data("id", id);
        $("#delcfmModel").modal();
    })

    $("#deleteItem").click(function () {
        var order_id = $("#order_id").data("id");
        var idList = $(".orderId_input");
        var flag = false;
        for (var i = 0; i < idList.length; i++) {
            var item = $(idList[i]);
            if ($(item).attr("value") == order_id) {
                flag = true;
                $(item).parent(".item_content").hide("slow", function () {
                    this.remove();
                })
            }
        }
        if (flag) {
            $.get("/deleteOrder/" + order_id,
                function (ret) {
                    if (ret !== 1) {
                        alert("删除失败");
                    }
                });
        }
    })
})