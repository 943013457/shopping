$(function () {
    FastTools.initHtml();
    init();
    $(".check_all_icon").click(function () {
        var allcheck = $(this).children("span");
        var itemcheck = $(".check_icon>span");

        if ($(allcheck).hasClass("glyphicon") &&
            $(allcheck).hasClass("glyphicon-ok")) {
            $(allcheck).removeClass("glyphicon");
            $(allcheck).removeClass("glyphicon-ok");

            $(itemcheck).removeClass("glyphicon");
            $(itemcheck).removeClass("glyphicon-ok");
        } else {
            $(allcheck).addClass("glyphicon");
            $(allcheck).addClass("glyphicon-ok");

            $(itemcheck).addClass("glyphicon");
            $(itemcheck).addClass("glyphicon-ok");
        }
        alltotal();
        toggle();
    });

    $(".items_content").on("click", ".check_icon", function () {
        $(this).children("span").toggleClass("glyphicon");
        $(this).children("span").toggleClass("glyphicon-ok");

        alltotal();
        toggle();
    });
    //TODO 修改数量时缓存要到Redis里 后期添加
    $(".items_content").on("click", ".item_icon_sub", function () {
        var item_text = $(this).siblings(".item_text");
        value = parseInt($(item_text).val());
        if (value > 1) {
            $(item_text).attr("value", value - 1);
            $(item_text).val(value - 1);
            updatetotal(item_text);
            alltotal();
        }
    });

    $(".items_content").on("click", ".item_icon_add", function () {
        var item_text = $(this).siblings(".item_text");
        value = parseInt($(item_text).val());
        if (value < 1024) {
            $(item_text).attr("value", value + 1);
            $(item_text).val(value + 1);
            updatetotal(item_text);
            alltotal();
        }
    });

    $(".items_content").on("input propertychange", ".item_text", function () {
        var item_text = this;
        value = parseInt($(item_text).val());
        if (value <= 0 || !/^[0-9]+$/.test($(item_text).val())) {
            $(item_text).attr("value", 1);
            $(item_text).val(1);
        } else if (value > 1024) {
            $(item_text).attr("value", 1024);
            $(item_text).val(1024);
        } else {
            $(item_text).attr("value", value);
            $(item_text).val(value);
        }
        updatetotal(item_text);
        alltotal();
    });

    $(".items_content").on("click", ".item_delete", function () {
        $("#url").data("item", this);
        $("#delcfmModel").modal();
    })

    $("#deleteItem").click(function () {
        var item = $("#url").data("item");
        var pid = $(item).children("#productId").val();
        deleteitem(pid, function () {
            $(item).parentsUntil(".panel").hide("slow", function () {
                $(item).parentsUntil(".items_content").remove();
            });
        });
    })

    $("#submit").click(function () {
        //使用cookie传递参数,这里要使用数组不然不能解析
        var itemsJson = [];
        var items = $(".items_content").children(".panel");
        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            var check_span = $(item).find(".check_icon>span");
            if (!$(check_span).hasClass("glyphicon") && !$(check_span).hasClass("glyphicon-ok")) {
                continue;
            }
            var pid = $(item).find("#productId").attr("value");
            var number = $(item).find(".item_text").attr("value");
            itemsJson.push({"pid": pid, "number": number})
        }
        if (itemsJson.length == 0) {
            return;
        }
        $.cookie('submitOrderJson', JSON.stringify(itemsJson), {expires: 1, path: '/'});
        window.location.href = "/submitOrder";
    })
})

function init() {
    FastTools.ajax("/getTrolley", "GET", "", function (flag, data) {
        if(flag){
            var json = JSON.parse(data.msg);
            for (var index in json) {
                showItems(json[index]);
            }
        }
    })
    //触发提示工具
    $("#submit").tooltip();
}

//删除商品
function deleteitem(pid, callback) {
    FastTools.ajax("/deleteItem/" + pid, "DELETE", "", function (flag, data) {
        if (flag && JSON.parse(data.msg)) {
            callback();
            toastr.success('操作成功');
        } else {
            toastr.error('删除失败,请稍后再试');
        }
    });
}

//更新单件总价
function updatetotal(item) {
    var parent = $(item).parentsUntil(".panel-body");
    var price = parseFloat($(parent).siblings(".item_price").children(".now_price").text());
    var number = parseInt($(parent).children(".item_text").attr("value"));
    var total = price * number;

    $(parent).siblings(".item_total").children("#total").text(total);
}

//计算选中的价格
function alltotal() {
    var items = $(".items_content").find(".check_icon");
    var total = 0;
    var count = 0;

    for (var i = 0; i < items.length; i++) {
        var checkbox = $(items[i]).children("span");
        if ($(checkbox).hasClass("glyphicon") &&
            $(checkbox).hasClass("glyphicon-ok")) {
            count++;
            total += parseFloat($(items[i]).siblings(".item_total").children("#total").text());
        }
    }
    $("#select_number").text(count);
    $("#all_total").text(total);
}

function showItems(json) {
    var img = json['img'];
    var id = json['id'];
    var name = json['name'];
    var originalprice = json['originalprice'];
    var promoteprice = json['promoteprice'];
    var number = json['number'];

    var text = " <div class='panel panel-default'>" +
        "<div class='panel-body'>" +
        "<div class='check_icon'>" +
        "<span></span>" +
        "</div>" +
        "<div class='item_img'>" +
        "<img src=" + img + ">" +
        "</div>" +
        "<div class='item_name'>" +
        "<a href=/items/" + id + ">" + name + "</a>" +
        "</div>" +
        "<div class='item_price'>" +
        "<span>￥</span>" +
        "<del class='old_price'>" + originalprice + "</del><br/>" +
        "<span>￥</span>" +
        "<span class='now_price'>" + promoteprice + "</span>" +
        "</div>" +
        "<div class='item_number'>" +
        "<a class='item_icon_sub' href='javascript:;'>-</a>" +
        "<input class='item_text' type='text' value=" + number + ">" +
        "<a class='item_icon_add' href='javascript:;'>+</a>" +
        "</div>" +
        "<div class='item_total'>" +
        "<span>￥</span>" +
        "<span id='total'>" + parseFloat(promoteprice) * parseInt(number) + "</span>" +
        "</div>" +
        "<div class='item_delete'>" +
        "<a href='javascript:;'>删除</a>" +
        "<input id='productId' type='hidden' value=" + id + "></div>" +
        "</div>" +
        "</div>"

    $(".items_content").append(text);
}

function toggle() {
    var items = $(".items_content").find(".check_icon");
    for (var i = 0; i < items.length; i++) {
        if ($(items[i]).children("span").hasClass("glyphicon")) {
            $("#submit").tooltip('destroy');
            return;
        }
    }
    $("#submit").tooltip();
}