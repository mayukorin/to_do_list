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
    <h2>${sessionScope.account.name}のtask一覧</h2>
    <c:import url="../layout/tasksIndex.jsp" />
    <p><a href="<c:url value='/members/show?id=${sessionScope.account.id}'/>">アカウント情報詳細を見る</a></p>
    <p><a href="<c:url value='/groups/show'/>">${sessionScope.group.name}のメンバー一覧ページに戻る</a>
    </c:param>
</c:import>