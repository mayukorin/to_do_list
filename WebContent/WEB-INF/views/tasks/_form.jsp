<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script src="https://rawgit.com/jquery/jquery-ui/master/ui/i18n/datepicker-ja.js"></script>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br/>
        <c:forEach var="error" items="${errors}">
            <c:out value="${error}" /><br/>
        </c:forEach>
    </div>
</c:if>
<label for="title">Task名</label><br/>
<input type="text" name="title" value="${task.title}"/>
<br/><br/>

<label for="deadline">締め切り</label><br/>
<input id="datepicker" type="text" name="deadline"  value="${task.deadline}"/>
<br/><br/>

<label for="memo">内容</label><br />
<textarea name="memo" rows="10" cols="50">${task.memo}</textarea>
<br /><br />

<p>公開範囲</p>
<c:if test="${groups != null }">
    <c:forEach var="group" items="${groups}">
        <input type="checkbox" name="${group.id}" value="${group.id}">${group.name}
    </c:forEach>
</c:if>
<br/><br/>

<input type="hidden" name="_token" value="${_token}"/>
<button type="submit">投稿</button>
<script>
   $('#datepicker').datepicker();
</script>