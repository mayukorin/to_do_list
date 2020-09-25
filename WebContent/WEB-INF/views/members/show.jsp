<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
    <c:import url="../group_member/_show.jsp" />

    <p><a href="<c:url value='/members/toppage?id=${sessionScope.account.id}'/>">${sessionScope.account.name}のtask一覧に戻る</a></p>
    </c:param>
</c:import>