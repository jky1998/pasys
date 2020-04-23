$(document).ready(function () {
    navLocation("daily_manage", "plan_manage", "plan_manage_li");
    showDepartments("departmentId");
    initPage();
    showStaffs();

    var form = '${form}';
    console.log(form);

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
});

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
        }
    });
}