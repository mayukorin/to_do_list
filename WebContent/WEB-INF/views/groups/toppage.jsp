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
    <h2>${sessionScope.account.name}のtask一覧</h2>
    <table id = "task_list">
            <tbody>
                <c:forEach var="tl" items = "${task_like}">
                     <tr data-href="<c:url value='/groups/tasks/show?id=${tl.key.id}'/>">
                        <td class="icon">
                        <c:choose>
                                <c:when test="${tl.key.finish_flag == 0}">
                                    <i class="fas fa-circle circle${tl.value % 3 }"></i>
                                </c:when>
                                <c:otherwise>
                                    <i class="far fa-check-circle check"></i>
                                </c:otherwise>
                          </c:choose>
                     </td>

                     <td><c:out value="${tl.key.title}"/></td>
                     <td><fmt:formatDate value="${tl.key.deadline}" pattern="yyyy/MM/dd HH:mm" /></td>
                     <c:choose>
                            <c:when test="${tl.value % 2 == 1 }">
                                <td ><a href="<c:url value='/hearts/create?id=${tl.key.id}&flag=0'/>"><i class="far fa-heart"></i></a></td>
                            </c:when>
                            <c:otherwise>
                                <td ><a href="<c:url value='/hearts/destroy?id=${tl.key.id }&flag=0'/>"><i class="fas fa-heart red_heart"></i></a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </tbody>

    </table>
    <p><a href="<c:url value='/group/show?id=${sessionScope.account.id}'/>">アカウント情報詳細を見る</a></p>

    <p><a href="<c:url value='/tasks/groups/new?id=${sessionScope.account.id}'/>">Taskを新規追加する</a></p>

    <p><a href="<c:url value='/groups/show'/>">${sessionScope.group.name}のメンバー一覧ページに戻る</a>
    </c:param>
</c:import>