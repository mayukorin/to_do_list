<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app2.jsp">
    <c:param name="content2">
        <c:choose>
            <c:when test="${task != null}">
                <h2>task 作成ページ</h2>
                <form method="POST" action="<c:url value='/tasks/persons/create' />">
                    <c:import url="../_form.jsp" />
                </form>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>



        <p><a href="<c:url value='/toppage/index'/>">task一覧に戻る</a></p>

    </c:param>
</c:import>