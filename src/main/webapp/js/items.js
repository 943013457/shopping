$(function () {
    FastTools.initHtml();
    var pid = parseInt(/[0-9]+/.exec(/(\/items\/){1}[0-9]+/.exec(window.location.pathname)));
    getIsAddTrolley(pid);
    getProductJson(pid);
    getImageJson(pid);
    getPropertyJson(pid);


    $(".up").click(function () {
        var value = parseInt($(".number_input").val());
        if (value < parseInt($("#buy_stock").text())) {
            $(".number_input").val(value + 1);
            $(".number_input").attr("value", value + 1);
        }
    })

    $(".down").click(function () {
        var value = parseInt($(".number_input").val());
        if (value > 1) {
            $(".number_input").val(value - 1);
            $(".number_input").attr("value", value - 1);
        }
    })

    $(".number_input").bind("input propertychange", function () {
        var value = $(this).val();
        var max = parseInt($("#buy_stock").text());
        var text = $(".number_input");

        if (value < 1 || !/^[0-9]+$/.test(value)) {
            $(text).val(1);
            $(text).attr("value", 1);
        } else if (value > max) {
            $(text).val(max);
            $(text).attr("value", max);
        } else {
            $(text).attr("value", value);
        }
    });

    $(".buy").click(function () {
        //使用cookie传递参数,这里要使用数组不然不能解析
        var itemJson = [{"pid": pid, "number": $(".number_input").attr("value")}];
        $.cookie('submitOrderJson', JSON.stringify(itemJson), {path: '/'});

        window.location.href = "/submitOrder";
    })

    $(".buy_btn").on("click", ".addcart", function () {
        var data = {"productID": pid, "number": $(".number_input").attr("value")};
        FastTools.ajax("/addTrolley", "POST", JSON.stringify(data), function (flag, data) {
            if (flag && JSON.parse(data.msg)) {
                $(".addcart").remove();
                $(".buy_btn").append(
                    "<button type=\"button\" class=\"btn disabled addedcart\">已加入购物车</button>");
            }
        });
    });

    var detail = $("#detail");
    var review = $("#review");
    var pageDetail = $(".pageDetail");
    var pageImage = $(".page_image");
    var pageReview = $(".page_review");
    detail.click(function () {
        detail.addClass("active");
        review.removeClass("active");
        pageDetail.show();
        pageImage.show();

        pageReview.hide();
    });
    var fristClick = false;
    review.click(function () {
        detail.removeClass("active");
        review.addClass("active");
        pageDetail.hide();
        pageImage.hide();

        pageReview.show();
        if (!fristClick) {
            fristClick = true;
            getReviewJson(pid);
        }
    });

    $(".row").on("mouseenter", ".col-img", (function () {
        var src = $(this).children("a").children("img")[0].src;
        $("#small_img_select").attr("src", src);
    }));

    function getIsAddTrolley(pid) {
        FastTools.ajax("/getIsAddTrolley/" + pid, "GET", "", function (flag, data) {
            if (flag) {
                if (JSON.parse(data.msg)) {
                    $(".buy_btn").append(
                        "<button type=\"button\" class=\"btn disabled addedcart\">已加入购物车</button>");
                } else {
                    $(".buy_btn").append(
                        "<button type=\"button\" class=\"btn btn-info addcart\">加入购物车</button>");
                }
            }
        })
    }

    function getProductJson(pid) {
        FastTools.ajax("/getProductJson/" + pid, "GET", "", function (flag, data) {
            if (flag) {
                var json = data.msg;
                $(".title").text(json.name);
                $(".subtitle").text(json.subtitle);
                $(".yuanjia").text(json.originalprice);
                $(".cuxiaojia").text(json.promoteprice);
                $(".sale_number").text(json.sales);
                $(".review_number").text(json.review);
                $(".review_number_nav").text(json.review);
                $("#buy_stock").text(json.stock);
            }
        })
    }

    function getImageJson(pid) {
        FastTools.ajax("/getImageJson/" + pid, "GET", "", function (flag, data) {
            if (flag) {
                var json = data.msg;
                $("#title_img").attr("src", json['top']);

                var small = json['small'];
                if (small.length > 0) {
                    var s = small.split(",");
                    for (var i in s) {
                        if (0 === parseInt(i)) {
                            $("#small_img_select").attr("src", s[i]);
                        }
                        $(".row").append(
                            "<div class=\"col-xs-1 col-img\">" +
                            "<a href=\"javascript:;\" class=\"thumbnail\">" +
                            "<img src=" + s[i] +
                            "></a>" +
                            "</div>")
                    }
                }
                var content = json['content'];
                if (content.length > 0) {
                    var c = content.split(",");
                    for (var i in c) {
                        $(".page_image").append("<img src=\"" + c[i] + "\"/>");
                    }
                }
            }
        })
    }

    function getPropertyJson(pid) {
        FastTools.ajax("/getPropertyJson/" + pid, "GET", "", function (flag, data) {
            if (flag) {
                var json = data.msg;
                for (var j in json) {
                    $(".detail_content>h4").after("<span class=\"detail_font\">" +
                        j +
                        "<span>: </span>" +
                        json[j] +
                        "</span>");
                }
            }
        })
    }

    function getReviewJson(pid) {
        FastTools.ajax("/getReviewJson/" + pid, "GET", "", function (flag, data) {
            if (flag) {
                var json = JSON.parse(data.msg);
                for (var j in json) {
                    var obj = json[j];
                    $(".page_review").append("<div class=\"review_item\">" +
                        "<div class=\"review_content\">" +
                        obj['content'] +
                        "</div>" + "<div class=\"review_username\">" +
                        obj['username'] +
                        "<span class=\"anonymity_font\">(匿名)</span></div>" +
                        "<div class=\"review_time\">" +
                        obj['createDate'] +
                        "</div>");
                }
            }
        })
    }

})
