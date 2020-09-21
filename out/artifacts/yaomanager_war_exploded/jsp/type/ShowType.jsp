<%--
  Created by IntelliJ IDEA.
  User: 409863254
  Date: 2020/9/16
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/js/jquery-3.2.1.min.js"></script>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
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

</style>
<body>
<h1>药品类型</h1>
<div>
    <form action="">
         类型<input type="text" id="type1"> <br>
        <input type="button" value="提交" id="btn"><br>
    </form>

</div>


<table class="table table-bordered">
    <tr>
        <th>序号</th>
        <th>类型</th>
        <th colspan="2"><input type='button' value='添加'  class='btn btn-primary' data-toggle='modal' data-target='#insert' ></th>
    </tr>
    <tbody id="tb">

    </tbody>

</table>

当前是第<span id="num"></span>页&nbsp;&nbsp;&nbsp;总共有<span id="totalPage"></span>页
<a href="javascript:search(pageBean.pageNo-1)">上一页</a>
<a href="javascript:search(pageBean.pageNo+1)">下一页</a>



<%--//添加模态框--%>
<div class="modal fade bs-example-modal-lg" id="insert" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
                <h1>类型添加</h1>
            <form action="">
                类型：<input type="text" id="insertType"> <br>
                <input type="button" value="添加" data-dismiss="modal"  id="insertbtn">

            </form>


        </div>
    </div>
</div>


<%--//修改模态框--%>
<div class="modal fade bs-example-modal-lg" id="update" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <form action=" " >
                序号：<input type="text" id="typeid" readonly> <br>
                类型：<input type="text" id="mtype"><br>
                <input type="button" value="修改"  data-dismiss="modal" id="updateBtn"  >
            </form>


        </div>
    </div>
</div>
<script>



    // 分页显示
    let pageBean = new Object();
    pageBean.pageSize = 6;
    pageBean.totalPage = 0;
    function search(num) {
        pageBean.factor = [];
        if (0 < num && num <= pageBean.totalPage) {
            pageBean.pageNo = num;
        }


        if ($("#type1").val() != null && $("#type1").val() != "") {
            pageBean.factor.push("   mtype like '%"+$("#type1").val()+"%' ");
        }
        pageBean.data = null;
        $.ajax({
            url: "/records/showtype",
            type: "post",
            data: pageBean,
            success: function (page) {
                $("#tb").empty();

                pageBean = page;
                addTab1("tb", pageBean.data, ["typeid","mtype"]);
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
            tr += "<td>" + obj[titles[1]] + "</td>";
            tr += "<td><input type='button'  value='修改' onclick='findOneType("+obj[titles[0]]+")'    class='btn btn-primary' data-toggle='modal' data-target='#update' ></td>";
            tr += "<td><a href='/records/deleteOne?id="+obj[titles[0]]  +"'   class='btn btn-primary'  id='abtn'>删除<a></td>";
            tr += "</tr>";
            tb.append($(tr));
        }
    }
    // 查询一个
    function findOneType(id){
        $.ajax({
            url: "/records/findOneType",
            type: "post",
            data: {"id":id},
            success: function (page) {
                console.log(page);
                $("#typeid").val(page["typeid"]);
                $("#mtype").val(page["mtype"]);
            },
            dataType: "json"
        })
    }

    // 修改按钮 修改事件
    $("#updateBtn").click(function () {
        let mtype= $("#mtype").val();
        let typeid=$("#typeid").val();

        $.ajax({
            url: "/records/updateOne",
            type: "post",
            data: {"mtype":mtype,"typeid":typeid},
            success: function () {},
            dataType: "json"
        })
        // 修改成功后 刷新页面
        search(1);
    });
    // 添加按钮 添加数据
    $("#insertbtn").click(function () {
        let insertType= $("#insertType").val();
        console.log(insertType);

        $.ajax({
            url: "/records/insertOne",
            type: "post",
            data: {"mtype":insertType},
            success: function () {},
            dataType: "json"
        })
        // 修改成功后 刷新页面
        search(1);
    });
    $('#update').modal(options)
    $('#insert').modal(options)
</script>

</body>
</html>
