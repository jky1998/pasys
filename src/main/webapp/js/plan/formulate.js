$(document).ready(function () {
    navLocation("daily_manage", "plan_formulate", "plan_formulate_li");
    // 显示日期选择
    initPage();
    getPlans();

    // select改变获取对应月度的计划
    $("#yyyy").change(function () {
        getPlans()
    });
    $("#mm").change(function () {
        getPlans()
    });

    // 复制上月计划
    $("#copyLastPlan").click(function () {
        var date = getMonthly();
        var lastMonthly = new Date();
        if (date.getMonth() === 0) {
            lastMonthly.setFullYear(date.getFullYear() - 1);
            lastMonthly.setMonth(11);
            lastMonthly.setDate(1);
        } else {
            lastMonthly.setFullYear(date.getFullYear());
            lastMonthly.setMonth(date.getMonth() - 1);
            lastMonthly.setDate(1);
        }

        $.ajax({
            url: "/plan/copyLastPlan",
            type: 'POST',
            data: JSON.stringify({
                "monthly": date,
                "lastMonthly": lastMonthly
            }),
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            success: function (data) {
                alert(data.msg);
                getPlans();
            }
        });
    });

    // 添加计划明细
    $("#submit_add").click(function () {
        var monthly = getMonthly();
        // 判断输入规范
        var content = document.getElementById("addContent").value;
        var detail = document.getElementById("addDetail").value;
        var score = document.getElementById("addScore").value;
        var flag;
        while (true) {
            flag = checkContent(content);
            if (!flag) {
                break;
            }
            flag = checkDetail(detail);
            if (!flag) {
                break;
            }
            flag = checkScore(score);
            if (!flag) {
                break;
            }
            break;
        }

        // 添加目标
        if (flag) {
            $.ajax({
                url: "/plan/monthly/add",
                type: 'POST',
                data: JSON.stringify({
                    "content": content,
                    "detail": detail,
                    "score": score,
                    "monthly": monthly
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    getPlans();
                }
            });
        }
    });

    // 复制共性目标
    $("#copy").click(function () {
        var date = getMonthly();

        $.ajax({
            url: "/plan/copy/monthly=" + date,
            type: 'POST',
            dataType: "json",
            success: function (data) {
                alert(data.msg);
                getPlans();
            }
        });
    });

    // 修改
    $("#submit_update").click(function () {
        // 判断输入规范
        var content = document.getElementById("updateContent").value;
        var detail = document.getElementById("updateDetail").value;
        var score = document.getElementById("updateScore").value;
        var flag;
        while (true) {
            flag = checkContent(content);
            if (!flag) {
                break;
            }
            flag = checkDetail(detail);
            if (!flag) {
                break;
            }
            flag = checkScore(score);
            if (!flag) {
                break;
            }
            break;
        }
        if (flag) {
            var id = $("#updateId").val();
            $.ajax({
                url: "/plan/update/" + id,
                type: 'PUT',
                data: JSON.stringify({
                    "content": content,
                    "detail": detail,
                    "score": score
                }),
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    getPlans();
                }
            });
        }
    });

    // 获取要修改的目标
    $("#getUpdatePlan").click(function () {
        var ids = [];
        var id;
        $("input[name='id']:checked").each(function () {
            ids.push($(this).val());
        });
        if (ids.length === 0 || ids.length > 1) {
            alert("请先选择一个要修改的计划！")
        } else {
            id = ids[0];
            $.ajax({
                url: "/plan/getUpdatePlan/" + id,
                type: 'POST',
                dataType: "json",
                success: function (data) {
                    $("#updateId").val(data.id);
                    $("#updateContent").val(data.content);
                    $("#updateDetail").val(data.detail);
                    $("#updateScore").val(data.score);
                }
            });
            // 弹出dialog
            var updateBtn = document.getElementById("getUpdatePlan");
            updateBtn.setAttribute("data-toggle", "modal");
        }
    });

    // 删除
    $("#delete").click(function () {
        var ids = [];
        $("input[name='id']:checked").each(function () {
            ids.push($(this).val());
        });
        if (ids.length === 0) {
            alert("请先选择要删除的计划！")
        } else {
            $.ajax({
                url: "/plan/delete",
                type: 'DELETE',
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify({
                   ids: ids
                }),
                dataType: "json",
                success: function (data) {
                    alert(data.msg);
                    getPlans();
                }
            });
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

function initPage() {
    var yyyy = document.getElementById("yyyy");
    var mm = document.getElementById("mm");

    var date = new Date();
    var curY = date.getFullYear();
    var curM = date.getMonth() + 1;

    initYMD(yyyy, 1999, curY + 1);
    initYMD(mm, 1, 12);

    // 控制选中当前年月
    for (var i = 0; i < yyyy.length; i++) {
        if (yyyy[i].value == curY) {
            yyyy.selectedIndex = i;
        }
    }
    for (var i = 0; i < mm.length; i++) {
        if (mm[i].value == curM) {
            mm.selectedIndex = i;
        }
    }
}

// 初始化年月
function initYMD(obj, start, end) {
    for (var i = start; i <= end; i++) {
        obj.options.add(new Option(i, i));
    }
}

// 获取月度计划
function getPlans() {
    var date = getMonthly();
    // 清空表
    $("#plan_tbl tr:not(:first)").html("");
    $.ajax({
        url: "/plan/formulate/monthly=" + date,
        type: 'POST',
        dataType: "json",
        success: function (data) {
            for (var i in data) {
                $("#plan_tbl").append("<tr>"
                    + "<td><input type='checkbox' name='id' value=" + data[i].id + "></td>"
                    + "<td>" + transferTime(data[i].monthly) + "</td>"
                    + "<td>" + data[i].content + "</td>"
                    + "<td><span class='detail' "
                    + "style='text-decoration: underline'  data-toggle='modal' data-target='#details' value="
                    + data[i].detail + ">详细信息</span></td></tr>");
            }

            $(".detail").click(function(){
                var detail = $(this).attr("value");
                document.getElementById("detail").innerHTML = "";
                $("#detail").append("<span>" + detail + "</span>");
            });
        }
    });
}

// 获取月度
function getMonthly() {
    var year = $("#yyyy").find("option:selected").text();
    var month = $("#mm").find("option:selected").text();
    var date = new Date();
    date.setFullYear(year);
    date.setMonth(month - 1);
    date.setDate(1);
    return date;
}

// 日期格式化
function transferTime(cTime) {     //将json串的一串数字进行解析
    var jsonDate = new Date(parseInt(cTime));
    //       alert(jsonDate);
    //为Date对象添加一个新属性，主要是将解析到的时间数据转换为我们熟悉的“yyyy-MM-dd hh:mm:ss”样式
    Date.prototype.format = function(format) {
        var o = {
            //获得解析出来数据的相应信息，可参考js官方文档里面Date对象所具备的方法
            "y+": this.getFullYear(), //得到对应的年信息
            "M+": this.getMonth() + 1, //得到对应的月信息，得到的数字范围是0~11，所以要加一
            "d+": this.getDate(), //得到对应的日信息
            "h+": this.getHours(), //得到对应的小时信息
            "m+": this.getMinutes(), //得到对应的分钟信息
            "s+": this.getSeconds(), //得到对应的秒信息
        }
        //将年转换为完整的年形式
        if(/(y+)/.test(format)) {
            format = format.replace(RegExp.$1,    (this.getFullYear() + "") .substr(4 - RegExp.$1.length));
        }
        //连接得到的年月日 时分秒信息
        for(var k in o) {
            if(new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1,  RegExp.$1.length == 1 ? o[k] : ("00" + o[k]) .substr(("" + o[k]).length));
            }
        }
        return format;
    };
    var newDate = jsonDate.format("yyyy-MM");
    return newDate;
}