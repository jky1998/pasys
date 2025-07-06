$(document).ready(function () {
    navLocation("date_query","monthly_statistic","monthly_statistic_li");
    initPage();
    showMonthlyTbl();
    $("#yyyy").change(function () {
        showMonthlyTbl();
    });
    $("#mm").change(function () {
        showMonthlyTbl();
    });
});
function showMonthlyTbl() {
    $("#monthly_tbl tr:not(:first)").html("");
    var monthly = getMonthly();
    $.ajax({
        url: "/score/monthly/show/" + monthly,
        type: 'POST',
        dataType: "json",
        success:function (data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].selfScore == -1) {
                    var selfScore = "暂未评分";
                } else {
                    selfScore = data[i].selfScore.toFixed(2);
                }
                if (data[i].departScore == -1) {
                    var departScore = "暂未评分";
                } else {
                    departScore = data[i].departScore.toFixed(2);
                }
                if (data[i].assessorScore == -1) {
                    var assessorScore = "暂未评分";
                } else {
                    assessorScore = data[i].assessorScore.toFixed(2);
                }
                if (data[i].average == -1) {
                    var average = "暂未评分";
                } else {
                    average = data[i].average.toFixed(2);
                }

                $("#monthly_tbl").append("<tr>" +
                    "<td>" + data[i].relation.staff.no + "</td>" +
                    "<td>" + data[i].relation.staff.name + "</td>" +
                    "<td>" + transferTime(data[i].relation.plan.monthly) + "</td>" +
                    "<td>" + selfScore + "</td>" +
                    "<td>" + departScore + "</td>" +
                    "<td>" + assessorScore + "</td>" +
                    "<td>" + average + "</td>" +
                    "</tr>")
            }
        }
    })
}