$(function () {

    $(".loginBtn").click(function () {
        var username = $.trim($("#username").val());
        var password = $("#password").val();
        if (CheckIsNull(username, password)) {
            ShowErrorMessage("用户名/密码不能为空");
            return false;
        }
        var JsonData = JSON.stringify({"user": username, "password": password});
        $.ajax({
            url: "selectUserPwd",
            type: "POST",
            data: JsonData,
            datatype: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (result) {
                switch (result) {
                    case "ok":
                        //返回上一页并刷新
                        var ref = document.referrer;
                        if (ref != "") {
                            self.location = document.referrer;
                        } else {
                            self.location = "index";
                        }
                        break;
                    case "banned" :
                        ShowErrorMessage("账号已被封禁");
                        break;
                    case "error" :
                        ShowErrorMessage("用户名/密码错误");
                        break;
                    case "unknown" :
                        ShowErrorMessage("未知错误");
                        break;
                    case "UserNameNull":
                        ShowErrorMessage("用户名不能为空");
                        break;
                    case "PasswordNull":
                        ShowErrorMessage("密码不能为空");
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

