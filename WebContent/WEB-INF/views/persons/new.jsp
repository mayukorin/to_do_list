<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
        <h2>アカウント 新規登録ページ</h2>

        <form method="POST" action="<c:url value='/persons/create'/>">
            <c:import url="_form.jsp"/>
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit">登録</button>
        </form>


        <p><a href="<c:url value='/login'/>">ログイン画面に戻る</a></p>
    </c:param>
</c:import>