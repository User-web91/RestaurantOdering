<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <h1>今日菜单</h1>

        <div style="margin-bottom: 20px;">
            <div style="margin-bottom: 10px;">
                <strong>分类:</strong>
                <a href="list" class="btn btn-sm <s:if test='categoryId == null'>btn-primary</s:if>"
                    style="margin-right: 5px;">全部</a>
                <s:iterator value="categories">
                    <a href="list?categoryId=<s:property value='id'/>" class="btn btn-sm"
                        style="margin-right: 5px; <s:if test='categoryId == id'>background-color: #ddd;</s:if>">
                        <s:property value="name" />
                    </a>
                </s:iterator>
            </div>

            <form action="list" method="get" style="display: flex; gap: 10px;">
                <input type="text" name="keyword" placeholder="搜索菜品..." class="form-control" style="width: 300px;">
                <button type="submit" class="btn btn-secondary">搜索</button>
            </form>
        </div>

        <div class="dish-grid">
            <s:iterator value="dishes">
                <div class="dish-card">
                    <!-- Display image if available, else placeholder -->
                    <s:if test="imageUrl != null && !imageUrl.isEmpty()">
                        <img src="${pageContext.request.contextPath}/<s:property value='imageUrl'/>" class="dish-image"
                            alt="<s:property value=" name" />">
                    </s:if>
                    <s:else>
                        <img src="https://via.placeholder.com/300x150?text=No+Image" class="dish-image"
                            alt="<s:property value=" name" />">
                    </s:else>
                    <div class="dish-info">
                        <h3>
                            <s:property value="name" />
                        </h3>
                        <p style="color: #666; font-size: 0.9em;">
                            <s:property value="description" />
                        </p>
                        <div
                            style="display: flex; justify-content: space-between; align-items: center; margin-top: 15px;">
                            <span class="dish-price">¥
                                <s:property value="price" />
                            </span>

                            <c:if test="${sessionScope.user.role == 'customer'}">
                                <form action="${pageContext.request.contextPath}/order/addToCart" method="post"
                                    style="display: inline;">
                                    <input type="hidden" name="dishId" value="<s:property value=" id" />">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn">加入购物车</button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </div>
            </s:iterator>
        </div>

        <%@ include file="../../footer.jsp" %>