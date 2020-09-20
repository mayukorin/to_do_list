<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>task 新規登録ページ</h2>

        <form method="POST" action="<c:url value='/tasks/create'/>">
            <c:import url="_form.jsp"/>
        </form>

        <p><a href="<c:url value='/toppage/index'/>">ホーム画面に戻る</a></p>
        <c:if test="${sessionScope.group!= null}">
            <p><a href="<c:url value='/groups/toppage'/>">${sessionScope.group.name}のメンバーのtask一覧に戻る</a>
        </c:if>
    </c:param>
</c:import>