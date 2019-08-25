$(function () {
    var url = "/getUser";
    $.ajax({
        url: url,
        type: "GET",
        datatype:"text",
        async:true,
        success: function (name) {
            var top_welcome = $(".top-welcome");
            if (name === undefined || name === "") {
                top_welcome.text("Hi，欢迎来到天猫").after("<a class=\"top-text\" id=\"loginBtn\" href=\"/login\">请登录</a>\n" +
                    "<a class=\"top-text\" id=\"registerBtn\" href=\"/register\">免费注册</a>");
                return;
            }
            top_welcome.text("Hi，" + name).after("<a class=\"top-text\" href=\"/logout\">退出</a>");
        }
    });
})