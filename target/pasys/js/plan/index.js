$(document).ready(function () {
    navLocation("date_query", "plan_index", "plan_index_li");
    showDepartments();
    showStaffs();
    initPage();
    $("#yyyy").val(0);
    $("#mm").val(0);
    showPlans();

    // 根据部门显示员工
    $("#departmentId").change(function () {
        showStaffs();
    });

    $("#query").click(function () {
        showPlans();
    });


});

function showDepartments() {
    var department = document.getElementById("departmentId");
    $.ajax({
        url: "/department/show",
        type: 'GET',
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                department.options.add(new Option(data[i].name, data[i].id));
            }
        }
    });
}

function showStaffs() {
    $('#staffId option').not('option:first').remove();
    var staff = document.getElementById("staffId");
    var departmentId = $("#departmentId").val();
    if (departmentId === undefined) {
        departmentId = 0;
    }
    $.ajax({
        url: "/staff/show/" + departmentId,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                staff.options.add(new Option(data[i].no + " " + data[i].name, data[i].id));
            }
        }
    });
}

function showPlans() {
    $("#plan_tbl tr:not(:first)").html("");
    var departmentId = $("#departmentId").val();
    var staffId = $("#staffId").val();
    var yyyy = $("#yyyy").val();
    var mm = $("#mm").val();

    $.ajax({
        url: "/plan/index/query",
        type: 'POST',
        data: JSON.stringify({
            "departmentId": departmentId,
            "staffId": staffId,
            "yyyy": yyyy,
            "mm": mm
        }),
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#plan_tbl").append("<tr>" +
                    "<td>" + data[i].staff.no + "</td>" +
                    "<td>" + data[i].staff.name + "</td>" +
                    "<td>" + data[i].staff.department.name + "</td>" +
                    "<td>" + transferTime(data[i].plan.monthly) + "</td>" +
                    "<td style='text-decoration: underline' data-toggle='modal' data-target='#details'" +
                    "monthly=" + data[i].plan.monthly + " class='detail'" +
                    " staffId=" + data[i].staff.id + ">详细信息</td>" +
                    "<td style='text-decoration: underline'" +
                    "staffId=" + data[i].staff.id + " class='scores'" +
                    "monthly=" + data[i].plan.monthly + " data-toggle='modal' data-target='#progressModal'>完成情况</td>" +
                    "</tr>");
            }
            $(".detail").click(function () {
                var detail = $("#detail");
                detail.empty();
                var staffId = this.getAttribute("staffId");
                var monthly = this.getAttribute("monthly");
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
                            detail.append("<span>" +
                                data[i].plan.content + "</span><br>");
                        }
                    }
                })
            });

            $(".scores").click(function () {
                var progress = $("#progress_content");
                progress.empty();
                var staffId = this.getAttribute("staffId");
                var monthly = this.getAttribute("monthly");
                $.ajax({
                    url: "/plan/index/progress",
                    type: 'POST',
                    data: JSON.stringify({
                        "staffId": staffId,
                        "monthly": monthly
                    }),
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    success: function (data) {
                        for (var i = 0; i < data.length; i++) {
                            progress.append("<tr>" +
                                "<td>" + data[i].type + "</td>" +
                                "<td>" + data[i].total + "</td>" +
                                "<td class='score'>" + data[i].score + "</td></tr>");
                        }
                        var score = document.getElementsByClassName("score");
                        for (var i = 0; i < score.length; i++) {
                            //console.log(score[i].innerHTML);
                            if (score[i].innerHTML == 0) {
                                score[i].innerHTML = "暂未评分";
                            }
                        }
                    }
                })
            });
        }
    })
}