$(document).ready(function() {
    $("#workdate").datepicker ({
        language:"zh-CN"
    });

    // 修改员工信息
    $("#show_update").click(function () {

        // 显示部门选项
        var department = document.getElementById("department");
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

        // 显示要修改的员工信息
        var ids = [];
        var id;
        $("input[name='staffId']:checked").each(function(){
            ids.push($(this).val());
        });
        if (ids.length === 0 || ids.length > 1) {
            alert("请选择一名员工！")
        } else {
            id = ids[0];
            $.ajax({
                url: "/staff/getUpdateStaff/" + id,
                type: 'POST',
                dataType: "json",
                success: function (staff) {
                    console.log(staff);
                    $("#staffId").val(staff.id);
                    $("#updateNo").val(staff.no);
                    $("#name").val(staff.name);
                    if (staff.gender === 1) {
                        $("#female").attr("checked", true);
                    }
                    $("#workdate").val(staff.formatdate);
                    $("#department").val(staff.department.id);
                    $("#position").val(staff.position);
                    $("#phone").val(staff.phone);
                    $("#mail").val(staff.mail);
                    $("#idcard").val(staff.idcard);
                    $("#address").val(staff.address);
                    $("#password").val(staff.password);
                }
            });
            // 弹出dialog
            var updateBtn = document.getElementById("show_update");
            updateBtn.setAttribute("data-toggle", "modal");
            updateBtn.setAttribute("data-target", "#updateStaff");
        }
    });

    // 修改用户信息
    $("#submit_update").click(function () {
        // 表单验证
        var staffId = $("#staffId").val();
        var flag;
        while (true) {
            var no = document.getElementById("updateNo").value;
            flag = checkNo(no);
            if (!flag) {
                break;
            }
            var name = document.getElementById("name").value;
            flag = checkName(name);
            if (!flag) {
                break;
            }
            var gender = getRadioValue("gender");
            var wordate = document.getElementById("workdate").value;
            flag = checkWorkDate(wordate);
            if (!flag) {
                break;
            }
            var departmentId = getSelectValue("department");
            flag = checkDepartment(departmentId);
            if (!flag) {
                break;
            }
            var position = getSelectValue("position");
            flag = checkPosition(position);
            if (!flag) {
                break;
            }
            var phone = document.getElementById("phone").value;
            flag = checkPhone(phone);
            if (!flag) {
                break;
            }
            var mail = document.getElementById("mail").value;
            flag = checkMail(mail);
            if (!flag) {
                break;
            }
            var idcard = document.getElementById("idcard").value;
            flag = checkIdCard(idcard);
            if (!flag) {
                break;
            }
            var address = document.getElementById("address").value;
            flag = checkAddress(address);
            if (!flag) {
                break;
            }
            var password = document.getElementById("password").value;
            flag = checkPassword(password);
            if (!flag) {
                break;
            }
            break;
        }

        // 数据传输
        if (flag) {
            $.ajax({
                url: "/staff/update/" + staffId,
                type: 'PUT',
                data: JSON.stringify({
                    "no": no,
                    "name": name,
                    "gender": gender,
                    "password": password,
                    "workdate": wordate,
                    "position": position,
                    "mail": mail,
                    "phone": phone,
                    "idcard": idcard,
                    "address": address,
                    "departmentId": departmentId
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    if (data.flag === false) {
                        alert(data.msg);
                    } else if (data.flag === true) {
                        alert(data.msg);
                        window.location.reload();
                    }
                }
            })
        }
    });

    // 删除员工
    $("#delete").click(function () {
        var ids = [];
        $("input[name='staffId']:checked").each(function(){
            ids.push($(this).val());
        });
        if (ids.length === 0) {
            alert("请先选择要删除的员工！");
        } else {
            $.ajax({
                url: "/staff/delete",
                type: "DELETE",
                data: JSON.stringify({
                    "ids": ids
                }),
                contentType: "application/json;charset=UTF-8",
                success: function () {
                    alert("删除成功！");
                    window.location.reload()  //刷新页面
                }
            })
        }
    });

});