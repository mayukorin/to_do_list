<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
        <h2>ログイン</h2>
        <form method="POST" action="<c:url value='/login'/>">
             <c:import url="login_form.jsp" />
        </form>
        <p><a href="<c:url value='/person/new'/>">アカウントを作成する</a></p>

    </c:param>
</c:import>