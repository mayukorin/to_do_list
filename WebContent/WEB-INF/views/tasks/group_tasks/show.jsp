<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../../layout/app2.jsp">
    <c:param name="content2">
    <c:import url="../_show.jsp" />
    <c:choose>
        <c:when test="${sessionScope.updated_task == null}">
            <p><a href="<c:url value='/tasks/groups/edit?id=${task.id}'/>">このtaskを編集する</a></p>
            <c:choose>
            <c:when test="${sessionScope.account != null }">
                <p><a href="<c:url value="/group/toppage"/>">${sessionScope.account.name}のtask一覧画面に戻る</a></p>
            </c:when>
            <c:otherwise>
                <p><a href="<c:url value="/groups/toppage"/>">${sessionScope.group.name}のメンバーのtask一覧画面に戻る</a>
            </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
                <p><a href="<c:url value="/tasks/groups/history?id=${origin_task.id }"/>">task更新履歴一覧に戻る</a></p>
        </c:otherwise>
    </c:choose>
    </c:param>

</c:import>