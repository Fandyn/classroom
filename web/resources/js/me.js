$().ready(function () {
    $("#seachBtn").click(function () {
        load();
    })

    function load() {
        $("#father").html("");
        $.ajax({
            url: "/myweb/search",
            data: $("#searchForm").serialize(),
            dataType: "json",
            type: "post",
            success: function (dataInfo) {
                console.log(dataInfo);
                for (var i = 0; i < dataInfo.pageData.length; i++) {
                    var stu = dataInfo.pageData[i];
                    var html = "<tr>\n" +
                        "        <td>" + stu.studentname + "</td>\n" +
                        "        <td>" + stu.gender + "</td>\n" +
                        "        <td>" + stu.age + "</td>\n" +
                        "        <td>" + stu.city + "</td>\n" +
                        "        <td>" + stu.studentno + "</td>\n" +
                        "        <td>" + stu.email + "</td>\n" +
                        "        <td>" + stu.classname + "</td>\n" +
                        "        <td><input type='button' value='删除' class='btn btn-primary' id='" + stu.studentid + "'></td>\n" +
                        "        <td><input type='button' value='修改' class=\"btn btn-primary\" id='" + stu.studentid + "' data-toggle=\"modal\" data-target=\".update\"></td>\n" +
                        "    </tr>"
                    $("#father").append(html);
                }
                console.log($("#pagesCount"))
                $("#pagesCount").html(dataInfo.pagesCount);
                $("#rowsCount").html(dataInfo.rowsCount);
                $("#pageNow").html(dataInfo.pageNow);
                $("#pageRows").html(dataInfo.pageRows);

            }
        })
    }

    load()
    $("#father").on("click", "input[value='删除']", function () {
        var studentid = this.id;
        $.ajax({
            url: "/myweb/deleteByStudentId",
            data: {studentid: studentid},
            dataType: "json",
            type: "post",
            success: function (dataInfo) {
                if (dataInfo.state) {
                    $("#father").html("");
                    load();
                    alert(dataInfo.message)
                } else {
                    alert(dataInfo.message)
                }
            }
        })
    })
    $("#addbtn").click(function () {
        $.ajax({
            url: "/myweb/cla/search",
            data: {},
            async: false,
            dataType: "json",
            type: "post",
            success: function (dataInfo) {
                console.log(dataInfo)
                if (dataInfo.state) {
                    for (var i = 0; i < dataInfo.data.length; i++) {
                        console.log(dataInfo.data[i]);
                        var cla = dataInfo.data[i];
                        //var html='<option id="'+ cla.classid+'">'+ cla.classname+'</option>';
                        op = $("#clone").clone();
                        op.attr('value', cla.classid);
                        console.log(cla.classid);
                        op.html(cla.classname);
                        // console.log(html);
                        $("#classid").append(op);
                    }
                } else {
                    alert(dataInfo.message);
                }
            }
        })
    })
    $("#insertStu").click(function () {
        $.ajax({
            url: "/myweb/stu/insert",
            data: {
                "stuname": $("#stuname").val(),
                "gender": $("#gender").val(),
                "studentno": $("#stuno").val(),
                "email": $("#email").val(),
                "classid": $("#classid").val()
            },
            dataType: "json",
            type: "post",
            success: function (dataInfo) {
                console.log(dataInfo)
                if (dataInfo.state) {
                    console.log(dataInfo)
                } else {
                    alert(dataInfo.message);
                }
            }
        })
    })
    $("#father").on("click", "input[value='修改']", function () {
        $.ajax({
            url: "/myweb/cla/search",
            data: {},
            async: false,
            dataType: "json",
            type: "post",
            success: function (dataInfo) {
                $("#classidU").html("");
                console.log(dataInfo)
                if (dataInfo.state) {
                    for (var i = 0; i < dataInfo.data.length; i++) {
                        // console.log(dataInfo.data[i]);
                        var cla = dataInfo.data[i];
                        //var html='<option id="'+ cla.classid+'">'+ cla.classname+'</option>';
                        op = $("#clone").clone();
                        op.attr('value', cla.classid);
                        // console.log(cla.classid);
                        op.html(cla.classname);
                        // console.log(html);
                        $("#classidU").append(op);
                    }
                } else {
                    alert(dataInfo.message);
                }
            }
        })
        var studentid = this.id;
        $.ajax({
            url: "/myweb/stu/searchStuById",
            data: {studentid: studentid},
            dataType: "json",
            type: "post",
            success: function (dataInfo) {
                console.log(dataInfo)
                var stu = dataInfo.data;
                console.log(stu);
                console.log($("#studentid"));
                $("#studentid").val(stu.studentid);
                $("#stunameU").val(stu.studentname);
                $("#genderU").val(stu.gender);
                $("#stunoU").val(stu.studentno);
                $("#emailU").val(stu.email);
            }
        })
    })
    $("#updateStu").click(function () {
        $.ajax({
            url: "/myweb/stu/insertOrUpdate",
            data: $("#ser").serialize(),
            type: "post",
            dataType: "json",
            success: function (dataInfo) {
                if (dataInfo.state) {
                    load();
                } else {
                    alert(dataInfo.message)
                }
            }
        })
    })
    $("#stunoU").blur(function () {
        var studentid = "";
        if ($("#studentid").val() != null && $("#studentid").val() != "" && $("#studentid").val() != "null") {
            studentid = $("#studentid").val();
        }
        $.ajax({
            url: "/myweb/stu/searchStuByNo",
            data: {studentno: $("#stunoU").val(), studentid: studentid},
            type: "post",
            dataType: "json",
            success: function (dataInfo) {
                $("#text").html(dataInfo.message);
                if (dataInfo.state) {
                    $("#updateStu").prop("disabled", false)
                } else {
                    $("#updateStu").prop("disabled", true)
                }
            }
        })
    })
    $("#stuno").blur(function () {
        var studentid = "";
        if ($("#studentid").val() != null && $("#studentid").val() != "" && $("#studentid").val() != "null") {
            studentid = $("#studentid").val();
        }
        $.ajax({
            url: "/myweb/stu/searchStuByNo",
            data: {studentno: $("#stuno").val(), studentid: studentid},
            type: "post",
            dataType: "json",
            success: function (dataInfo) {
                console.log(dataInfo)
                $("#text1").html(dataInfo.message);
                if (dataInfo.state) {
                    $("#insertStu").prop("disabled", false)
                } else {
                    $("#insertStu").prop("disabled", true)
                }
            }
        })
    })
})
