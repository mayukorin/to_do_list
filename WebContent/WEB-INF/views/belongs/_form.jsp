<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${hasError}">
      <div id="flush_error">
                アカウント番号かパスワードが間違っています。
      </div>
</c:if>
<label for="code">アカウント番号</label><br/>
<input type="text" name="code" value="${group.code}"/>
<br/><br/>

<label for="password">パスワード</label><br/>
<input type="password" name="password"/>
<br/><br/>

<label for="position">ポジション</label><br/>
<input type="text" name="position"/>
<br/><br/>

<input type="hidden" name="_token" value="${_token}"/>
<button type="submit">登録</button>
