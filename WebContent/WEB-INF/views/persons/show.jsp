<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>${sessionScope.login_person.name}のアカウント情報 詳細ページ</h2>
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
                <c:if test="${sessionScope.GroupBelong != null }">
                <tr>

                    <th>所属しているグループ</th>
                    <td>
                        <ul>
                            <c:forEach var="group" items = "${sessionScope.GroupBelong}">
                                <li><a href="<c:url value='/belongs/show?id=${group.id }'/>"><c:out value="${group.name}"/></a></li>
                            </c:forEach>
                        </ul>
                    </td>
                 </tr>
                 </c:if>
             </tbody>
        </table>

        <p><a href="<c:url value='/persons/edit?id=${sessionScope.login_person.id}'/>">アカウント情報を編集する</a></p>



        <p><a href="<c:url value='/belongs/new'/>">新しいグループに参加する</a>
        <p><a href="<c:url value='/groups/new'/>">新しくグループを作成する</a>
    </c:param>
</c:import>