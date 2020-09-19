<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table id = "task_list">
            <tbody>
                <c:forEach var="task" items = "${tasks}">
                    <tr>
                     <td><c:out value="${task.title}"/></td>
                     <td><fmt:formatDate value="${task.deadline}" pattern="yyyy/MM/dd HH:mm" /></td>
                     <td><a href="<c:url value='/tasks/show?id=${task.id}'/>">詳細</a></td>
                    </tr>
                </c:forEach>
            </tbody>
</table>