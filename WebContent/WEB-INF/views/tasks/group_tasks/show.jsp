<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../../layout/app.jsp">
    <c:param name="content">
    <c:import url="../_show.jsp" />
    <p><a href="<c:url value='/tasks/edit?id=${task.id}'/>">このtaskを編集する</a></p>
    <p><a href="<c:url value="/groups/toppage"/>">${sessionScope.group.name}のメンバーのtask一覧画面に戻る</a></p>
    <c:if test="${sessionScope.account != null }">
        <p><a href="<c:url value="/group/toppage"/>">${sessionScope.account.name}のtask一覧画面に戻る</a></p>
    </c:if>
    </c:param>

</c:import>