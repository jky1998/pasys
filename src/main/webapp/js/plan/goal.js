$(document).ready(function () {
    navLocation("system_setup", "plan_common", "plan_common_id");

    var scores = document.getElementsByClassName("score");
    var totalScore = 0.0;
    for (var i = 0; i < scores.length; i++) {
        var score = scores[i].innerText * 1.0;
        totalScore += score;
    }
    document.getElementById("total").innerText = totalScore;

    $("#submit_add").click(function () {
        // 判断输入规范
        var content = document.getElementById("content").value;
        var detail = document.getElementById("detail").value;
        var score = document.getElementById("score").value;
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
                url: "/plan/common/add",
                type: 'POST',
                data: JSON.stringify({
                    "content": content,
                    "detail": detail,
                    "score": score
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    window.location.reload();
                }
            });
        }
    });

    // 显示要修改的共性目标
    $("#update").click(function () {
       var ids = [];
       var id;
       $("input[name='id']:checked").each(function () {
           ids.push($(this).val());
       });
       if (ids.length === 0 || ids.length > 1) {
           alert("请先选择一个要修改的共性目标！")
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
           var updateBtn = document.getElementById("update");
           updateBtn.setAttribute("data-toggle", "modal");
       }
    });

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

        // 修改
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
                    window.location.reload();
                }
            });
        }
    });

    // 删除
    $("#delete").click(function () {
        var ids = [];
        $("input[name='id']:checked").each(function () {
            ids.push($(this).val())
        });
        if (ids.length === 0) {
            alert("请先选择要删除的共性目标！")
        } else {
            $.ajax({
                url: "/plan/delete",
                type: 'DELETE',
                data: JSON.stringify({
                    "ids": ids
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    window.location.reload();
                }
            })
        }
    });
});