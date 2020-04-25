$(document).ready(function () {
    navLocation("daily_manage", "score_self", "score_self_li");
    initPage();
    getPlans();

    // select改变获取对应月度的计划
    $("#yyyy").change(function () {
        getPlans();
    });
    $("#mm").change(function () {
        getPlans();
    });

    $("#save").click(function () {
        var scores = $("[name='selfScore']");
        var arr = new Array();
        for (var i = 0; i < scores.length; i++) {
            var id = scores[i].getAttribute("relationId") * 1.0;
            var score = scores[i].value * 1.0;
            var relation = {id: id, score: score};
            arr.push(relation);
        }
        $.ajax({
            url: "/response/addSelfScores",
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "relations": arr
            }),
            dataType: "json",
            success: function (data) {
                alert(data.msg);
            }
        });
    });
});

// 获取月度计划
function getPlans() {
    var date = getMonthly();
    // 清空表
    $("#self_tbl tr:not(:first)").html("");
    $.ajax({
        url: "/response/self/show/" + date,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            var selfTbl = $("#self_tbl");
            for (var i in data) {
                selfTbl.append("<tr>"
                    + "<td>" + transferTime(data[i].plan.monthly) + "</td>"
                    + "<td>" + data[i].plan.content + "</td>"
                    + "<td class='score' value=" + data[i].plan.score + ">" + data[i].plan.score + "</td>"
                    + "<td><input type='text' class='form-control' relationId=" + data[i].id + " name='selfScore'"
                    + "score=" + data[i].plan.score+ "></td>"
                    + "<td><span class='detail' "
                    + "style='text-decoration: underline'  data-toggle='modal' data-target='#details' value="
                    + data[i].plan.detail + ">详细信息</span></td></tr>");
            }

            selfTbl.append("<tr>"
                + "<td></td>"
                + "<td style='text-align: center; font-weight: bold'>合计</td>"
                + "<td id='totalScore'></td>"
                + "<td id='totalSelf'></td>"
                + "<td></td>");

            // 总分
            var scores = document.getElementsByClassName("score");
            var total = 0;
            for (var i = 0; i < scores.length; i++) {
                var score = scores[i].getAttribute("value") * 1.0;
                total += score;
            }
            document.getElementById("totalScore").innerText = total;

            $(".detail").click(function() {
                var detail = $(this).attr("value");
                document.getElementById("detail").innerHTML = "";
                $("#detail").append("<span>" + detail + "</span>");
            });

            $("input[name='selfScore']").change(function () {
                if ($(this).val() > $(this).attr("score")) {
                    alert("请重新评分！");
                    $(this).val("");
                }
                var totalSelf = 0;
                var selfScore = document.getElementsByName("selfScore");
                for (var i = 0; i< selfScore.length; i++) {
                    totalSelf += selfScore[i].value * 1.0;
                }
                document.getElementById("totalSelf").innerText = totalSelf;
            });
        }
    });
}