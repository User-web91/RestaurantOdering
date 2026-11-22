<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>

        <div
            style="max-width: 400px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
            <h2 style="text-align: center; margin-bottom: 20px;">用户登录</h2>

            <s:if test="hasActionErrors()">
                <div style="color: red; margin-bottom: 10px;">
                    <s:actionerror />
                </div>
            </s:if>

            <form action="${pageContext.request.contextPath}/user/login" method="post">
                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" name="username" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn" style="width: 100%;">登录</button>
            </form>
            <p style="text-align: center; margin-top: 15px;">
                还没有账号? <a href="${pageContext.request.contextPath}/user/register">去注册</a>
            </p>
        </div>

        <%@ include file="../../footer.jsp" %>