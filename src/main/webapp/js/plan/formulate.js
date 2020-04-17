$(document).ready(function () {

    var obj = document.getElementById("daily_manage");
    obj.style.display = 'block';
    var planFormulate = document.getElementById("plan_formulate");
    planFormulate.style.color = "white";
    var planFormulateLi = document.getElementById("plan_formulate_li");
    planFormulateLi.style.background = "#5e5e5e";


    // 显示日期选择
    initPage();

    function initPage() {
        var yyyy = document.getElementById("yyyy");
        var mm = document.getElementById("mm");

        var date = new Date();
        var currY = date.getFullYear();
        var curM = date.getMonth() + 1;

        initYMD(yyyy, 1999, currY);
        initYMD(mm, 1, 12);

        // 控制选中当前年月
        yyyy.selectedIndex = yyyy.length - 1;
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
});