$(document).ready(function () {
    navLocation("daily_manage", "plan_formulate", "plan_formulate_li");
    // 显示日期选择
    initPage();
    getPlans();

    // select改变获取对应月度的计划
    $("#yyyy").change(function () {
        getPlans()
    });
    $("#mm").change(function () {
        getPlans()
    });

    // 复制上月计划
    $("#copyLastPlan").click(function () {
        var date = getMonthly();
        var lastMonthly = new Date();
        if (date.getMonth() === 0) {
            lastMonthly.setFullYear(date.getFullYear() - 1);
            lastMonthly.setMonth(11);
            lastMonthly.setDate(2);
        } else {
            lastMonthly.setFullYear(date.getFullYear());
            lastMonthly.setMonth(date.getMonth() - 1);
            lastMonthly.setDate(2);
        }

        $.ajax({
            url: "/plan/copyLastPlan",
            type: 'POST',
            data: JSON.stringify({
                "monthly": date,
                "lastMonthly": lastMonthly
            }),
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            success: function (data) {
                alert(data.msg);
                getPlans();
            }
        });
    });

    // 添加计划明细
    $("#submit_add").click(function () {
        var monthly = getMonthly();
        // 判断输入规范
        var content = document.getElementById("addContent").value;
        var detail = document.getElementById("addDetail").value;
        var score = document.getElementById("addScore").value;
        var flag;
        while (true) {
            flag = checkContent(content);
            if (!flag) {
                break;
            }
            flag = checkDetail(detail);
            if (!flag) {
                break;
            }
            flag = checkScore(score);
            if (!flag) {
                break;
            }
            break;
        }

        // 添加目标
        if (flag) {
            $.ajax({
                url: "/plan/monthly/add",
                type: 'POST',
                data: JSON.stringify({
                    "content": content,
                    "detail": detail,
                    "score": score,
                    "monthly": monthly
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    getPlans();
                }
            });
        }
    });

    // 复制共性目标
    $("#copy").click(function () {
        var date = getMonthly();

        $.ajax({
            url: "/plan/copy/monthly=" + date,
            type: 'POST',
            dataType: "json",
            success: function (data) {
                alert(data.msg);
                getPlans();
            }
        });
    });

    // 修改
    $("#submit_update").click(function () {
        // 判断输入规范
        var content = document.getElementById("updateContent").value;
        var detail = document.getElementById("updateDetail").value;
        var score = document.getElementById("updateScore").value;
        var flag;
        while (true) {
            flag = checkContent(content);
            if (!flag) {
                break;
            }
            flag = checkDetail(detail);
            if (!flag) {
                break;
            }
            flag = checkScore(score);
            if (!flag) {
                break;
            }
            break;
        }
        if (flag) {
            var id = $("#updateId").val();
            $.ajax({
                url: "/plan/update/" + id,
                type: 'PUT',
                data: JSON.stringify({
                    "content": content,
                    "detail": detail,
                    "score": score
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    getPlans();
                }
            });
        }
    });

    // 获取要修改的目标
    $("#getUpdatePlan").click(function () {
        var updateBtn = document.getElementById("getUpdatePlan");
        updateBtn.removeAttribute("data-toggle");
        var ids = [];
        var id;
        $("input[name='id']:checked").each(function () {
            ids.push($(this).val());
        });
        if (ids.length === 0 || ids.length > 1) {
            alert("请先选择一个要修改的计划！")
        } else {
            id = ids[0];
            $.ajax({
                url: "/plan/getUpdatePlan/" + id,
                type: 'POST',
                dataType: "json",
                success: function (data) {
                    $("#updateId").val(data.id);
                    $("#updateContent").val(data.content);
                    $("#updateDetail").val(data.detail);
                    $("#updateScore").val(data.score);
                }
            });
            // 弹出dialog
            updateBtn.setAttribute("data-toggle", "modal");
        }
    });

    // 删除
    $("#delete").click(function () {
        var ids = [];
        $("input[name='id']:checked").each(function () {
            ids.push($(this).val());
        });
        if (ids.length === 0) {
            alert("请先选择要删除的计划！")
        } else {
            $.ajax({
                url: "/plan/delete",
                type: 'DELETE',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                   ids: ids
                }),
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    getPlans();
                }
            });
        }
    });

    // 全选
    $("#selectAll").click(function () {
        var checkbox = document.getElementsByName("id");
        if (this.checked) {
            for (var i = 0; i < checkbox.length; i++) {
                checkbox[i].checked = true;
            }
        } else {
            for (var i = 0; i < checkbox.length; i++) {
                checkbox[i].checked = false;
            }
        }
    });
});

// 获取月度计划
function getPlans() {
    var date = getMonthly();
    // 清空表
    $("#plan_tbl tr:not(:first)").html("");
    $.ajax({
        url: "/plan/formulate/monthly=" + date,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                $("#plan_tbl").append("<tr>"
                    + "<td><input type='checkbox' name='id' value=" + data[i].id + "></td>"
                    + "<td>" + transferTime(data[i].monthly) + "</td>"
                    + "<td>" + data[i].content + "</td>"
                    + "<td><span class='detail' "
                    + "style='text-decoration: underline'  data-toggle='modal' data-target='#details' value="
                    + data[i].detail + ">详细信息</span></td></tr>");
            }

            $(".detail").click(function(){
                var detail = $(this).attr("value");
                document.getElementById("detail").innerHTML = "";
                $("#detail").append("<span>" + detail + "</span>");
            });
        }
    });
}
