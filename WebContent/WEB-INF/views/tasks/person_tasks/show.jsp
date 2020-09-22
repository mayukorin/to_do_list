<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../../layout/app.jsp">
    <c:param name="content">
    <c:import url="../_show.jsp" />
    <p><a href="<c:url value='/tasks/edit?id=${task.id}'/>">このtaskを編集する</a></p>
    <p><a href="<c:url value="/toppage/index"/>">task一覧画面に戻る</a></p>
    </c:param>

</c:import>