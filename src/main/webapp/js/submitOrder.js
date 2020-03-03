$(function () {
    var cookie_json = $.cookie('submitOrderJson') || "";
    init();
    $(".submit_btn").click(submit);

    function init() {
        if ("" !== cookie_json) {
            FastTools.ajax("/getOrderJson", "POST", cookie_json, function (flag, data) {
                if (flag) {
                    var retJson = JSON.parse(data.msg);
                    var all_total = 0;
                    for (var i in retJson) {
                        var json = retJson[i];
                        var imgSrc = json['imgUrl'];
                        var pid = json['pid'];
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
                            "<a class='itemTitle' id='" + pid + "' href='/items/" + pid + "'>" + name + "</a></div>" +
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
        if ("" !== cookie_json) {
            var jsons = [];
            var address = $(".address_text").val();
            var receiver = $(".consignee_text").val();
            var phone = $(".phone_text").val();

            var items = $(".productItems").find(".item_body");
            for (var i = 0; i < items.length; i++) {
                var productId = $(items[i]).find(".itemTitle").attr("id")
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
            FastTools.ajax("/createOrder", "POST", JSON.stringify(jsons), function (flag, data) {
                if (flag) {
                    window.location.href = "/pay/alipay/" + data.msg;
                } else {
                    toastr.success("订单生成失败,error:" + data.msg);
                }
            })
        }
    }

})