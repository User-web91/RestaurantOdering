<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>
        <div
            style="max-width: 400px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
            <h2 style="text-align: center; margin-bottom: 20px;">用户注册</h2>

            <s:if test="hasActionErrors()">
                <div class="alert alert-danger"
                    style="color: red; margin-bottom: 15px; padding: 10px; background-color: #fce8e6; border: 1px solid #ecccd1; border-radius: 4px;">
                    <s:actionerror />
                </div>
            </s:if>

            <form action="${pageContext.request.contextPath}/user/register" method="post">
                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" name="user.username" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input type="password" name="user.password" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>电话</label>
                    <input type="text" name="user.phone" class="form-control">
                </div>
                <!-- For public registration, role is always customer -->
                <input type="hidden" name="user.role" value="customer">
                <button type="submit" class="btn" style="width: 100%;">注册</button>
            </form>
        </div>
        <%@ include file="../../footer.jsp" %>