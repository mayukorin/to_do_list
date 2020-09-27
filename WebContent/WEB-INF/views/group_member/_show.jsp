<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <h2>${sessionScope.account.name}のアカウント情報 詳細ページ</h2>
        <table class="account_show">
            <tbody>
                <tr>
                    <th>名前</th>
                    <td><c:out value="${sessionScope.account.name}"/></td>
                </tr>
                <tr>
                    <th>アカウント番号</th>
                    <td><c:out value="${sessionScope.account.code}"/></td>
                </tr>
                <c:choose>
                    <c:when test="${sessionScope.account.id != sessionScope.group.id }">
                        <tr>
                            <th>ポジション</th>
                            <td><c:out value="${b.position}"/></td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                         <tr>
                              <th>リーダー</th>
                              <td><c:out value="${sessionScope.account.leader.name}"/></td>
                         </tr>
                    </c:otherwise>
                </c:choose>
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
        <br/><br/>
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


