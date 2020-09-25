<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app2.jsp">
    <c:param name="content2">
    <c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br/>
        <c:forEach var="error" items="${errors}">
            <c:out value="${error}" /><br/>
        </c:forEach>
    </div>
    </c:if>
    <h2>アカウント情報 編集ページ</h2>
    <p>（パスワードは変更する場合のみ入力してください）</p>
    <form method="POST" action="<c:url value='/groups/update' />">

                <c:import url="../groups/_form.jsp" />

                 <label for="leader">リーダーのアカウント番号</label><br/>
                 <input type="text" name="leader"  value="${group.leader.code}"/>
                 <br/><br/>

                 <input type="hidden" name="_token" value="${_token}"/>
                 <button type="submit">投稿</button>
                 <br/><br/>
   </form>
   <p><a href="<c:url value='/group/show?id=${group.id}' />">アカウント情報詳細に戻る</a></p>

    </c:param>
</c:import>