<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>グループ 新規参加ページ</h2>
        <p>（名前は、新規グループを作成する時のみ入力してください。）</p>
        <form method="POST" action="<c:url value='/groups/create' />">
                    <input type="hidden" name="_token" value="${_token}" />
        </form>


        <form method="POST" action="<c:url value='/belongs/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='/toppage/index' />">ホームページに戻る</a></p>
         <c:if test="${new_flag != null }">

            <script>
            groupCreate();
            function groupCreate() {
                            if(confirm("入力したグループは存在していませんでした。新しくグループを作成してよろしいですか？")) {
                                document.forms[0].submit();
                            }
                        }
            </script>
        </c:if>
    </c:param>

</c:import>