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
    $('#staff option').not('option:first').remove();
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

function format(num) {
    num = num + "";
    var date = "";
    var month = new Array();
    month["Jan"] = 1; month["Feb"] = 2; month["Mar"] = 3; month["Apr"] = 4; month["May"] = 5; month["Jan"] = 6;
    month["Jul"] = 7; month["Aug"] = 8; month["Sep"] = 9; month["Oct"] = 10; month["Nov"] = 11; month["Dec"] = 12;
    var week = new Array();
    week["Mon"] = "一"; week["Tue"] = "二"; week["Wed"] = "三"; week["Thu"] = "四"; week["Fri"] = "五"; week["Sat"] = "六"; week["Sun"] = "日";
    str = num.split(" ");
    date = str[5] + "-";
    date = date + month[str[1]];
    return date;
}