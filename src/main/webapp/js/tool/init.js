$(function () {
    var FastTools = {
        welcomeText: "Hi，欢迎来到天猫",
        homeIcon: "/image/HomeIcon.png",
        loginAction: "/login",
        registerAction: "/register",
        loginOutAction: "/logout",
        initHtml: function () {
            var loginHtml = "";
            var loginName = this.getUserName();
            if (loginName) {
                this.welcomeText = "Hi, " + loginName;
                loginHtml = "<a class='top-text' href='" + this.loginOutAction + "'>退出</a>";
            } else {
                loginHtml =
                    "<a class='top-text' href='" + this.loginAction + "'>请登录</a>\n" +
                    "<a class='top-text' href='" + this.registerAction + "'>免费注册</a>";
            }
            var nav_html =
                "<span id='top-welcome'>" + this.welcomeText + loginHtml + "</span>" +
                "<span id='pull-right'>" +
                "   <a class='top-text' href='/myorder'>我的订单</a>" +
                "   <a class='top-text' href='/shoppingTrolley'>购物车</a>" +
                "</span>"
            $("#nav-top").html(nav_html);

            var search_html =
                "<div class='home_icon'>" +
                "    <a href='/index'><img class='icon' src='" + this.homeIcon + "'></a>" +
                "</div>" +
                "<div class='seach_box'>" +
                "    <form action='search' method='post'>" +
                "        <button id='search-btn' type='submit'>搜商品</button>" +
                "        <input id='search-text' type='text' placeholder='这里有你想要的'>" +
                "    </form>" +
                "</div>"
            $("#content-top").html(search_html);
            //toastr弹窗初始化
            toastr.options.positionClass = 'toast-top-center';
        },
        isAsync: true,//本次是否异步请求
        ajax: function (url, type, data, callback) {
            var async = this.isAsync;
            this.isAsync = !this.isAsync;
            console.log(url);
            console.log(JSON.stringify(data));
            $.ajax({
                type: type,
                url: url,
                contentType: "application/json;charset=UTF-8",
                datatype: "JSON",
                data: data,
                async: async,
                success: function (data) {
                    if (data.code === "ok") {
                        callback(true, data);
                    } else {
                        callback(false, data);
                    }
                },
                error: function () {
                    callback(false, "请求失败");
                }
            });
        },
        getUserName: function () {
            var ret = "";
            this.isAsync = false;
            this.ajax("/getUser", "GET", "", function (flag, data) {
                if (flag) {
                    var json = JSON.parse(data.msg);
                    ret = json.name;
                }
            });
            return ret;
        }
    }
    //页面初始化
    FastTools.initHtml();
    window.FastTools = FastTools;
})
