function checkContent(content) {
    if (content === "") {
        alert("目标内容不可为空！");
        return false;
    } else if (content.length > 500) {
        alert("目标内容过长，请重新输入！");
        return false
    } else {
        return true;
    }
}

function checkDetail(detail) {
    if (detail === "") {
        alert("考核标准不可为空！");
        return false;
    } else if (detail.length > 500) {
        alert("考核标准过长，请重新输入！");
        return false
    } else {
        return true;
    }
}

function checkScore(score) {
    if (isNaN(score)) {
        alert("请输入一个数字！");
        return false;
    } else if (score > 100) {
        alert("分值过大，请重新输入！");
        return false
    } else if (score === "") {
        alert("分值不可为空！");
        return false;
    } else {
        return true;
    }
}