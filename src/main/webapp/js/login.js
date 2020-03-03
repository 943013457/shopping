$(function () {

    $(".loginBtn").click(function () {
        var username = $.trim($("#username").val());
        var password = $("#password").val();
        if (CheckIsNull(username, password)) {
            ShowErrorMessage("用户名/密码不能为空");
            return false;
        }
        var json = {"user": username, "password": password};
        FastTools.ajax("/selectUserPwd", "POST", JSON.stringify(json), function (flag, data) {
            if (flag) {
                //返回上一页并刷新
                var ref = document.referrer;
                if (ref != "") {
                    self.location = document.referrer;
                } else {
                    self.location = "index";
                }
            } else {
                switch (data.msg) {
                    case "-101":
                        ShowErrorMessage("用户名不能为空");
                        break;
                    case "-102":
                        ShowErrorMessage("密码不能为空");
                        break;
                    case "-103":
                        ShowErrorMessage("账号已被封禁");
                        break;
                    case "-104":
                        ShowErrorMessage("用户名/密码错误");
                        break;
                }
            }
        })
        return false;
    });

    /**
     * @return {boolean}
     */
    function CheckIsNull() {
        for (var i = 0; i < arguments.length; i++) {
            var obj = arguments[i];
            if (obj === undefined || obj == null || obj === "") {
                return true;
            }
            if (arguments[i].length <= 0) {
                return true;
            }
        }
        return false;
    }

    function ShowErrorMessage(msg) {
        $("#error_Message").css("opacity", "1");
        $("#error_text").text(msg);
    }
})

