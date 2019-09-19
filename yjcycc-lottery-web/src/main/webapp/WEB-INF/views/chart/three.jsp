<%--
  Created by IntelliJ IDEA.
  User: yangjun
  Date: 2019/9/19
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>三星</title>
    <link href="/static/layui/css/layui.css" type="text/css" rel="stylesheet"/>
    <link href="/static/lottery/css/chart.css" type="text/css" rel="stylesheet"/>
    <script src="/static/layui/layui.js"></script>
</head>
<body>

<div id="container" class="layui-container">

    <form class="layui-form">
        <div class="layui-inline" style="height: 48px;">
            <div class="ball ball-bg-green" onclick="changeColor('ball ball-bg-green')"></div>
        </div>
        <div class="layui-inline" style="height: 48px;">
            <div class="ball ball-bg-blue" onclick="changeColor('ball ball-bg-blue')"></div>
        </div>
        <div class="layui-inline" style="height: 48px;">
            <div class="ball ball-bg-orange" onclick="changeColor('ball ball-bg-orange')"></div>
        </div>
        <div class="layui-inline" style="height: 48px;">
            <div class="ball ball-bg-red" onclick="changeColor('ball ball-bg-red')"></div>
        </div>
        <div class="layui-inline" style="margin-bottom: 20px; width: 100px;">
            <select id="lotteryType" name="lotteryType" lay-filter="lotteryType">
                <option value="f1_11x5">1分11选5</option>
                <option value="f3_11x5">3分11选5</option>
            </select>
        </div>
    </form>
    <table id="chartTable">
        <tr>
            <td style="width:135px;">期号</td>
            <td style="width:100px;" class="border_left border_right">前三号码</td>
            <td>01</td>
            <td>02</td>
            <td>03</td>
            <td>04</td>
            <td>05</td>
            <td>06</td>
            <td>07</td>
            <td>08</td>
            <td>09</td>
            <td>10</td>
            <td>11</td>
        </tr>
    </table>
</div>

<script src="/static/lottery/js/chart.js" charset="utf-8"></script>
<script src="/static/jquery/jquery-1.12.4.js" charset="utf-8"></script>

<script>

    init("${chartType}");

    setInterval(function () {
        initSingle("${chartType}");
    }, 5000);

</script>


<script>
    layui.use(['layer', 'form', 'jquery'], function(){
        var form = layui.form;
        form.on('select(lotteryType)', function(data){
            clearTable();
            init("${chartType}");
            return false;
        });
    });
</script>

</body>

</html>
