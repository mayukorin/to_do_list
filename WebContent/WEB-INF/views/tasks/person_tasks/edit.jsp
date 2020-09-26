<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
<c:when test="${sessionScope.group == null }">
<c:import url="/WEB-INF/views/layout/app2.jsp">
    <c:param name="content2">
        <c:choose>
            <c:when test="${task != null}">
                <h2>task 編集ページ</h2>

                <form method="POST" action="<c:url value='/tasks/persons/update' />">
                    <c:import url="../_form.jsp" />
                </form>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${sessionScope.group == null }">
                <p><a href="<c:url value='/toppage/index'/>">task一覧に戻る</a></p>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${sessionScope.account == null }">
                        <p><a href="<c:url value='/groups/toppage'/>">${sessionScope.group.name}のメンバーのtask一覧に戻る</a></p>
                    </c:when>
                    <c:otherwise>
                        <p><a href="<c:url value='/members/toppage'/>">${sessionScope.account.name}のtask一覧に戻る</a></p>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>

        <p><a href="#" onclick="confirmDestroy();">このtaskを削除する</a></p>

        <form method="POST" action="${pageContext.request.contextPath}/tasks/persons/destroy">
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
</c:when>
<c:otherwise>
<c:import url="/WEB-INF/views/layout/app2.jsp">
    <c:param name="content2">
        <c:choose>
            <c:when test="${task != null}">
                <h2>task 編集ページ</h2>

                <form method="POST" action="<c:url value='/tasks/persons/update' />">
                    <c:import url="../_form.jsp" />
                </form>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${sessionScope.group == null }">
                <p><a href="<c:url value='/toppage/index'/>">task一覧に戻る</a></p>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${sessionScope.account == null }">
                        <p><a href="<c:url value='/groups/toppage'/>">${sessionScope.group.name}のメンバーのtask一覧に戻る</a></p>
                    </c:when>
                    <c:otherwise>
                        <p><a href="<c:url value='/members/toppage'/>">${sessionScope.account.name}のtask一覧に戻る</a></p>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>

        <p><a href="#" onclick="confirmDestroy();">このtaskを削除する</a></p>

        <form method="POST" action="${pageContext.request.contextPath}/tasks/persons/destroy">
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
</c:otherwise>
</c:choose>