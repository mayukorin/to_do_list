<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../../layout/app2.jsp">
    <c:param name="content2">
    <h2>現在のtask</h2>
    <table id = "task_show">
                    <tbody>
                        <tr>
                        <th>登録者</th>
                        <td><c:out value="${sessionScope.updated_task.account.name}"/></td>
                        </tr>
                        <tr>
                        <th>更新者</th>
                        <td><c:out value="${sessionScope.updated_task.update_person.name }"/></td>
                        </tr>
                        <tr>
                        <th>リーダー</th>
                        <td><c:out value="${sessionScope.updated_task.task_leader.name }"/></td>
                        </tr>
                        <tr>
                        <th>title</th>
                        <td><c:out value="${sessionScope.updated_tasktask.title}"/></td>
                        </tr>
                        <tr>
                        <th>締め切り</th>
                        <td><fmt:formatDate value="${sessionScope.updated_task.deadline}" pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                        <th>内容</th>
                        <td>
                            <pre><c:out value="${SessionScope.updated_task.memo}"/></pre>
                        </td>
                        </tr>
                    </tbody>
                </table>
                <br/>
                <p><a href="<c:url value='/groups/tasks/show?id=${sessionScope.updated_task.id }'/>">task詳細ページに戻る</a></p>
                <h2>過去のtask</h2>
    <c:forEach var="t" items="${task_history}">
        <table id = "task_show">
                    <tbody>
                        <tr>
                        <th>登録者</th>
                        <td><c:out value="${t.account.name}"/></td>
                        </tr>
                        <tr>
                        <th>更新者</th>
                        <td><c:out value="${t.update_person.name }"/></td>
                        </tr>
                        <tr>
                        <th>リーダー</th>
                        <td><c:out value="${t.task_leader.name }"/></td>
                        </tr>
                        <tr>
                        <th>title</th>
                        <td><c:out value="${t.title}"/></td>
                        </tr>
                        <tr>
                        <th>締め切り</th>
                        <td><fmt:formatDate value="${t.deadline}" pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                        <th>内容</th>
                        <td>
                            <pre><c:out value="${t.memo}"/></pre>
                        </td>
                        </tr>
                    </tbody>
                </table>
                <br/>
                 <a href="<c:url value='/groups/tasks/show?id=${t.id}&iid=1'/>">コメントを見る</a>
                <br/><br/>

    </c:forEach>
    </c:param>
</c:import>