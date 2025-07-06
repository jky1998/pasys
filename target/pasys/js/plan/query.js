$(document).ready(function () {
    navLocation("date_query", "plan_query", "plan_query_li");
    showDepartments();
    initPage();
    showStaffs();
    showSelected("yyyy", "selectedYyyy");
    showSelected("mm", "selectedMm");

    // 根据部门显示员工
    $("#departmentId").change(function () {
        //$("#staff").empty();
        showStaffs();
    });

    // 显示详细信息
    $(".detail").click(function () {
        var detail = $(this).attr("value");
        document.getElementById("detail").innerHTML = "";
        $("#detail").append("<span>" + detail + "</span>");
    });

    // 格式化日期
    var monthly = document.getElementsByClassName("monthly");
    for (var i = 0; i < monthly.length; i++) {
        var date = monthly[i].getAttribute("value");
        monthly[i].innerText = format(date);
    }
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
            showSelected("departmentId", "selectedDepartment");
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
            showSelected("staffId", "selectedStaff");
        }
    });
}

function showSelected(idName, valueIdName) {
    var obj = document.getElementById(idName);
    var value = document.getElementById(valueIdName).value;
    if (value != undefined) {
        for (var i = 0; i < obj.options.length; i++) {
            if (obj.options[i].value == value) {
                obj.options[i].selected = true;
            }
        }
    }
}
