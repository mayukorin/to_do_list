<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
        <c:choose>
            <c:when test="${sessionScope.origin_comment_id == null }">
                <h2>task コメントページ</h2>
            </c:when>
            <c:otherwise>
                <h2>返信作成ページ</h2>
            </c:otherwise>
        </c:choose>
        <c:if test="${errors != null}">
            <div id="flush_error">
                入力内容にエラーがあります。<br/>
                <c:forEach var="error" items="${errors}">
                    <c:out value="${error}" /><br/>
                </c:forEach>
            </div>
        </c:if>
        <form method="POST" action="<c:url value='/comments/create' />">
            <c:import url="_form.jsp" />
        </form>
        <br/><br/>
        <table id = "task_show">
                    <tbody>
                        <tr>
                            <th>登録者</th>
                            <td><c:out value="${task.account.name}"/></td>
                        </tr>
                        <c:if test="${sessionScope.group != null && sessionScope.group.id == task.account.id }">
                            <tr>
                                <th>更新者</th>
                                <td><c:out value="${task.update_person.name }"/></td>
                            </tr>
                            <tr>
                                <th>リーダー</th>
                                <td><c:out value="${task.task_leader.name }"/></td>
                            </tr>
                        </c:if>
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
                     </tbody>
                </table>

                <c:choose>
                    <c:when test="${sessionScope.origin_comment_id == null }">
                        <c:choose>
                            <c:when test="${sessionScope.group == null }">
                                <p><a href="<c:url value='/tasks/persons/show?id=${task.id}'/>">task詳細画面に戻る</a></p>
                            </c:when>
                            <c:when test="${sessionScope.group.id == task.account.id }">
                                <p><a href="<c:url value='/groups/tasks/show?id=${task.id}'/>">task詳細画面に戻る</a></p>
                            </c:when>
                            <c:otherwise>
                                <p><a href="<c:url value='/members/tasks/show?id=${task.id}'/>">task詳細画面に戻る</a></p>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <p><a href="<c:url value='/comments/show?id=${sessionScope.origin_comment_id}'/>">コメント詳細画面に戻る</a></p>
                    </c:otherwise>
                </c:choose>

    </c:param>
</c:import>