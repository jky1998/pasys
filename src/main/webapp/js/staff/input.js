$(document).ready(function(){
    $("#workdate").datepicker ({
        language:"zh-CN"
    });

    var department = document.getElementById("department");
    console.log(department);
    $.ajax({
        url: "/department/show",
        type: 'GET',
        dataType: "json",
        success: function (data) {
            console.log(data);
            for (var i in data) {
                department.options.add(new Option(data[i].name, data[i].id));
            }
        }
    })
});