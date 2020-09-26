<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
        <h2>task 新規登録ページ</h2>

        <form method="POST" action="<c:url value='/tasks/create'/>">
            <c:import url="_form.jsp"/>
        </form>


        <c:choose>
        <c:when test="${sessionScope.group!= null}">
            <p><a href="<c:url value='/groups/member'/>">${sessionScope.account.name}のtask一覧に戻る</a>
        </c:when>
        <c:otherwise>
            <p><a href="<c:url value='/toppage/index'/>">task一覧に戻る</a></p>
        </c:otherwise>
        </c:choose>
    </c:param>
</c:import>