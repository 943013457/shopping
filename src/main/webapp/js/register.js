$(function () {
    //就绪状态
    var username;
    var password;
    var email;
    var phone;

    $(".ok_btn").click(function () {
        if (!$.trim(username) || !$.trim(password) || !$.trim(email) || !$.trim(phone)) {
            alert("请输入正确数据");
            return;
        }
        FastTools.ajax("/registerUser", "POST",
            {"user": username, "password": password, "email": email, "phone": phone},
            function (flag, data) {
                if (flag && JSON.parse(data.msg)) {
                    //注册成功后跳转
                    self.location = document.referrer;
                } else {
                    toastr.success('注册失败，error:' + data.msg);
                }
            })
    });

    //下一步
    $(".next_btn_1").click(function () {
        username = false;
        if (!$(this).hasClass("disabled")) {
            $(".username_msg").text("检查用户名...");
            var username = $("#username").val().replace(" ", "");
            FastTools.ajax("/selectUser" + username, "GET", "", function (flag, data) {
                if (flag && JSON.parse(data.msg)) {
                    $(".pagebox").animate({left: '-500px'});
                    $(".username_msg").text("");
                    $(".bar").animate({width: "48%"});
                    $(".nav_2").css("background-color", "#c40000");
                    username = $("#username").val().replace(" ", "");
                } else {
                    $(".username_msg").text("用户名重复");
                }
            })
        }
    });

    $(".next_btn_2").click(function () {
        if (!$(this).hasClass("disabled")) {
            $(".pagebox").animate({left: '-1000px'});
            $(".bar").animate({width: "76%"});
            $(".nav_3").css("background-color", "#c40000");
        }
    });

    //密码检查
    function checkpwd() {
        password = false;
        var one = $("#password_one").val();
        var two = $("#password_two").val();
        if (one === "" || two === "") {
            return;
        }
        if (one === two) {
            $(".password_msg_2").text("");
            $(".next_btn_2").removeClass("disabled");
            password = one;
        } else {
            $(".password_msg_2").text("密码不一致");
            $(".next_btn_2").addClass("disabled");
        }
    }

    //用户名
    $("#username").bind("input propertychange", function () {
        if ($(this).val() !== "") {
            $(".next_btn_1").removeClass("disabled");
            return;
        }
        $(".next_btn_1").addClass("disabled");
    });

    $("#password_one").bind("input propertychange", checkpwd);

    $("#password_two").bind("input propertychange", checkpwd);

    $("#email").bind("input propertychange", function () {
        var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
        var e = $(this).val().replace(" ", "");
        if (reg.test(e)) {
            email = e;
            $(".email_msg").text("");
            if (phone != null) {
                $(".ok_btn").removeClass("disabled");
            }
            return;
        }
        $(".email_msg").text("邮箱不正确");
        $(".ok_btn").addClass("disabled");
    });

    $("#phone").bind("input propertychange", function () {
        var reg = /^1[3456789]\d{9}$/;
        var e = $(this).val().replace(" ", "");
        if (reg.test(e)) {
            phone = e;
            $(".phone_msg").text("");
            if (email != null) {
                $(".ok_btn").removeClass("disabled");
            }
            return
        }
        $(".phone_msg").text("手机号不正确");
        $(".ok_btn").addClass("disabled");
    });


    //上一步
    $(".pre_btn_1").click(function () {
        $(".pagebox").animate({left: '0px'});
        $(".bar").animate({width: "23%"});
        $(".nav_2").css("background-color", "#6C6C6C");
    });

    $(".pre_btn_2").click(function () {
        $(".pagebox").animate({left: '-500px'});
        $(".bar").animate({width: "48%"});
        $(".nav_3").css("background-color", "#6C6C6C");
    });

});
