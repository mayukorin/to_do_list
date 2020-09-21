<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
                <h2>アカウント情報 編集ページ</h2>
                <p>（パスワードは変更する場合のみ入力してください）</p>
                <form method="POST" action="<c:url value='/persons/update' />">
                    <c:import url="_form.jsp" />

                    <input type="hidden" name="_token" value="${_token}"/>
                    <button type="submit">投稿</button>
                </form>
        <p><a href="<c:url value='/persons/show?id=${sessionScope.account.id}' />">アカウント情報詳細に戻る</a></p>

    </c:param>
</c:import>