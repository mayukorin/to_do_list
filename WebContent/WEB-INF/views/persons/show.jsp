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
                    <td><c:out value="${account.name}"/></td>
                </tr>
                <tr>
                    <th>アカウント番号</th>
                    <td><c:out value="${account.code}"/></td>
                </tr>
                <c:if test="${groups != null }">
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
                 </c:if>
             </tbody>
        </table>

        <c:if test="${persons != null }">
        <table id="person_list">
        <tbody>
            <tr>
                <th>アカウント番号</th>
                <th>名前</th>

            </tr>
            <c:forEach var="person" items = "${persons}">
            <tr>
                <td><c:out value="${person.code}"/></td>
                <td><c:out value="${person.name}"/></td>
            </tr>
            </c:forEach>
        </tbody>

        </table>

    </c:if>


        <c:if test="${leader_flag == 1|| account.id == sessionScope.login_person.id}">
            <p><a href="<c:url value='/persons/edit?id=${account.id}'/>">アカウント情報を編集する</a></p>
        </c:if>
        <c:if test="${account.id == sessionScope.login_person.id}">
            <p><a href="<c:url value='/belongs/new'/>">所属グループを追加する</a>
        </c:if>
        <c:choose>
        <c:when test="${sessionScope.group_id == null}">
            <p><a href="<c:url value='/toppage/index'/>">トップページに戻る</a></p>
        </c:when>
        <c:otherwise>
            <p><a href="<c:url value='/groups/toppage?id=${account.id}'/>">${account.name}のtask一覧に戻る</a></p>
        </c:otherwise>
        </c:choose>
    </c:param>
</c:import>