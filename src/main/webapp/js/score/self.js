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
});

// 获取月度计划
function getPlans() {
    var date = getMonthly();
    // 清空表
    $("#self_tbl tr:not(:first):not(:last)").html("");
    $.ajax({
        url: "/plan/formulate/monthly=" + date,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            var selfTbl = $("#self_tbl");
            for (var i in data) {
                selfTbl.append("<tr>"
                    + "<td>" + transferTime(data[i].monthly) + "</td>"
                    + "<td>" + data[i].content + "</td>"
                    + "<td>" + data[i].score + "</td>"
                    + "<td><input type='text' class='selfScore form-control' name='selfScore'></td>"
                    + "<td><span class='detail' "
                    + "style='text-decoration: underline'  data-toggle='modal' data-target='#details' value="
                    + data[i].detail + ">详细信息</span></td></tr>");
            }

            selfTbl.append("<tr>"
                + "<td></td>"
                + "<td style='text-align: center; font-weight: bold'>合计</td>"
                + "<td id='totalScore'></td>"
                + "<td id='totalSelf'></td>"
                + "<td></td>");

            $(".detail").click(function(){
                var detail = $(this).attr("value");
                document.getElementById("detail").innerHTML = "";
                $("#detail").append("<span>" + detail + "</span>");
            });
        }
    });
}