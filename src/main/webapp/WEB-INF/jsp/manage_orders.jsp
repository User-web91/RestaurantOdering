<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <h2>订单管理 (服务员)</h2>

        <table>
            <thead>
                <tr>
                    <th>订单ID</th>
                    <th>桌号</th>
                    <th>总金额</th>
                    <th>订单详情</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="orders">
                    <tr>
                        <td>
                            <s:property value="id" />
                        </td>
                        <td>
                            <s:property value="tableNum" />
                        </td>
                        <td>¥
                            <s:property value="totalAmount" />
                        </td>
                        <td>
                            <ul style="font-size: 0.85em; list-style: none; padding-left: 0; color: #555;">
                                <s:iterator value="items">
                                    <li>
                                        <s:property value="dish.name" /> x
                                        <s:property value="quantity" />
                                        (¥
                                        <s:property value="unitPrice" />)
                                    </li>
                                </s:iterator>
                            </ul>
                        </td>
                        <td>
                            <s:property value="status" />
                        </td>
                        <td>
                            <s:property value="createTime" />
                        </td>
                        <td>
                            <s:if test="status == 'PAID'">
                                <a href="confirm?orderId=<s:property value=" id" />" class="btn btn-secondary">确认接单</a>
                            </s:if>
                            <s:elseif test="status == 'CONFIRMED'">
                                <a href="serve?orderId=<s:property value=" id" />" class="btn">上菜</a>
                            </s:elseif>
                            <s:elseif test="status == 'REFUND_REQUESTED'">
                                <a href="confirmRefund?orderId=<s:property value=" id" />" class="btn"
                                style="background-color: orange;">批准退款</a>
                            </s:elseif>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>

        <%@ include file="../../footer.jsp" %>