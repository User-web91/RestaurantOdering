<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
    <%@ taglib prefix="s" uri="/struts-tags" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Restaurant System</title>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
            </head>

            <body>
                <header>
                    <div class="navbar">
                        <a href="${pageContext.request.contextPath}/index.jsp" class="logo">和山饭店自助点餐系统</a>
                        <nav class="nav-links">
                            <a href="${pageContext.request.contextPath}/dish/list">菜单</a>

                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <c:if test="${sessionScope.user.role == 'customer'}">
                                        <a href="${pageContext.request.contextPath}/user/profile">个人中心</a>
                                        <a href="${pageContext.request.contextPath}/order/viewCart">购物车</a>
                                        <a href="${pageContext.request.contextPath}/order/myOrders">我的订单</a>
                                    </c:if>
                                    <c:if
                                        test="${sessionScope.user.role == 'waiter' || sessionScope.user.role == 'admin'}">
                                        <a href="${pageContext.request.contextPath}/dish/manage">菜品管理</a>
                                        <a href="${pageContext.request.contextPath}/order/manage">订单管理</a>
                                    </c:if>
                                    <c:if test="${sessionScope.user.role == 'admin'}">
                                        <a href="${pageContext.request.contextPath}/admin/users">用户管理</a>
                                    </c:if>

                                    <span>欢迎, ${sessionScope.user.username} (${sessionScope.user.points}
                                        积分)</span>
                                    <a href="${pageContext.request.contextPath}/user/logout">退出</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/user/login?mode=login">登录</a>
                                    <a href="${pageContext.request.contextPath}/user/register?mode=register">注册</a>
                                </c:otherwise>
                            </c:choose>
                        </nav>
                    </div>
                </header>
                <div class="container">