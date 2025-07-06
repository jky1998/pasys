$(document).ready(function(){
    // 获得系统时间
    var date = new Date();
    document.getElementById("date").innerHTML =
        date.getFullYear() + "年" + date.getMonth() + "月" + date.getDate() + "日";
    document.getElementById("day").innerHTML =
        "星期" + "日一二三四五六".charAt(date.getDay());
});