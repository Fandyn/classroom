<%--
  Created by IntelliJ IDEA.
  User: 409863254
  Date: 2020/9/16
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/myjs.js"></script>
    <style>
        th{
            text-align: center;
        }
        td{
            text-align: center;
        }

    </style>
</head>
<body>
    <h1>详细物品</h1>

    <table class="table table-bordered">
        <tr>
            <th>药品名称</th>
            <th>数量</th>
            <th>规格</th>
            <th>单价</th>
            <th>金额</th>
        </tr>
        <tbody id="tb">


        </tbody>

    </table>
    <script>

        $(function () {
            $.ajax({
                url: "/records/xiangxi",
                type: "post",
                data: rid,
                success: function (page) {
                    console.log(page);
                    $("#tb").empty();
                    addTab1("tb",page, ["sname","snumber","snorms","sprice"]);
                },
                dataType: "json"
            })
        })



        // 插入表格
        function addTab1(tbodyid, data, titles) {
            let tb = $("#" + tbodyid);
            for (let i in data) {
                let tr = "<tr>";
                let obj = data[i];
                tr += "<td>" + obj[titles[0]] + "</td>";
                tr += "<td>" + obj[titles[1]] + "</td>";
                tr += "<td>" + obj[titles[2]] + "</td>";
                tr += "<td>" + obj[titles[3]] + "</td>";
                let num=0;
                    // parseFloat(obj[titles[3]])*parseFloat(obj[titles[1]];
                tr += "<td>" + num + "</td>";


                tr += "</tr>";
                tb.append($(tr));
            }
        }
    </script>


</body>

</html>
