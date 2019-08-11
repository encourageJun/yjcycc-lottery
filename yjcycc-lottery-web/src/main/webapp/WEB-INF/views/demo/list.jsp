<%--
  Created by IntelliJ IDEA.
  User: Yangjun
  Date: 18/12/24
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>部门列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/layui/css/layui.css"  media="all">
</head>
<body>

<div class="layui-btn-group demoTable">
    <button class="layui-btn" data-type="getCheckData">获取选中行数据</button>
    <button class="layui-btn" data-type="getCheckLength">获取选中数目</button>
    <button class="layui-btn" data-type="isAll">验证是否全选</button>
</div>

<table class="layui-hide" id="test" lay-filter="demo"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="/static/layui/layui.js" charset="utf-8"></script>
<script src="/static/jquery/jquery-1.12.4.js" charset="utf-8"></script>
<script src="/static/util/dictutils.js" charset="utf-8"></script>
<script>
    layui.use('table', function(){
        var table = layui.table;

        table.render({
            elem: '#test'
            ,url:'/department/listdata'
            ,cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,page:true
            ,height: 'full-60'
            ,cols: [[
                {field:'id', title: 'ID', sort: true}
                ,{field:'name', title: '部门名称'}
                ,{field:'parentId', title: '上级部门', sort: true}
                ,{field:'regionId', title: '所属区域', templet: function(res){
                    return IDict.getLabel("RegionType", res.regionId, "");
                }}
                ,{field:'branchType', title: '机构类型', templet: function(res){
                    return IDict.getLabel("BranchType", res.branchType, "");
                }}
                ,{field:'identifier', title: '部门标识', sort: true}
                ,{field:'mainHead', title: '主要负责人', sort: true}
                ,{field:'deputyHead', title: '副负责人'}
                ,{field:'pathCn', title: '路径', sort: true}
                ,{field:'sort', title: '排序', sort: true}
                ,{field:'description', title: '描述', sort: true}
                ,{toolbar:'#barDemo', width:160}
            ]]
        });

        //监听表格复选框选择
        table.on('checkbox(demo)', function(obj){
            console.log(obj)
        });
        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'detail'){
                layer.msg('ID：'+ data.id + ' 的查看操作');
            } else if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    obj.del();
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                layer.alert('编辑行：<br>'+ JSON.stringify(data))
            }
        });

        var $ = layui.$, active = {
            getCheckData: function(){ //获取选中数据
                var checkStatus = table.checkStatus('idTest')
                    ,data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
            ,getCheckLength: function(){ //获取选中数目
                var checkStatus = table.checkStatus('idTest')
                    ,data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
            }
            ,isAll: function(){ //验证是否全选
                var checkStatus = table.checkStatus('idTest');
                layer.msg(checkStatus.isAll ? '全选': '未全选')
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
</script>

</body>
</html>
