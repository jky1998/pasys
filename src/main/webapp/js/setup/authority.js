$(document).ready(function () {
    // select 显示 role
    var role = document.getElementById("role");
    $.ajax({
        url: "/role/show",
        type: 'GET',
        dataType: "json",
        success: function (roles) {
            for (var i in roles) {
                role.options.add(new Option(roles[i].comment, roles[i].id));
            }
        }
    });

    // 选中群组获取角色权限
    $("#role").change(function () {
       var roleId = $("#role").val();
       $.ajax({

       });
    });

    // 点击显示具体权限
    $(".hide_btn").click(function () {
        var index = $(".hide_btn").index(this);
        var shows = document.getElementsByClassName("hide_authority");
        var icon = document.getElementsByClassName("hide_btn");
        if (shows[index].style.display === '') {
            shows[index].style.display = 'block';
            icon[index].innerHTML = '-';
        } else if (shows[index].style.display === 'block') {
            shows[index].style.display = '';
            icon[index].innerHTML = '+';
        }
    });

    // 点击checkbox对应权限显示
    $("input.check").click(function () {
        var value = this.value;
        if (this.checked) {
            showCheckedValue("hide_li", value);
        } else {
            hideCheckedValue("hide_li", value);
        }
    });

    function showCheckedValue(className, value) {
        var obj = document.getElementsByClassName(className);
        var checked;
        for (var i = 0; i < obj.length; i++) {
            if (i === value - 1) {
                obj[i].style.display = 'block';
            }
        }
    }

    function hideCheckedValue(className, value) {
        var obj = document.getElementsByClassName(className);
        var checked;
        for (var i = 0; i < obj.length; i++) {
            if (i === value - 1) {
                obj[i].style.display = 'none';
            }
        }
    }

});