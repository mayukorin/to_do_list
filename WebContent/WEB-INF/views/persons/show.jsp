<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>${account.name}のアカウント情報 詳細ページ</h2>
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
            <c:choose>
                <c:when test="${account.id == sessionScope.login_person.id}">
                    <p><a href="<c:url value='/persons/edit?id=${account.id}'/>">アカウント情報を編集する</a></p>
                </c:when>
                <c:otherwise>
                    <p><a href="<c:url value='/groups/edit?id=${account.id}'/>">アカウント情報を編集する</a></p>
                </c:otherwise>
            </c:choose>
        </c:if>
        <c:if test="${account.id == sessionScope.login_person.id}">
            <p><a href="<c:url value='/belongs/new'/>">新しいグループに参加する</a>
            <p><a href="<c:url value='/groups/new'/>">新しくグループを作成する</a>
        </c:if>
        <c:choose>
        <c:when test="${sessionScope.group == null}">
            <p><a href="<c:url value='/toppage/index'/>">task一覧に戻る</a></p>
        </c:when>
        <c:otherwise>
            <p><a href="<c:url value='/groups/member?id=${account.id}'/>">${account.name}のtask一覧に戻る</a></p>
        </c:otherwise>
        </c:choose>
    </c:param>
</c:import>