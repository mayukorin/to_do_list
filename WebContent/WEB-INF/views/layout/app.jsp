<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <title>Todoアプリ</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/toppage/index' />">Todoアプリ</a></h1>&nbsp;&nbsp;&nbsp;
                </div>
                <c:if test="${sessionScope.GroupBelong != null }">
                    <div id="group_name">
                        <div class="dropdown_btn" id="dropdown_btn">
                            グループ
                        </div>
                        <div class="dropdown_body">
                            <ul class="dropdown_list">
                            <c:forEach var="group" items="${sessionScope.GroupBelong}">
                                <li class="dropdown_item"><a href="<c:url value='/groups/toppage?id=${group.id}'/>"class="dropdown_item-link"><c:out value="${group.name}"/></a></li>
                            </c:forEach>
                            </ul>
                        </div>
                    </div>
                </c:if>
                <c:if test="${sessionScope.login_person != null}">
                    <div id="person_name">
                        <a href="<c:url value='/persons/show?id=${sessionScope.login_person.id}'/>"><c:out value="${sessionScope.login_person.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;</a>
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by Mayuko inoue
            </div>
        </div>
        <script>
        (function () {
              document.addEventListener('DOMContentLoaded', function() { // HTML解析が終わったら
                const btn = document.getElementById('dropdown_btn'); // ボタンをidで取得
                if(btn) { // ボタンが存在しないときにエラーになるのを回避
                  btn.addEventListener('mouseover', function(){ //ボタンがクリックされたら
                    this.classList.toggle('is-open'); // is-openを付加する
                  });
                }
              });
            }());

        </script>

    </body>
</html>