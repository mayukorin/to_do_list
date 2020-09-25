<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>task一覧</h2>
        <table id = "task_list">
            <tbody>
                <c:forEach var="tl" items = "${task_like}">
                    <tr data-href="<c:url value='/tasks/persons/show?id=${tl.key.id}'/>">
                        <td class="icon"><i class="fas fa-circle"></i></td>
                         <td class="title"><c:out value="${tl.key.title}"/></td>
                         <td><fmt:formatDate value="${tl.key.deadline}" pattern="yyyy/MM/dd HH:mm" /></td>
                         <c:choose>
                            <c:when test="${tl.value == 0 }">
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
        <p><a href="<c:url value='/tasks/persons/new?id=${sessionScope.login_person.id}'/>">Taskを新規追加する</a></p>

    </c:param>
</c:import>