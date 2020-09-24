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
                <c:forEach var="task" items = "${tasks}">
                    <tr>
                     <td><c:out value="${task.title}"/></td>
                     <td><fmt:formatDate value="${task.deadline}" pattern="yyyy/MM/dd HH:mm" /></td>
                     <td><a href="<c:url value='/tasks/persons/show?id=${task.id}'/>">詳細</a></td>
                    </tr>
                </c:forEach>
            </tbody>

        </table>
        <p><a href="<c:url value='/persons/show?id=${sessionScope.login_person.id}'/>">アカウント情報詳細を見る</a></p>
        <p><a href="<c:url value='/tasks/persons/new?id=${sessionScope.login_person.id}'/>">Taskを新規追加する</a></p>
    </c:param>
</c:import>