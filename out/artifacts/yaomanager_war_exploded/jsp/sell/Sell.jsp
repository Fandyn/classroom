<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>
    <!-- Bootstrap -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/jquery-3.2.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->

    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/myjs.js"></script>
</head>
<style>
    th{
        text-align: center;
    }
    td{
        text-align: center;
    }
    table{
        margin:auto;
    }
    .di1{
        width: 720px;
        margin: auto;

    }
</style>
<body>
    <h1>账单展示</h1>
    <div>
        <form action="">
            订单号：<input type="text" id="danhao"> <br>
            时间：<input type="date" id="daytime"><br>
            <input type="button" value="提交" id="btn"><br>
        </form>

    </div>


<table class="table table-bordered">
    <tr>
        <th>出售单号</th>
        <th>出售时间</th>
        <th>总价</th>
        <th>详细账单</th>
    </tr>
    <tbody id="tb">

    </tbody>

</table>

    当前是第<span id="num"></span>页&nbsp;&nbsp;&nbsp;总共有<span id="totalPage"></span>页
    <a href="javascript:search(pageBean.pageNo-1)">上一页</a>
    <a href="javascript:search(pageBean.pageNo+1)">下一页</a>




    <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="di1">
                    <h1 align="center">详细物品</h1>
                    <table class="table table-bordered"  >
                        <tr>
                            <th>药品名称</th>
                            <th>数量</th>
                            <th>规格</th>
                            <th>单价</th>
                            <th>金额</th>
                        </tr>
                        <tbody id="tb1">


                        </tbody>

                    </table>
                </div>


            </div>
        </div>
    </div>

    <script>




        let pageBean = new Object();
        pageBean.pageSize = 6;

        pageBean.totalPage = 0;

        function search(num) {
            pageBean.factor = [];
            if (0 < num && num <= pageBean.totalPage) {
                pageBean.pageNo = num;
            }
            if ($("#danhao").val() != null && $("#danhao").val() != "") {
                pageBean.factor.push("  rid ='" + $("#danhao").val() + "'  ");
            }

            if ($("#daytime").val() != "" && $("#daytime").val() != null) {
                pageBean.factor.push("   rselldate like '" + $("#daytime").val() + "%'  ");
            }
            pageBean.data = null;

            $.ajax({
                url: "/records/selectRecords",
                type: "post",
                data: pageBean,
                success: function (page) {

                    pageBean = page;
                    $("#tb").empty();
                    addTab1("tb", pageBean.data, ["rid","rselldate","price","mid"]);
                    $("#num").html(pageBean.pageNo);
                    $("#totalPage").html(pageBean.totalPage)
                },
                dataType: "json"
            })
        }

        $(function () {
            search(1);
            $("#btn").click(function () {
                search(1);
            });


        })

        // 插入表格
        function addTab1(tbodyid, data, titles) {
            let tb = $("#" + tbodyid);
            for (let i in data) {
                let tr = "<tr>";
                let obj = data[i];

                    tr += "<td>" + obj[titles[0]] + "</td>";
                    tr += "<td>" +formatDate(obj[titles[1]]) + "</td>";
                    tr += "<td>" + obj[titles[2]] + "</td>";
                    tr += "<td><input type='button'  value='详情' onclick='xiangxi("+obj[titles[0]]+")'  class='btn btn-primary' data-toggle='modal' data-target='.bs-example-modal-lg' ></td>";
                    // tr += "<td><a href='/records/xiangxi?rid="+obj[titles[0]]  +"'  id='abtn'>详情<a></td>";
                tr += "</tr>";
                tb.append($(tr));
            }
        }

        function xiangxi(rid) {


            $.ajax({
                url: "/records/xiangxi",
                type: "post",
                data:{"rid":rid },
                success: function (page) {
                    $("#tb1").empty();
                    addTab2("tb1",page, ["sname","snumber","snorms","sprice"]);
                },
                dataType: "json"
            })

        }

        // 详细账单打印
        function addTab2(tbodyid, data, titles) {
            let tb = $("#" + tbodyid);
            for (let i in data) {
                let tr = "<tr>";
                let obj = data[i];

                tr += "<td>" + obj[titles[0]] + "</td>";
                tr += "<td>" + obj[titles[1]] + "</td>";
                tr += "<td>" + obj[titles[2]] + "</td>";
                tr += "<td>" + obj[titles[3]] + "</td>";
                tr += "<td>" + parseFloat(obj[titles[1]])*parseFloat(obj[titles[3]])   + "</td>";
                tr += "</tr>";
                tb.append($(tr));
            }
        }
        // 详细账单打印
        function addTab2(tbodyid, data, titles) {
            let tb = $("#" + tbodyid);
            for (let i in data) {
                let tr = "<tr>";
                let obj = data[i];

                tr += "<td>" + obj[titles[0]] + "</td>";
                tr += "<td>" + obj[titles[1]] + "</td>";
                tr += "<td>" + obj[titles[2]] + "</td>";
                tr += "<td>" + obj[titles[3]] + "</td>";
                tr += "<td>" + parseFloat(obj[titles[1]])*parseFloat(obj[titles[3]])   + "</td>";
                tr += "</tr>";
                tb.append($(tr));
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


        $('#myModal').modal(options)
    </script>

</body>
</html>