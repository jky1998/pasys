$(document).ready(function(){
    var obj = document.getElementById("system_setup");
    obj.style.display = 'block';
    navLocation("staff_management", "staff_input", "staff_input_li");

    $("#workdate").datepicker ({
        language:"zh-CN"
    });

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

    // 提交
    $("#submit").click(function () {
        // 表单验证
        var flag;
        while (true) {
            var no = document.getElementById("no").value;
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
                url: "/staff/add",
                type: 'POST',
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
                        reset();
                    }
                }
            })
        }

    });

    // 点击重置按钮
    $("#reset").click(function () {
        reset();
    });
});

// 清空方法
function reset() {
    document.getElementById("no").value = "";
    document.getElementById("name").value = "";
    $("#female").removeAttr("checked");
    document.getElementById("male").checked = true;
    document.getElementById("workdate").value = "";
    resetSelectValue("department");
    resetSelectValue("position");
    document.getElementById("phone").value = "";
    document.getElementById("mail").value = "";
    document.getElementById("idcard").value = "";
    document.getElementById("address").value = "";
    document.getElementById("password").value = "";
}

