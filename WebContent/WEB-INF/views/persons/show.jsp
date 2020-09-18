<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>アカウント情報 詳細ページ</h2>
        <table>
            <tbody>
                <tr>
                    <th>名前</th>
                    <td><c:out value="${sessionScope.login_person.name}"/></td>
                </tr>
                <tr>
                    <th>アカウント番号</th>
                    <td><c:out value="${sessionScope.login_person.code}"/></td>
                </tr>
                <tr>
                    <th>所属しているグループ</th>
                    <td>
                        <ul>
                            <c:forEach var="group" items = "${groups}">
                                <li><c:out value="${group.name}"></c:out></li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </tbody>
        </table>
        <p><a href="<c:url value='/persons/edit'/>">アカウント情報を編集する</a></p>
        <p><a href="<c:url value='/belongs/new'/>">所属グループを追加する</a>
        <p><a href="<c:url value='/index.html'/>">トップページに戻る</a></p>
    </c:param>
</c:import>