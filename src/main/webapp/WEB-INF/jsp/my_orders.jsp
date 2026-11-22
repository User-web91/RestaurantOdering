<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <h2>我的订单</h2>

        <table>
            <thead>
                <tr>
                    <th>订单号</th>
                    <th>日期</th>
                    <th>金额</th>
                    <th>状态</th>
                    <th>订单详情</th>
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
                            <s:property value="createTime" />
                        </td>
                        <td>¥
                            <s:property value="totalAmount" />
                        </td>
                        <td>
                            <span class="status-<s:property value=" status.toLowerCase()" />">
                            <s:property value="status" />
                            </span>
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
                            <s:if test="status == 'CREATED'">
                                <a href="toPay?orderId=<s:property value=" id" />" class="btn">立即支付</a>
                            </s:if>
                            <s:if test="status == 'PAID'">
                                <a href="requestRefund?orderId=<s:property value=" id" />" style="color: red;">申请退款</a>
                            </s:if>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>

        <%@ include file="../../footer.jsp" %>