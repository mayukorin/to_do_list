<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">

        <h2>task コメントページ</h2>
        <c:if test="${errors != null}">
        <div id="flush_error">
        入力内容にエラーがあります。<br/>
        <c:forEach var="error" items="${errors}">
            <c:out value="${error}" /><br/>
        </c:forEach>
        </div>
        </c:if>
        <form method="POST" action="<c:url value='/comments/update' />">
            <c:import url="_form.jsp" />
        </form>
        <br/><br/>
        <c:choose>
            <c:when test="${comment.for_comment == null }">
                <p><a href="<c:url value='/comments/show?id=${comment.id}'/>">コメント詳細画面に戻る</a></p>
            </c:when>
            <c:otherwise>
                <p><a href="<c:url value='/comments/show?id=${comment.for_comment.id}'/>">コメント詳細画面に戻る</a></p>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>