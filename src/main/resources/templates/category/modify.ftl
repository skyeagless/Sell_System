<html>
<head>
    <meta charset="utf-8">
    <title>新增修改类目</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/sell/css/style.css">
</head>
<body>
    <div id="wrapper" class="toggled">
        <#include "../common/nav.ftl">
        <div id="page-content-wrapper">
            <div class="container">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <form role="form" method="post" action="/sell/seller/category/save">
                            <div class="form-group">
                                <label>名字</label>
                                <input name="categoryName" type="text" class="form-control"
                                       value="${(productCategory.categoryName)!''}"/>
                            </div>

                            <div class="form-group">
                                <label>Type</label>
                                <input name="categoryType" type="number" class="form-control"
                                       value="${(productCategory.categoryType)!''}"/>
                            </div>

                            <#--                            隐藏的categoryId字段-->
                            <input hidden type="number" name="categoryId" value="${(productCategory.categoryId)!''}">

                            </br>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>










