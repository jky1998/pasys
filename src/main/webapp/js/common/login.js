// $(document).ready(function () {
//     $(":submit").click(function () {
//         var name = document.getElementById("name").value;
//         var password = document.getElementById("password").value;
//         $.ajax({
//             url: "/common/check",
//             type: 'POST',
//             data: JSON.stringify({
//                 "name": name,
//                 "password": password
//             }),
//             contentType: "application/json;charset=UTF-8",
//             dataType: "text",
//             success: function (data) {
//                 if (data === "success") {
//                     window.location.href = "/staff/input";
//                 } else if (data === "wrong") {
//                     document.getElementById("msg").innerText = "密码错误！";
//                 }
//             }
//         });
//     });
// });