<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app2.jsp">
    <c:param name="content2">
                <h2>グループ 新規作成ページ</h2>
                <form method="POST" action="<c:url value='/groups/create' />">
                    <c:import url="../groups/_form.jsp" />

                    <input type="hidden" name="_token" value="${_token}"/>
                    <button type="submit">投稿</button>
                    <br/><br/>
                </form>
        <p><a href="<c:url value='/persons/show?id=${sessionScope.login_person.id}' />">アカウント情報詳細に戻る</a></p>

    </c:param>
</c:import>