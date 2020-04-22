$(document).ready(function () {
    navLocation("daily_manage", "plan_input", "plan_input_li");
    showDepartments("department");
    showStaffs();
    
    // 根据部门查看员工
    $("#department").change(function () {
        showStaffs();
    });

    initPage();

    // select改变获取对应月度的计划
    $("#yyyy").change(function () {
        showPlans();
    });
    $("#mm").change(function () {
        showPlans();
    });

    
    $("#addRelation").click(function () {
        var staffName = document.getElementById("staffName");
        staffName.removeAttribute("readonly");
        var ids = [];
        var nos = [];
        $("input[name='id']:checked").each(function(){
            ids.push($(this).val());
            nos.push($(this).attr("sname"));
        });
        if (ids.length === 0) {
            alert("请先选择员工！");
        } else {
            $("#staffId").val(ids);
            var str = "";
            for (var i = 0; i < nos.length; i++) {
                str += nos[i] + "; ";
            }
            staffName.value = str;
            staffName.setAttribute("readonly", "readonly");
            var addRelation = document.getElementById("addRelation");
            addRelation.setAttribute("data-toggle", "modal");
            showPlans();
        }
    });
    
    $("#submit_add").click(function () {
        var str = $("#staffId").val();
        var staffIds = str.split(',');
        var planIds = [];
        $("input[name='planId']:checked").each(function () {
            planIds.push($(this).val());
        });
        if (planIds.length === 0) {
            alert("请选择计划！");
        } else {
            $.ajax({
                url: "/response/add",
                type: 'POST',
                data: JSON.stringify({
                    "staffIds": staffIds,
                    "planIds": planIds
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert("添加成功！")
                }
            })
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

function showDepartments(idName) {
    var department = document.getElementById(idName);
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
    // 清空表
    $("#staff_tbl tr:not(:first)").html("");
    var departmentId = $("#department").val();
    $.ajax({
        url: "/staff/show/" + departmentId,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                $("#staff_tbl").append("<tr>" +
                    "<td><input type='checkbox' name='id' value=" + data[i].id
                    + " sname=" + data[i].name + "></td>" +
                    "<td>" + data[i].no + "</td>" +
                    "<td>" + data[i].name + "</td>" +
                    "<td>" + data[i].department.name + "</td>")
            }
        }
    });
}

function showPlans() {
    var date = getMonthly();
    $("#plan").empty();
    var plan = document.getElementById("plan");
    $.ajax({
        url: "/plan/formulate/monthly=" + date,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                //plan.options.add(new Option(data[i].content, data[i].id));
                $("#plan").append("<input type='checkbox' name='planId' value="
                    + data[i].id + "><span>"
                    + data[i].content + "</span><br>")
            }
        }
    });
}