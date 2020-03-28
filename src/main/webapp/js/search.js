$(function () {
    FastTools.initHtml();
    let productName = FastTools.getQueryValue("productName");
    let page = FastTools.getQueryValue("page");
    let num = FastTools.getQueryValue("num");
    let sort = "";
    if (productName) {
        $("#search-text").val(productName);
    }
    $("input[type=radio][name=productSort]").change(function () {
        sort = $(this).val();
        getList(FastTools.pageTool.currentPage, sort, num);
    });
    getList(page, null, num);
    getPageTool();

    //分页
    function getPageTool() {
        FastTools.ajax("/getSearchTotalPage?productName=" + productName, "GET", "", function (flag, data) {
            if (flag && data.msg > 0) {
                FastTools.pageTool.addTool("#productPage", page, data.msg, function (currentPage) {
                    //返回选中页码
                    getList(currentPage, sort, num);
                })
            }
        });
    }

    function getList(page, sort, num) {
        if (productName) {
            let url = "/getSearchList?productName=" + productName;
            if (sort) {
                url += "&sort=" + sort + " desc";
            }
            if (page) {
                url += "&page=" + page;
            }
            if (num) {
                url += "&num=" + num;
            }
            // url = FastTools.location(url);
            FastTools.ajax(url, "GET", "", function (flag, data) {
                if (flag) {
                    var html = "";
                    let items = data.msg;
                    for (let i = 0; i < items.length; i++) {
                        let json = JSON.parse(items[i]);
                        html +=
                            "<div class='col-xs-6 product_item'>" +
                            "   <a href='/items/" + json.id + "' target='_blank'><img class='product_img' src='" + json.images + "'></a>" +
                            "   <p class='product_price'><span class='product_flag'>￥</span>" + json.promoteprice + "</p>" +
                            "   <p class='product_name'><a href='/items/" + json.id + "' target='_blank'>" + showSearchFont(productName, json.name) + "</a></p>" +
                            "   <div class='product_bottom'>" +
                            "       <span class='product_font'>月销量</span>" +
                            "       <span class='product_soles'>" + json.sales + "</span>" +
                            "       <span class='product_font'>评价</span>" +
                            "       <span class='product_review'>" + json.review + "</span>" +
                            "   </div>" +
                            "</div>";
                    }
                    $("#productList").html(html);
                } else {
                    $("#backImage").show();
                }
            })
        }
    }

    //高亮文字
    function showSearchFont(subFont, sourceFont) {
        let reg = new RegExp(subFont, "ig");
        let offset = sourceFont.search(reg);
        if (offset != -1) {
            let str = "<span style='color:red'>" + sourceFont.substr(offset, subFont.length) + "</span>";
            sourceFont = sourceFont.replace(reg, str);
        }
        return sourceFont;
    }

});
