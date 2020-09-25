<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
    <h2>いいねした人一覧</h2>
    <c:choose>
        <c:when test="${l_p_g != null }">
            <table id="like_show">
            <tr>
                <th>名前</th>
                <th>時間</th>
                <th>所属グループ</th>
            </tr>
            <tr>
              <c:forEach var="lpg" items="${l_p_g}">
                  <tr>
                      <td><c:out value="${lpg.key.person.name}"/></td>
                      <td><fmt:formatDate value="${lpg.key.created_at}" pattern="yyyy/MM/dd HH:mm" /></td>
                      <td>
                        <c:forEach var="pg" items="${lpg.value }">
                            <c:choose>
                                <c:when test="${pg.value == 0 }">
                                    <c:out value="${pg.key.name}"/><br/>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/members/toppage?account=${lpg.key.person.id}&group=${pg.key.id}'/>"><c:out value="${pg.key.name}"/></a><br/>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                      </td>
                  </tr>
              </c:forEach>
            </table>

        </c:when>
        <c:otherwise>
        <p>いいねをした人はまだいません</p>

        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${sessionScope.group == null }">
            <p><a href="<c:url value='/tasks/persons/show?id=${liked_task.id}'/>">task詳細画面に戻る</a></p>
        </c:when>
        <c:when test="${sessionScope.account == null }">
            <c:choose>
                <c:when test="${sessionScope.account.id == liked_task.account.id }">
                    <p><a href="<c:url value='/tasks/persons/show?id=${liked_task.id}'/>">task詳細画面に戻る</a></p>
                </c:when>
                <c:otherwise>
                    <p><a href="<c:url value='/members/tasks/show?id=${liked_task.id}'/>">task詳細画面に戻る</a></p>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <p><a href="<c:url value='/members/tasks/show?id=${liked_task.id}'/>">task詳細画面に戻る</a></p>
        </c:otherwise>
    </c:choose>


    </c:param>
</c:import>