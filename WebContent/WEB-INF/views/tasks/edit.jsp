<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${task != null}">
                <h2>Task 編集ページ</h2>
                <form method="POST" action="<c:url value='/tasks/update' />">
                    <c:import url="_form.jsp" />
                </form>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/toppage/index' />">ホーム画面に戻る</a></p>
        <c:if test="${sessionScope.group!= null}">
            <p><a href="<c:url value='/groups/toppage'/>">${sessionScope.group.name}のメンバーのtask一覧に戻る</a>
        </c:if>
        <c:if test="${task.origin_task == null  && task.update_person.id == sessionScope.login_person.id ||task.origin_task.account.id = sessionScope.login_person.id }">
            <p><a href="#" onclick="confirmDestroy();">このTaskを削除する</a></p>
        </c:if>
        <form method="POST" action="${pageContext.request.contextPath}/destroy">
            <input type="hidden" name="_token" value="${_token}" />
        </form>
        <script>
        function confirmDestroy() {
            if(confirm("本当に削除してよろしいですか？")) {
                document.forms[1].submit();
            }
        }
        </script>
    </c:param>
</c:import>