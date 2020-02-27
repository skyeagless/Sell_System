<html>
<head>
    <meta charset="utf-8">
    <title>订单详情</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/sell/css/style.css" media="all">
</head>
<body>
    <div id="wrapper" class="toggled">
        <#include "../common/nav.ftl">
        <div id="page-content-wrapper">
            <div class="container">
                <div class="row clearfix">
                    <#--    订单总表-->
                    <div class="col-md-4 column">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>订单ID</th>
                                <th>订单总金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${orderDTO.orderId}</td>
                                <td>${orderDTO.orderAmount}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <#--    购物车-->
                    <div class="col-md-12 column">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>商品ID</th>
                                <th>商品名称</th>
                                <th>商品单价</th>
                                <th>商品数量</th>
                                <th>商品总额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDTO.orderDetailList as orderDetail>
                                <tr>
                                    <td>${orderDetail.productId}</td>
                                    <td>${orderDetail.productName}</td>
                                    <td>${orderDetail.productPrice}</td>
                                    <td>${orderDetail.productQuantity}</td>
                                    <td>${orderDetail.productPrice*orderDetail.productQuantity}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>

                    <#--完结/取消订单按钮-->
                    <div class="col-md-12 column">
                        <#if orderDTO.getOrderStatus() == 0>
                            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-success">完结确认</a>
                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                        </#if>
                    </div>

                </div>
            </div>
        </div>
    </div>

</body>
</html>










