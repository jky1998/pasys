$(document).ready(function () {
    navLocation("daily_manage", "plan_formulate", "plan_formulate_li");
    // 显示日期选择
    initPage();

    var date = getMonthly();
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
                    + data[i].detail + ">详细信息</span></td>");
            }

            $(".detail").click(function(){
                var detail = $(this).attr("value");
                document.getElementById("detail").innerHTML = "";
                $("#detail").append("<span>" + detail + "</span>");
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
                window.location.reload();
            }
        });
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
            alert(ids);
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

    // 显示详细信息
    // var index = $(".detail").index(this);
    // document.getElementsByClassName("detail")[index].click();
    var span = document.getElementsByClassName("detail");
    console.log(span);

    // $(".detail").click(function () {
    //     var detail = $(this).attr("value");
    //     alert(detail);
    // });

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