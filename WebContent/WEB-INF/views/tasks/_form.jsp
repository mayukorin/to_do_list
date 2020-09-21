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

<c:choose>
    <c:when test="${groups != null }">
    <p>公開範囲</p>
        <c:forEach var="group" items="${groups}">
            <input type="checkbox" name="${group.id}" value="${group.id}"  >${group.name}
        </c:forEach>
    <c:if test="${shows_group != null }">
        <c:forEach var="group" items = "${shows_group}">
            <input type="checkbox" name="${group.id}" value="${group.id}" checked>${group.name}
        </c:forEach>
    </c:if>
    </c:when>
    <c:otherwise>
    <label for="task_leader">タスクリーダー</label><br/>
    <c:choose>
        <c:when test="${task.id == null }">
            <input type="text" name="task_leader" value="${task.task_leader.code}" />
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${ task.task_leader.id == sessionScope.login_person.id}">
                    <input type="text" name="task_leader" value="${task.task_leader.code}" />
                </c:when>
                <c:otherwise>
                    <input type="text" name="task_leader" value="${task.task_leader.code}" readonly/>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
    </c:otherwise>
</c:choose>

<br/><br/>

<input type="hidden" name="_token" value="${_token}"/>
<button type="submit">投稿</button>
<script>
   $('#datetimepicker').datetimepicker({
       step:1
   });
</script>