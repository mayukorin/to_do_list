<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${task != null}">
                <h2>Task 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                        <th>登録者</th>
                        <td><c:out value="${task.account.name}"/></td>
                        </tr>
                        <tr>
                        <th>title</th>
                        <td><c:out value="${task.title}"/></td>
                        </tr>
                        <tr>
                        <th>締め切り</th>
                        <td><fmt:formatDate value="${task.deadline}" pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                        <th>内容</th>
                        <td>
                            <pre><c:out value="${task.memo}"/></pre>
                        </td>
                        </tr>
                        <c:if test="${shows_group != null}">
                            <tr>
                            <th>公開しているグループ</th>
                            <td>
                            <ul>
                                <c:forEach var="group" items = "${shows_group}">
                                    <li><c:out value="${group.name}"></c:out></li>
                                </c:forEach>
                            </ul>
                            </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
                <c:if test="${tasks_history != null }">
                あいう
                </c:if>
                <c:if test="${sessionScope.login_person.id == task.account.id||sessionScope.group.id == task.account.id}">
                    <p><a href="<c:url value='/tasks/edit?id=${task.id}'/>">このTaskを編集する</a></p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <c:choose>
        <c:when test="${sessionScope.group == null}">
        <p><a href="<c:url value="/toppage/index"/>">ホーム画面に戻る</a></p>
        </c:when>
        <c:otherwise>
        <p><a href="<c:url value="/groups/toppage"/>">${sessionScope.group.name}のメンバーのtask一覧画面に戻る</a></p>
        </c:otherwise>
        </c:choose>
    </c:param>
</c:import>
