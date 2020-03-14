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
                    "<a class='top-text' href='" + this.loginAction + "'>请登录</a>" +
                    "<a class='top-text' href='" + this.registerAction + "'>免费注册</a>";
            }
            //顶部导航条
            var nav_html =
                "<span id='top-welcome'>" + this.welcomeText + loginHtml + "</span>" +
                "<span id='pull-right'>" +
                "   <a class='top-text' href='/myorder'>我的订单</a>" +
                "   <a class='top-text' href='/shoppingTrolley'>购物车</a>" +
                "</span>"
            $("#nav-top").html(nav_html);
            //搜索框
            var search_html =
                "<div class='home_icon'>" +
                "    <a href='/index'><img class='icon' src='" + this.homeIcon + "'></a>" +
                "</div>" +
                "<div class='seach_box'>" +
                "   <button id='search-btn'>搜商品</button>" +
                "   <input id='search-text' type='text' placeholder='这里有你想要的'>" +
                "</div>"
            $("#content-top").html(search_html);
            //搜索按钮监听
            $("div").on("click keyup", "#search-btn,#search-text", function (event) {
                if ((event.type === "click" && event.currentTarget.id === "search-text") || (event.type === "keyup" && event.keyCode !== 13)) {
                    return;
                }
                var search = $("#search-text").val();
                if (search) {
                    window.location.href = FastTools.location("/search?productName=" + search);
                } else {
                    FastTools.debounce(function () {
                        toastr.error('请输入商品名')
                    });
                }
            })
            //toastr弹窗初始化
            toastr.options.positionClass = 'toast-top-center';
        },
        pageTool: {
            element: "",
            dom: "",
            pageNum: 5,//默认显示页码
            currentPage: 1,//当前页
            totalPage: 0,//总页数
            callback: function () {
            },
            addTool: function (ele, currentPage, totalPage, callback) {
                this.element = ele;
                this.currentPage = currentPage || 1;
                this.totalPage = totalPage || 0;
                this.callback = callback;
                this.updateTool();
            },
            updateTool: function () {
                this.dom = "";
                this.initBack();
                this.initPage();
                this.initNext();
                $(this.element).html(this.dom);
            },
            changePage: function (page) {
                this.currentPage = page;
                this.updateTool();
                this.callback(this.currentPage);
            },
            backPage: function () {
                if (this.currentPage - 1 > 0) {
                    this.currentPage--;
                    this.updateTool();
                    this.callback(this.currentPage);
                }
            },
            nextPage: function () {
                if (this.currentPage + 1 <= this.totalPage) {
                    this.currentPage++;
                    this.updateTool();
                    this.callback(this.currentPage);
                }
            },
            initBack: function () {
                let isEnable = "";
                if (this.currentPage <= 1) {
                    isEnable = " class='disabled'";
                }
                this.dom +=
                    "<ul class='pagination pagination-lg'>" +
                    "<li" + isEnable + "><a href='javascript:;' aria-label='Previous' onclick='FastTools.pageTool.changePage(1)'>" +
                    "   <span aria-hidden='true'>首页</span>" +
                    "</a></li>" +
                    "<li" + isEnable + "><a href='javascript:;' aria-label='Previous' onclick='FastTools.pageTool.backPage()'>" +
                    "   <span aria-hidden='true'>上一页</span>" +
                    "</a></li>";
            },
            initPage: function () {
                let mid = parseInt(this.pageNum / 2);
                let index = this.currentPage;
                if (index - mid > 0 && index - mid < this.totalPage) {
                    //页码滑动(触发滑动)
                    index = this.currentPage - mid;
                } else {
                    //未触发滑动
                    index -= (index % this.pageNum) - 1;
                }
                let end = index + this.pageNum;
                if (end > this.totalPage) {
                    end = this.totalPage;
                }
                for (let i = index; i <= end; i++) {
                    let isEnable = "";
                    if (i == this.currentPage) {
                        isEnable = " class='disabled'";
                    }
                    this.dom += "<li" + isEnable + "><a href='javascript:;' onclick='FastTools.pageTool.changePage(" + i + ")'>" + i + "</a></li>";
                }

            },
            initNext: function () {
                let isEnable = "";
                if (this.currentPage >= this.totalPage) {
                    isEnable = " class='disabled'";
                }
                this.dom +=
                    "<li" + isEnable + "><a href='javascript:;' aria-label='Next' onclick='FastTools.pageTool.nextPage()'>" +
                    "   <span aria-hidden='true'>下一页</span>" +
                    "<li" + isEnable + "><a href='javascript:;' aria-label='Next' onclick='FastTools.pageTool.changePage(" + this.totalPage + ")'>" +
                    "   <span aria-hidden='true'>尾页</span>" +
                    "</a></li></ul><p id='pageText'>总页数: " + parseInt(this.totalPage) + "</p>";
            }
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
                contentType: "application/json;charset=utf-8",
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
                error: function (e) {
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
        },
        getQueryValue: function (name) {
            var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
            var r = window.location.search.substr(1).match(reg);
            if (r != null) {
                return decodeURI(decodeURI(r[2]));
            }
            return null;
        },
        location: function (url) {
            //二次编码
            return encodeURI(encodeURI(url));
            // window.location.href = encodeURI(encodeURI(url));
        },
        timeout: 1000,
        debounceTimer: null,
        throttleTimer: null,
        debounce: function (callback) {
            //防抖 (立即执行)
            if (!this.debounceTimer) {
                callback.apply(this, arguments);
            }
            clearTimeout(this.debounceTimer)
            this.debounceTimer = setTimeout(function () {
                this.debounceTimer = null;
            }, this.timeout);
        },
        throttle: function (callback) {
            //节流
            if (!this.throttleTimer) {
                callback.apply(this.arguments);
                this.throttleTimer = setTimeout(function () {
                    this.throttleTimer = null;
                }, this.timeout);
            }
        }
    }
    //页面初始化
    FastTools.initHtml();
    window.FastTools = FastTools;
})
