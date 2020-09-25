<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
            </c:if>
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
                <p>
                <c:choose>
                <c:when test="${tl.value == 0 }">
                <a href="<c:url value='/hearts/create?id=${tl.key.id}&flag=1'/>"><i class="far fa-heart"></i></a>
                </c:when>
                <c:otherwise>
                <a href="<c:url value='/hearts/destroy?id=${tl.key.id }&flag=1'/>"><i class="fas fa-heart red_heart"></i></a>
                </c:otherwise>
                </c:choose>
                <a href="<c:url value='/comments/new?task_id=${tl.key.id}'/>"><i class="far fa-comment comment"></i></a>
                </p>
                <c:if test="${tasks_history != null }">
                あいう
                </c:if>
                <p><a href="<c:url value='/hearts/index?id=${tl.key.id}'/>">いいねした人一覧を見る</a></p>
                <h2>コメント</h2>
                <table id="comment_table">
                    <tr>
                        <th>名前</th>
                        <th>日時</th>
                        <th>コメント内容</th>
                        <th>操作</th>

                    </tr>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td><c:out value="${comment.comment_person.name}"/></td>
                            <td><fmt:formatDate value="${tl.key.deadline}" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td><pre><c:out value="${comment.content}"/></pre></td>
                            <td><a href="<c:url value='/comments/show'/>">返信を見る</a></td>
                        </tr>
                    </c:forEach>
                </table>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
