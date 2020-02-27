<html>
<head>
    <meta charset="utf-8">
    <title>新增修改商品</title>
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
                        <form role="form" method="post" action="/sell/seller/product/save">
                            <div class="form-group">
                                <label>商品名称</label>
                                <input name="productName" type="text" class="form-control"
                                value="${(productInfo.productName)!''}"/>
                            </div>
                            <div class="form-group">
                                <label>价格</label>
                                <input name="productPrice" type="text" class="form-control"
                                       value="${(productInfo.productPrice)!''}"/>
                            </div>
                            <div class="form-group">
                                <label>库存</label>
                                <input name="productStock" type="number" class="form-control"
                                       value="${(productInfo.productStock)!''}"/>
                            </div>
                            <div class="form-group">
                                <label>描述</label>
                                <input name="productDescription" type="text" class="form-control"
                                       value="${(productInfo.productDescription)!''}"/>
                            </div>
                            <div class="form-group">
                                <label>图片</label>
                                <img height="100" width="100" src="${(productInfo.productIcon)!''}">
                                <input name="productIcon" type="text" class="form-control"
                                       value="${(productInfo.productIcon)!''}"/>
                            </div>
                            <div class="form-group">
                                <label>类目</label>
                                <select name="categoryType" class="form-control">
                                    <#list productCategoryList as category>
                                        <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? &&
                                            productInfo.categoryType == category.categoryType>
                                                selected
                                            </#if>
                                        >
                                            ${category.categoryName}
                                        </option>
                                    </#list>
                                </select>
                            </div>
<#--                            隐藏的productId字段-->
                            <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>










