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
        reset();
       var roleId = $("#role").val();
       $.ajax({
           url: "/authority/show/" + roleId,
           type: 'POST',
           dataType: "json",
           success: function (data) {
               // 右框权限展开
               showHideAuthorities();
               for (var i in data) {
                   // 右框checkbox对应群组权限选中
                   showCheckedAuthorities("check", data[i].id);
                   // 左框显示对应群组权限
                   showCheckedValue("hide_li", data[i].id);
               }
           }
       });
    });

    // 保存
    $("#update").click(function () {
        // 获取选中的群组
        var roleId = $("#role").val();
        // 获取选中的权限
        var aIds = getCheckedIds("check");
        $.ajax({
            url: "/authority/update",
            type: 'POST',
            data: JSON.stringify({
                "roleId": roleId,
                "authorityIds": aIds
            }),
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            success: function (data) {
                alert(data.msg);
                window.location.reload();
            }
        })
    });

    // 刷新
    $("#reset").click(function () {
        reset();
    });

    // 点击 + 显示具体权限
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

    // 右框权限展开
    function showHideAuthorities() {
        var shows = document.getElementsByClassName("hide_authority");
        var icon = document.getElementsByClassName("hide_btn");
        for (var i = 0; i < shows.length; i++) {
            if (shows[i].style.display === '') {
                shows[i].style.display = 'block';
                icon[i].innerHTML = '-';
            }
        }
    }

    // 右框checkbox对应群组权限选中
    function showCheckedAuthorities(className, value) {
        var obj = document.getElementsByClassName(className);
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].value == value) {
                obj[i].checked = true;
            }
        }
    }

    // 获取checkbox选中的id
    function getCheckedIds(className) {
        var ids = [];
        var obj = document.getElementsByClassName(className);
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                ids.push(obj[i].value);
            }
        }
        return ids;
    }

    // checkbox 选中 hide_li 显示
    function showCheckedValue(className, value) {
        var obj = document.getElementsByClassName(className);
        for (var i = 0; i < obj.length; i++) {
            if (i === value - 1) {
                obj[i].style.display = 'block';
            }
        }
    }

    // checkbox 取消选中 hide_li 隐藏
    function hideCheckedValue(className, value) {
        var obj = document.getElementsByClassName(className);
        for (var i = 0; i < obj.length; i++) {
            if (i === value - 1) {
                obj[i].style.display = 'none';
            }
        }
    }

    // 重置
    function reset() {
        // checkbox
        resetCheckedbox("check");
        // li
        resetlis("hide_li");
    }

    // 重置checkbox
    function resetCheckedbox(className) {
        var obj = document.getElementsByClassName(className);
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                obj[i].checked = false;
            }
        }
    }

    // 重置 hide_li
    function resetlis(className) {
        var obj = document.getElementsByClassName(className);
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].style.display === 'block') {
                obj[i].style.display = 'none';
            }
        }
    }
});