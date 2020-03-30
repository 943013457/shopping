$(function () {
    FastTools.initHtml();
    $(".list_li").hover(function () {
        $(this).addClass("selected");
    }, function () {
        $(this).removeClass("selected");
    })

    //获取推荐商品
    FastTools.ajax("/getIndexCategory", "GET", "", function (flag, data) {
        if (flag) {
            let json = data.msg;
            for (let name in json) {
                let html = "";
                html += "<div class='tuijian_top'>" +
                    "<span class='tuijian_font'>" + name + "</span>" +
                    "<div class='tuijian_content'><div class='row'>";
                let product = json[name];
                for (let i in product) {
                    html += "<a href='/items/" + product[i].id + "' class='col-xs-3 '>" +
                        "<div class='tuijina_item'>" +
                        "<img class='tuijian_img' src='" + product[i].image + "'>" +
                        "<p class='tuijian_name'>" + product[i].name + "</p>" +
                        "<p class='tuijian_price'>￥" + product[i].promoteprice + "</p></div></a>";
                }
                $(".tuijian").html(function (i, oldHtml) {
                    return oldHtml + html;
                });
            }
        }
    })

    $(".list_li a").click(function () {
        let val = this.text;
        window.location.href = FastTools.location("/search?productName=" + val);
    })
});




