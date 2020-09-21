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
        <c:import url="../layout/tasksIndex.jsp" />
        <p><a href="<c:url value='/persons/show?id=${sessionScope.login_person.id}'/>">アカウント情報詳細を見る</a></p>
        <p><a href="<c:url value='/tasks/new?id=${sessionScope.login_person.id}'/>">Taskを新規追加する</a></p>
        <div>
            <c:if test="${groups != null}">
                <c:forEach var="group" items="${groups}">
                    <p><a href="<c:url value='/groups/toppage?id=${group.id}'/>">${group.name}</a></p>
                </c:forEach>
            </c:if>
        </div>
    </c:param>
</c:import>