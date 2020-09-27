<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app2.jsp">
    <c:param name="content2">
        <c:if test="${hasError}">
            <div id="flush_error">
                    ポジションを入力してください。
            </div>
        </c:if>
        <h2>編集ページ</h2>


        <form method="POST" action="<c:url value='/belongs/update' />">
            <label for="name">グループ</label><br/>
            <c:out value="${belong.group.name }"/>
            <br/><br/>

            <label for="position">ポジション</label><br/>
            <input type="text" name="position" value=${belong.position }/>
            <br/><br/>

            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit">登録</button>
        </form>
        <p><a href="<c:url value='/persons/show'/>">アカウント情報詳細ページへ戻る</a></p>
    </c:param>

</c:import>