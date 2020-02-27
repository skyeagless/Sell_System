<html>
    <head>
        <meta charset="utf-8">
        <title>卖家类目列表</title>
        <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="/sell/css/style.css" media="all">
    </head>
    <body>
        <div id="wrapper" class="toggled">
    <#--        边栏sidebar-->
         <#include "../common/nav.ftl">
    <#--        主要内容区域-->
            <div id="page-content-wrapper">
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th>类目ID</th>
                                    <th>名称</th>
                                    <th>Type</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list categoryList as category>
                                    <tr>
                                        <td>${category.categoryId}</td>
                                        <td>${category.categoryName}</td>
                                        <td>${category.categoryType}</td>
                                        <td>${category.createTime}</td>
                                        <td>${category.updateTime}</td>
                                        <td><a href="/sell/seller/category/modify?categoryId=${category.categoryId}">修改</a></td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>










