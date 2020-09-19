<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
    <h2>${group.name}のメンバー一覧</h2>
    <table id="person_list">
        <tbody>
            <tr>
                <th>アカウント番号</th>
                <th>名前</th>
                <th>操作</th>
            </tr>
            <tr>
                <td><c:out value="${group.code}"/></td>
                <td><c:out value="${group.name}"/></td>
                <td><a href="<c:url value='/groups/member?id = ${group.id }'/>">詳細を見る</a></td>
            </tr>
            <c:forEach var="person" items = "${persons}">
                <td><c:out value="${person.code}"/></td>
                <td><c:out value="${person.name}"/></td>
                <td><a href="<c:url value='/groups/member?id=${group.id }'/>">詳細を見る</a></td>
            </c:forEach>
        </tbody>

    </table>
    <p><a href="<c:url value='/groups/toppage'/>">${group.name}のメンバーのTask一覧に戻る</a></p>
    </c:param>

</c:import>