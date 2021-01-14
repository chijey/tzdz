<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>升高</th>
                            <th>体重</th>
                            <th>图片</th>
                            <th>爱好</th>
                            <th>工作</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list personInfoPage.content as personInfo>
                        <tr>
                            <td>${personInfo.id}</td>
                            <td>${personInfo.name}</td>
                            <td>${personInfo.sex}</td>
                            <td>${personInfo.height}</td>
                            <td>${personInfo.weight}</td>
                            <td>${personInfo.avatar}</td>
                            <td>${personInfo.hobby}</td>
                            <td>${personInfo.job}</td>
                            <td>${personInfo.createTime}</td>
                            <td>${personInfo.updTime}</td>
<#--                            <td><img height="100" width="100" src="${personInfo.productIcon}" alt=""></td>-->
<#--                            <td>${personInfo.productPrice}</td>-->
<#--                            <td>${personInfo.productStock}</td>-->
<#--                            <td>${personInfo.productDescription}</td>-->
<#--                            <td>${personInfo.categoryType}</td>-->
<#--                            <td>${personInfo.createTime}</td>-->
<#--                            <td>${personInfo.updateTime}</td>-->
<#--                            <td><a href="/sell/seller/person/index?productId=${personInfo.productId}">修改</a></td>-->
<#--                            <td>-->
<#--                                <#if productInfo.getProductStatusEnum().message == "在架">-->
<#--                                    <a href="/sell/seller/person/off_sale?productId=${productInfo.productId}">下架</a>-->
<#--                                <#else>-->
<#--                                    <a href="/sell/seller/person/on_sale?productId=${productInfo.productId}">上架</a>-->
<#--                                </#if>-->
<#--                            </td>-->
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..personInfoPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte personInfoPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
