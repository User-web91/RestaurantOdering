<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <div
            style="max-width: 600px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
            <h2 style="text-align: center; margin-bottom: 20px;">个人中心</h2>

            <div style="margin-bottom: 30px; border-bottom: 1px solid #eee; padding-bottom: 20px;">
                <h3>我的信息</h3>
                <p><strong>用户ID:</strong>
                    <s:property value="user.id" />
                </p>
                <p><strong>角色:</strong>
                    <s:property value="user.role" />
                </p>
                <p><strong>积分:</strong>
                    <s:property value="user.points" />
                </p>
                <s:if test="user.role == 'customer'">
                    <p><strong>余额:</strong> ¥
                        <s:property value="user.balance" />
                    </p>
                </s:if>
            </div>

            <h3>修改信息</h3>
            <form action="updateProfile" method="post">
                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" name="user.username" value="<s:property value=" user.username" />"
                    class="form-control" required>
                </div>
                <div class="form-group">
                    <label>电话</label>
                    <input type="text" name="user.phone" value="<s:property value=" user.phone" />"
                    class="form-control">
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input type="password" name="user.password" value="<s:property value=" user.password" />"
                    class="form-control" required>
                </div>
                <button type="submit" class="btn">保存修改</button>
            </form>
        </div>

        <%@ include file="../../footer.jsp" %>