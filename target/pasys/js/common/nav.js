$(document).ready(function(){
    // 点击下拉菜单
    $(".drop").click(function(){
        var index = $('.drop').index(this);
        var dropbtn = document.getElementsByClassName("hide_title");
        var span = document.getElementsByClassName("drop");
        if (dropbtn[index].style.display === '') {
            dropbtn[index].style.display = 'block';
            span[index].className = "glyphicon glyphicon-triangle-top drop";
        } else if (dropbtn[index].style.display === 'block') {
            dropbtn[index].style.display = '';
            span[index].className = "glyphicon glyphicon-triangle-bottom drop";
        }
    });
});

function navLocation(boxName, aName, liName) {
    var obj = document.getElementById(boxName);
    obj.style.display = 'block';
    var a = document.getElementById(aName);
    a.style.color = "white";
    var li = document.getElementById(liName);
    li.style.background = "#5e5e5e";
}