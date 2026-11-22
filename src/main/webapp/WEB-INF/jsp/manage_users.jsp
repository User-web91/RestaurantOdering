<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>
        <h2>用户管理 (管理员)</h2>
        <div style="background: #fff; padding: 20px; border-radius: 8px; margin-bottom: 20px;">
            <h3>添加新用户</h3>
            <form action="addUser" method="post"
                style="display: flex; gap: 10px; align-items: flex-end; flex-wrap: wrap;">
                <div class="form-group" style="margin-bottom: 0;">
                    <label>用户名</label>
                    <input type="text" name="user.username" class="form-control" required>
                </div>
                <div class="form-group" style="margin-bottom: 0;">
                    <label>密码</label>
                    <input type="text" name="user.password" class="form-control" required>
                </div>
                <div class="form-group" style="margin-bottom: 0;">
                    <label>电话</label>
                    <input type="text" name="user.phone" class="form-control">
                </div>
                <div class="form-group" style="margin-bottom: 0;">
                    <label>角色</label>
                    <select name="user.role" class="form-control">
                        <option value="customer">顾客</option>
                        <option value="waiter">服务员</option>
                        <option value="admin">管理员</option>
                    </select>
                </div>
                <button type="submit" class="btn">添加用户</button>
            </form>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>用户名</th>
                    <th>角色</th>
                    <th>电话</th>
                    <th>积分</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="users">
                    <tr>
                        <td>
                            <s:property value="id" />
                        </td>
                        <td>
                            <s:property value="username" />
                        </td>
                        <td>
                            <s:property value="role" />
                        </td>
                        <td>
                            <s:property value="phone" />
                        </td>
                        <td>
                            <s:property value="points" />
                        </td>
                        <td>
                            <s:if test="id != #session.user.id">
                                <a href="${pageContext.request.contextPath}/admin/deleteUser?id=${id}"
                                    style="color: red;" onclick="return confirm('确认要删除此用户吗？');">删除</a>
                            </s:if>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <%@ include file="../../footer.jsp" %>