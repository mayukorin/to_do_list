<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.0/jquery-ui.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-datetimepicker@2.5.20/build/jquery.datetimepicker.full.min.js"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jquery-datetimepicker@2.5.20/jquery.datetimepicker.css">
<script type="text/javascript"></script>
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
<input id="datetimepicker" type="text" name="deadline"  value="${task.deadline}"/>
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
   $('#datetimepicker').datetimepicker({
       step:1
   });
</script>