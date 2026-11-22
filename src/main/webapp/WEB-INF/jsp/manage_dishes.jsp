<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../../header.jsp" %>
        <h2>菜品管理</h2>
        <div style="background: #fff; padding: 20px; border-radius: 8px; margin-bottom: 20px;">
            <h3>
                <s:if test="dish != null && dish.id != null">编辑菜品</s:if>
                <s:else>添加新菜品</s:else>
            </h3>
            <form action="save" method="post" enctype="multipart/form-data">
                <input type="hidden" name="dish.id" value="<s:property value='dish.id'/>">
                <div class="form-group">
                    <label>菜名</label>
                    <input type="text" name="dish.name" value="<s:property value='dish.name'/>" class="form-control"
                        required>
                </div>
                <div class="form-group">
                    <label>价格</label>
                    <input type="number" step="0.01" name="dish.price" value="<s:property value='dish.price'/>"
                        class="form-control" required>
                </div>
                <div class="form-group">
                    <label>图片</label>
                    <input type="file" name="image" class="form-control">
                    <s:if test="dish.imageUrl != null">
                        <p style="font-size: 0.8em; color: #666; margin-top: 5px;">
                            当前图片:
                            <s:property value="dish.imageUrl" />
                        </p>
                    </s:if>
                </div>
                <div class="form-group">
                    <label>描述</label>
                    <textarea name="dish.description"
                        class="form-control"><s:property value='dish.description'/></textarea>
                </div>
                <div class="form-group">
                    <label>分类</label>
                    <select name="categoryId" class="form-control">
                        <option value="">-- 请选择分类 --</option>
                        <s:iterator value="categories">
                            <option value="<s:property value='id'/>" <s:if test="dish.category.id == id">selected</s:if>
                                >
                                <s:property value="name" />
                            </option>
                        </s:iterator>
                    </select>
                </div>
                <button type="submit" class="btn">
                    <s:if test="dish != null && dish.id != null">更新菜品</s:if>
                    <s:else>保存菜品</s:else>
                </button>
                <s:if test="dish != null && dish.id != null">
                    <a href="manage" class="btn" style="background: #95a5a6; margin-left: 10px;">取消编辑</a>
                </s:if>
            </form>
        </div>
        <h3>当前菜单</h3>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>名称</th>
                    <th>价格</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="dishes">
                    <tr>
                        <td>
                            <s:property value="id" />
                        </td>
                        <td>
                            <s:property value="name" />
                        </td>
                        <td>
                            <s:property value="price" />
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/dish/edit?id=${id}">编辑</a> |
                            <a href="${pageContext.request.contextPath}/dish/delete?id=${id}" style="color: red;"
                                onclick="return confirm('确认要删除此菜品吗？');">删除</a>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
        <%@ include file="../../footer.jsp" %>