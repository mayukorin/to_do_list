<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${hasError}">
      <div id="flush_error">
            アカウント番号かパスワードが間違っています。
      </div>
</c:if>
<c:if test="${flush != null }">
    <div id="flush_success">
        <c:out value="${flush}"></c:out>
    </div>
</c:if>
<form method="POST" action="<c:url value='/login'/>">
    <label for="code">アカウント番号</label><br/>
    <input type="text" name="code" value="${code}"/>
    <br/><br/>

    <label for="password">パスワード</label><br/>
    <input type="password" name="password" />
    <br/><br/>

    <input type="hidden" name="_token" value="${_token}"/>
    <button type="submit">ログイン</button>
</form>