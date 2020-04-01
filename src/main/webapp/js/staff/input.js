$(document).ready(function(){
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
            var position = document.getElementById("position").value;
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
        }

        // 数据传输
        if (flag) {
            $.ajax({
                url: "/staff/add",
                type: 'POST',
                data: {
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
                },
                dataType: "json",
                success: function (msg) {
                    console.log(msg);
                }
            })
        }

    });

    // 清空
    $("#reset").click(function () {
        document.getElementById("no").value = "";
        document.getElementById("name").value = "";
        $("#female").removeAttr("checked");
        document.getElementById("male").checked = true;
        document.getElementById("workdate").value = "";
        resetSelectValue("department");
        document.getElementById("position").value = "";
        document.getElementById("phone").value = "";
        document.getElementById("mail").value = "";
        document.getElementById("idcard").value = "";
        document.getElementById("address").value = "";
        document.getElementById("password").value = "";
    });

    // 表单验证
    function checkNo(no) {
        if (no === "") {
            alert("工号不可为空！");
            return false;
        } else if (no.length > 20) {
            alert("工号过长，请重新输入！");
            return false;
        } else
            return true;
    }
    function checkName(name) {
        if (name === "") {
            alert("姓名不可为空！");
            return false;
        } else if (name.length > 20) {
            alert("姓名过长，请重新输入！");
            return false;
        } else
            return true;
    }
    function checkWorkDate(workdate) {
        if (workdate === "") {
            alert("请选择入职日期！");
            return false;
        } else
            return true;
    }
    function checkDepartment(departmentId) {
        if (departmentId === "0") {
            alert("请选择部门！");
            return false;
        }else
            return true;
    }
    function checkPosition(position) {
        if (position === "") {
            alert("职位不可为空！");
            return false;
        } else if (position.length > 50) {
            alert("职位过长，请重新输入！");
            return false;
        } else
            return true;
    }
    function checkPhone(phone) {
        if (phone === "") {
            alert("联系电话不可为空！");
            return false;
        } else if(!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(phone))){
            alert("手机号码有误，请重新输入");
            return false;
        } else
            return true;
    }
    function checkMail(mail) {
        if (mail === "") {
            alert("邮箱不可为空！");
            return false;
        } else if (!(/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(mail))) {
            alert("邮箱有误，请重新输入！");
            return false;
        } else if (mail.length > 50) {
            alert("邮箱过长，请重新输入！")
            return false;
        } else
            return true;
    }
    function checkIdCard(idcard) {
        if (idcard === "") {
            alert("身份证号不可为空！");
            return false;
        } else if (!(/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/.test(idcard))) {
            alert("身份证号有误，请重填！");
            return false;
        } else
            return true;
    }
    function checkAddress(address) {
        if (address === "") {
            alert("地址不可为空！");
            return false;
        } else if (address.length > 100) {
            alert("地址过长，请重新输入！");
            return false;
        } else
            return true;
    }
    function checkPassword(password) {
        if (password === "") {
            alert("密码不可为空！");
            return false;
        } else if (password.length > 50) {
            alert("密码过长，请重新输入！");
            return false;
        } else
            return true;
    }

    // 获取radio选项值
    function getRadioValue(RadioName){
        var obj;
        obj=document.getElementsByName(RadioName);
        if(obj!=null){
            var i;
            for(i=0;i<obj.length;i++){
                if(obj[i].checked){
                    return obj[i].value;
                }
            }
        }
        return null;
    }

    // 获取select选项值
    function getSelectValue(selectId) {
        var obj = document.getElementById(selectId);
        var index = obj.selectedIndex;
        var value = obj.options[index].value;
        return value;
    }
    // reset select选项值
    function resetSelectValue(selectId) {
        var obj = document.getElementById(selectId);
        obj.selectedIndex = 0;
    }
});