$(document).ready(function () {
    navLocation("daily_manage", "score_department", "score_department_li");
    initPage();
    showProgress();
    // select改变
    $("#yyyy").change(function () {
        clear();
        $("#plan_tbl tr:not(:first)").html("");
        showProgress();
    });
    $("#mm").change(function () {
        clear();
        $("#plan_tbl tr:not(:first)").html("");
        showProgress();
    });

    $(".staff").click(function () {
        clear();
        var staffId = this.getAttribute("value");
        this.style.background = "#5e5e5e";
        this.style.color = "white";
        var monthly = getMonthly();
        $("#plan_tbl tr:not(:first)").html("");
        var planTbl = $("#plan_tbl");
        $.ajax({
            url: "/response/department/show",
            type: 'POST',
            data: JSON.stringify({
                "staffId": staffId,
                "monthly": monthly
            }),
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    planTbl.append("<tr>"
                        + "<td>" + data[i].plan.content + "</td>"
                        + "<td class='score' value=" + data[i].plan.score + ">" + data[i].plan.score + "</td>"
                        + "<td><span class='detail' "
                        + "style='text-decoration: underline' data-toggle='modal' data-target='#details' value="
                        + data[i].plan.detail + ">详细信息</span></td></tr>")
                }

                if (data.length !== 0) {
                    // 合计总分
                    planTbl.append("<tr>"
                        + "<td style='text-align: center; font-weight: bold'>合计</td>"
                        + "<td id='totalScore'></td>"
                        + "<td></td>");
                    var scores = document.getElementsByClassName("score");
                    var total = 0;
                    for (var i = 0; i < scores.length; i++) {
                        var score = scores[i].getAttribute("value") * 1.0;
                        total += score;
                    }
                    document.getElementById("totalScore").innerText = total;
                    // 评分
                    planTbl.append("<tr>"
                        + "<td style='text-align: center; font-weight: bold'>评分</td>"
                        + "<td><input type='text' totalScore=" + total + " class='form-control' id='departmentScore' staffId=" + staffId +"></td>"
                        + "<td></td></tr>")
                } else {
                    planTbl.append("<tr>"
                        + "<td style='text-align: center; font-weight: bold'>无</td>"
                        + "<td></td>"
                        + "<td></td></tr>")
                }

                // 详细信息
                $(".detail").click(function() {
                    var detail = $(this).attr("value");
                    document.getElementById("detail").innerHTML = "";
                    $("#detail").append("<span>" + detail + "</span>");
                });

                // 显示已经评分的分数
                var obj = document.getElementById("departmentScore");
                $.ajax({
                    url: "/score/show",
                    type: 'POST',
                    data: JSON.stringify({
                        "monthly": monthly,
                        "staffId": staffId,
                        "type": 0
                    }),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    success: function (data) {
                        if (data != null) {
                            obj.value = data.score;
                        }
                    }
                })
            }
        });
    });

    $("#save").click(function() {
        var monthly = getMonthly();
        var obj = document.getElementById("departmentScore");
        if (obj == null) {
            alert("请先评分！")
        } else {
            var score = obj.value;
            var staffId = obj.getAttribute("staffId");
            var totalScore = obj.getAttribute("totalScore");
            if (isNaN(score)) {
                alert("请输入一个数字！");
            } else if (score > totalScore ) {
                alert("评分过高！");
            }  else {
                $.ajax({
                    url: "/score/department/add",
                    type: 'POST',
                    data: JSON.stringify({
                        "monthly": monthly,
                        "score": score,
                        "staffId": staffId
                    }),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    success: function (data) {
                        alert(data.msg);
                    }
                });
            }
        }
        showProgress();
    });
});

function showProgress() {
    $("#progress_content tr:not(:first)").html("");
    var monthly = getMonthly();
    $.ajax({
        url: "/score/department/progress/" + monthly,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            $("#progress_content").append("<tr>"
                + "<td>"+ transferTime(data.monthly) + "</td>"
                + "<td><div class='progress'>" +
                "<div class='progress-bar' role='progressbar' aria-valuenow=" + (data.percent)*100 + " aria-valuemin='0' aria-valuemax='100'"
                + "style='width: " + (data.percent)*100 + "%;'>"
                + ((data.percent)*100).toFixed(1) + "%</div>" +
                "</div></td>"
                + "</tr>");
        }
    })
}