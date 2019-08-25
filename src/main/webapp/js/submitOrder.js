$(function () {
    var cookie_json = $.cookie('submitOrderJson');
    var pids = JSON.parse(cookie_json);
    init();
    $(".submit_btn").click(submit);

    function init() {
        if (undefined !== cookie_json) {
            $.ajax({
                url: "/getOrderJson",
                type: "POST",
                data: cookie_json,
                datatype: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (retJson) {
                    var all_total = 0;
                    for (var i in retJson) {
                        var json = retJson[i];
                        var imgSrc = json['imgUrl'];
                        var pid = pids[i]['pid'];
                        var name = json['name'];
                        var price = json['price'];
                        var number = json['number'];
                        var total = parseFloat(price) * parseInt(number);
                        all_total += total;

                        $(".productItems").append(
                            "<div class=item_body>" +
                            "<div class='panel-body'>" +
                            "<div class='productItemImg'>" +
                            "<img class='itemImg' src=" + imgSrc + "></div>" +
                            "<div class='productInfo'>" +
                            "<a class='itemTitle' href='/items/" + pid + "'>" + name + "</a></div>" +
                            "<div class='productPrice'>" +
                            "<span class='itemPrice'>￥" + price + "</span></div>" +
                            "<div class='productNumber'>" +
                            "<span class='itemNumber'>" + number + "</span></div>" +
                            "<div class='productTotal'>" +
                            "<span class='itemTotal'>￥" + total + "</span></div></div>" +
                            "<div class='panel-heading remark'>" +
                            "<span class='remark_font'>给卖家留言</span>" +
                            "<input type='text' class='form-control remark_text' placeholder='选填：对本次交易的说明'></div></div>");
                    }
                    $(".total_number").text(all_total);
                }
            })
        }
    }

    function submit() {
        if (undefined !== cookie_json) {
            var jsons = [];
            var address = $(".address_text").val();
            var receiver = $(".consignee_text").val();
            var phone = $(".phone_text").val();

            var items = $(".productItems").find(".item_body");
            for (var i = 0; i < items.length; i++) {
                var productId = pids[i]['pid'];
                var number = $(items[i]).find(".itemNumber").text();
                var price = $(items[i]).find(".itemTotal").text().replace("￥", "");
                var msg = $(items[i]).find(".remark_text").val();
                jsons.push({
                    "address": address,
                    "receiver": receiver,
                    "phone": phone,
                    "userMessage": msg,
                    "price": price,
                    "productId": productId,
                    "number": number
                })
            }
            $.ajax({
                url: "/createOrder",
                type: "POST",
                data: JSON.stringify(jsons),
                datatype: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (ret) {
                    if ("-1" == ret) {
                        alert("请登录");
                        window.location.href = "/login";
                    } else if ("-2" == ret || "-3" == ret || "" == ret) {
                        alert("提交订单失败 " + ret);
                    } else {
                        window.location.href = "/pay/alipay/" + ret;
                    }
                }
            })
        }
    }

})