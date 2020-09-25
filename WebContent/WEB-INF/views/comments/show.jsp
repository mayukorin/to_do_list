<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
    <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
    <h2>コメント詳細ページ</h2>
    <table id="comment_table">
                    <tr>
                        <th>名前</th>
                        <th>日時</th>
                        <th>コメント内容</th>
                        <th>操作</th>

                    </tr>

                     <tr>
                         <td><c:out value="${origin_comment.comment_person.name}"/></td>
                         <td><fmt:formatDate value="${origin_comment.updated_at }" pattern="yyyy-MM-dd HH:mm" /></td>
                         <td><pre><c:out value="${origin_comment.content}"/></pre></td>
                         <td>
                            <c:if test="${origin_comment.comment_person.id == sessionScope.login_person.id }">
                                <a href="<c:url value='/comments/edit?id=${origin_comment.id}'/>">編集する</a>
                            </c:if>
                         </td>
                     </tr>

                </table>
                <br/><br/>
      <h2>返信一覧</h2>
      <table id="comment_table">
                    <tr>
                        <th>名前</th>
                        <th>日時</th>
                        <th>コメント内容</th>
                        <th>操作</th>

                    </tr>
                    <c:forEach var="comment" items="${return_comments}">
                        <tr>
                            <td><c:out value="${comment.comment_person.name}"/></td>
                            <td><fmt:formatDate value="${origin_comment.updated_at }" pattern="yyyy-MM-dd HH:mm" /></td>
                            <td><pre><c:out value="${comment.content}"/></pre></td>
                            <td>
                                <c:if test="${origin_comment.comment_person.id == sessionScope.login_person.id }">
                                    <a href="<c:url value='/comments/edit?id=${comment.id}'/>">編集する</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <c:choose>
        <c:when test="${sessionScope.group == null }">
            <p><a href="<c:url value='/tasks/persons/show?id=${origin_comment.for_task.id}'/>">task詳細画面に戻る</a></p>
        </c:when>
        <c:when test="${sessionScope.account == null }">
            <c:choose>
                <c:when test="${sessionScope.account.id == origin_comment.for_task.account.id }">
                    <p><a href="<c:url value='/tasks/persons/show?id=${origin_comment.for_task.id}'/>">task詳細画面に戻る</a></p>
                </c:when>
                <c:otherwise>
                    <p><a href="<c:url value='/members/tasks/show?id=${origin_comment.for_task.id}'/>">task詳細画面に戻る</a></p>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <p><a href="<c:url value='/members/tasks/show?id=${origin_comment.for_task.id}'/>">task詳細画面に戻る</a></p>
        </c:otherwise>
    </c:choose>
    </c:param>
</c:import>