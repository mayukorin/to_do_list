<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <c:choose>
            <c:when test="${task_like!= null}">
                <h2>task 詳細ページ</h2>
                <c:forEach var="tl" items="${task_like}">
                <table id = "task_show">
                    <tbody>
                        <tr>
                        <th>登録者</th>
                        <td><c:out value="${tl.key.account.name}"/></td>
                        </tr>
                        <c:if test="${sessionScope.group != null && sessionScope.group.id == tl.key.account.id }">
                            <tr>
                            <th>更新者</th>
                            <td><c:out value="${tl.key.update_person.name }"/></td>
                            </tr>
                            <tr>
                            <th>リーダー</th>
                            <td><c:out value="${tl.key.task_leader.name }"/></td>
                            </tr>
                        </c:if>
                        <tr>
                        <th>title</th>
                        <td><c:out value="${tl.key.title}"/></td>
                        </tr>
                        <tr>
                        <th>締め切り</th>
                        <td><fmt:formatDate value="${tl.key.deadline}" pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                        <th>内容</th>
                        <td>
                            <pre><c:out value="${tl.key.memo}"/></pre>
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
                <c:choose>
                <c:when test="${tl.value == 0 }">
                <p><a href="<c:url value='/hearts/create?id=${tl.key.id}&flag=1'/>"><i class="far fa-heart"></i></a></p>
                </c:when>
                <c:otherwise>
                <p><a href="<c:url value='/hearts/destroy?id=${tl.key.id }&flag=1'/>"><i class="fas fa-heart red_heart"></i></a></p>
                </c:otherwise>
                </c:choose>
                <c:if test="${tasks_history != null }">
                あいう
                </c:if>
                <p><a href="<c:url value='/hearts/index?id=${tl.key.id}'/>">いいねした人一覧を見る</a></p>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
