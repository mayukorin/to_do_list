<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
    <c:import url="../group_member/_show.jsp" />

    <c:if test="${sessionScope.account.leader.id == sessionScope.login_person.id }">
         <p><a href="<c:url value='/group/edit?id=${sessionScope.account.id}'/>">アカウント情報を編集する</a></p>
    </c:if>
    <p><a href="<c:url value='/group/toppage?id=${sessionScope.account.id}'/>">${sessionScope.account.name}のtask一覧に戻る</a></p>
    </c:param>
</c:import>