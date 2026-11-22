<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <h2>购物车</h2>

        <s:if test="#session.cart == null || #session.cart.isEmpty()">
            <p>您的购物车是空的。</p>
            <a href="${pageContext.request.contextPath}/dish/list" class="btn">去点餐</a>
        </s:if>
        <s:else>
            <form action="placeOrder" method="post">
                <table>
                    <thead>
                        <tr>
                            <th>菜品名称</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>小计</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="cartItems">
                            <tr>
                                <td>
                                    <s:property value="dish.name" />
                                </td>
                                <td>¥
                                    <s:property value="dish.price" />
                                </td>
                                <td>
                                    <s:property value="quantity" />
                                </td>
                                <td>¥
                                    <s:property value="subtotal" />
                                </td>
                            </tr>
                        </s:iterator>
                        <tr>
                            <td colspan="3" style="text-align: right; font-weight: bold;">总计:</td>
                            <td style="font-weight: bold; color: green; font-size: 1.2em;">¥
                                <s:property value="cartTotal" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                <div style="margin-top: 20px; text-align: right;">
                    <label>桌号:</label>
                    <input type="number" name="tableNum" required style="width: 60px; padding: 5px;">
                    <button type="submit" class="btn" style="margin-left: 20px;">提交订单</button>
                </div>
            </form>
        </s:else>
        <%@ include file="../../footer.jsp" %>