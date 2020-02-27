<html>
    <head>
        <meta charset="utf-8">
        <title>卖家商品列表</title>
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
                                    <th>
                                        订单ID
                                    </th>
                                    <th>
                                        姓名
                                    </th>
                                    <th>
                                        手机号
                                    </th>
                                    <th>
                                        地址
                                    </th>
                                    <th>
                                        金额
                                    </th>
                                    <th>
                                        订单状态
                                    </th>
                                    <th>
                                        支付状态
                                    </th>
                                    <th>
                                        创建时间
                                    </th>
                                    <th colspan="2">
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list orderDTOPage.content as orderDTO>
                                    <tr>
                                        <td>${orderDTO.orderId}</td>
                                        <td>${orderDTO.buyerName}</td>
                                        <td>${orderDTO.buyerPhone}</td>
                                        <td>${orderDTO.buyerAddress}</td>
                                        <td>${orderDTO.orderAmount}</td>
                                        <td>${orderDTO.getOrderStatusEnum()}</td>
                                        <td>${orderDTO.getPayStatusEnum()}</td>
                                        <td>${orderDTO.createTime}</td>
                                        <td>
                                            <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                                        </td>
                                        <td>
                                            <#if orderDTO.getOrderStatus() == 0>
                                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                        <#--分页-->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                                <#if currentPage lte 1>
                                    <li class="disabled"><a href="#">Prev</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">prev</a></li>
                                </#if>

                                <#list 1..orderDTOPage.getTotalPages() as index>
                                    <#if currentPage == index>
                                        <li class="disabled"> <a href="#"> ${index}</a></li>
                                    <#else>
                                        <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                    </#if>
                                </#list>

                                <#if currentPage == orderDTOPage.getTotalPages()>
                                    <li class="disabled"><a href="#">Next</a></li>
                                <#else>
                                    <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">Next</a></li>
                                </#if>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>










