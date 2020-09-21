function editTab(tbid) {
    $("#" + tbid + " td").click(function () {
        let td = $(this);
        let txt = td.text();
        let input = $("<input value='" + txt + "' size='10'>");
        td.empty();
        //td.html("");
        td.append(input);
        td.css({"padding": 0});
        input.css({"margin": 0, "border": 0, "font-size": 20, "color": "red", "text-align": "center"});
        input.width(td.width()).height(td.height());
        input.click(function () {
            return false;
        });
        input.trigger("focus").trigger("select");
        input.blur(function () {
            td.text(this.value);
            input.remove();
        });
    });
}

function addFormData(formid, obj) {
    $("#" + formid + " :input[name]").each(function () {
        $(this).val(obj[this.name]);
    });
}

function addSelect(selectid, data, val, txt) {
    let sel = $("#" + selectid);
    for (let i in data) {
        sel.append($("<option value='" + data[i][val] + "'>" + data[i][txt] + "</option>"));
    }
}

function formatDate(date) {
    var date = new Date(date);
    var YY = date.getFullYear() + '-';
    var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
    var hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
    return YY + MM + DD +" "+hh + mm + ss;
}

function addTab(tbodyid, data, titles) {
    let tb = $("#" + tbodyid);
    for (let i in data) {
        let tr = "<tr>";
        let obj = data[i];
        for (let index in titles) {
            tr += "<td>" + obj[titles[index]] + "</td>"
        }
        tr += "</tr>";
        tb.append($(tr));
    }
}