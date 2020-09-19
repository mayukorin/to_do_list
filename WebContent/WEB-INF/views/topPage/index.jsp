<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>タスク一覧</h2>
        <table id = "task_list">
            <tbody>
                <c:forEach var="task" items = "${tasks}">
                    <tr>
                     <td><c:out value="${task.title}"/></td>
                     <td><c:out value="${task.deadline}"/></td>
                     <td><a href="<c:url value='/tasks/show?id=${task.id}'/>">詳細</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p><a href="<c:url value='/persons/show'/>">アカウント情報詳細を見る</a></p>
        <p><a href="<c:url value='/tasks/new'/>">Taskを新規追加する</a></p>
    </c:param>
</c:import>