<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
    <c:if test="${ error != null }">
        <div id="flush_error">
                    <c:out value="${error}"/>
         </div>
    </c:if>
        <table class="account_show">
            <tbody>
                <tr>
                    <th>グループ</th>
                    <td><c:out value="${belong.group.name}" /></td>
                </tr>
                <tr>
                    <th>ポジション</th>
                    <td><c:out value="${belong.position}" /></td>
                </tr>

            </tbody>
        </table>
        <p><a href="<c:url value='/belongs/edit?id=${belong.id}'/>">ポジションを編集する</a></p>
        <p><a href="#" onclick="confirmDestroy();">このグループを退会する</a></p>
        <p><a href="<c:url value='/persons/show'/>">アカウント詳細へ戻る</a></p>

        <form method="POST" action="${pageContext.request.contextPath}/belongs/destroy">
            <input type="hidden" name="_token" value="${_token}" />
        </form>
        <script>
        function confirmDestroy() {
            if(confirm("本当に退会してよろしいですか？")) {
                document.forms[0].submit();
            }
        }
        </script>
    </c:param>
</c:import>