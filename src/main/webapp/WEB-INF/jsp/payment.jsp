<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <div
            style="max-width: 600px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
            <h2 style="text-align: center; margin-bottom: 20px;">订单支付</h2>

            <s:if test="hasActionErrors()">
                <div class="alert alert-danger"
                    style="color: red; margin-bottom: 15px; padding: 10px; background-color: #fce8e6; border: 1px solid #ecccd1; border-radius: 4px;">
                    <s:actionerror />
                </div>
            </s:if>

            <div style="margin-bottom: 20px; padding: 15px; background: #f9f9f9; border-radius: 4px;">
                <p><strong>订单号:</strong>
                    <s:property value="currentOrder.id" />
                </p>
                <p><strong>订单总额:</strong> <span style="font-size: 1.2em; color: green;">¥
                        <s:property value="currentOrder.totalAmount" />
                    </span></p>
            </div>

            <div style="margin-bottom: 20px; padding: 15px; border: 1px solid #eee; border-radius: 4px;">
                <h4>我的账户</h4>
                <p><strong>当前余额:</strong> ¥
                    <s:property value="#session.user.balance" />
                </p>
                <p><strong>当前积分:</strong>
                    <s:property value="#session.user.points" /> (可抵扣: ¥
                    <s:property value="#session.user.points * 0.02" />)
                </p>
            </div>

            <form action="pay" method="post">
                <input type="hidden" name="orderId" value="<s:property value=" currentOrder.id" />">

                <s:if test="#session.user.points > 0">
                    <div class="form-group" style="padding: 10px; background: #e8f5e9; border-radius: 4px;">
                        <label style="display: flex; align-items: center; cursor: pointer;">
                            <input type="checkbox" name="usePoints" value="true"
                                style="margin-right: 10px; width: auto;">
                            使用积分抵扣 (1积分 = ¥0.02)
                        </label>
                    </div>
                </s:if>

                <button type="submit" class="btn" style="width: 100%; margin-top: 20px;">确认支付</button>
                <a href="myOrders" class="btn btn-secondary"
                    style="display: block; text-align: center; margin-top: 10px; background: #95a5a6;">取消</a>
            </form>
        </div>

        <%@ include file="../../footer.jsp" %>