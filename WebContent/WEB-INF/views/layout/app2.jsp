<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
        <title>Todoアプリ</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
        <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">

    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/toppage/index' />">Todoアプリ</a></h1>&nbsp;&nbsp;&nbsp;
                </div>

                <div class="person_logout">
                    <c:if test="${sessionScope.login_person != null}">
                        <c:if test="${sessionScope.GroupBelong != null }">
                        <div id="group_name">
                            <div class="dropdown_btn" id="dropdown_btn">
                                グループ&nbsp;<i class="fas fa-caret-down"></i>&nbsp;&nbsp;&nbsp;
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
                    <div id="person_name">
                        <a href="<c:url value='/persons/show?id=${sessionScope.login_person.id}'/>"><c:out value="${sessionScope.login_person.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;</a>
                    </div>
                    <div id="logout">
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                    </c:if>
                </div>


            </div>

            <div id = "wrapper2">
                <aside class="left">
                </aside>
                <div id="main">
                    <div id="content2">
                            ${param.content2}
                    </div>
                </div>
                <aside class="right">
                    <c:if test="${sessionScope.group != null }">
                    <div id="group_member">
                        <div id="gmm" data-href="<c:url value='/groups/toppage?id=${sessionScope.group.id}'/>">${sessionScope.group.name}のメンバー</div>
                        <table id="members_list">
                            <tr align="center" data-href = "<c:url value='/group/toppage?id=${sessionScope.group.id}'/>">

                                <td >${sessionScope.group.name}</td>
                            </tr>
                            <c:if test="${sessionScope.ps != null }">
                                <c:forEach var="p" items="${ps}">
                                    <tr align="center" data-href = "<c:url value='/members/toppage?id=${p.id }'/>">
                                        <td >${p.name}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </table>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.updated_task != null }">
                        <div id="origin_task" data-href = "<c:url value='/groups/tasks/show?id=${sessionScope.updated_task.id}'/>">最新のtaskページ</div>

                        </c:if>
                </aside>

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
                  btn.addEventListener('click', function(){ //ボタンがクリックされたら
                    this.classList.toggle('is-open'); // is-openを付加する
                  });
                }
              });
            }());

        </script>
        <script>
        //tasklistをクリックした時の処理
        jQuery(function($) {

              //data-hrefの属性を持つtrを選択しclassにclickableを付加
              $('tr[data-href]').addClass('clickable')

                //クリックイベント
                .click(function(e) {

                  //e.targetはクリックした要素自体、それがa要素以外であれば
                  if(!$(e.target).is('a')){

                    //その要素の先祖要素で一番近いtrの
                    //data-href属性の値に書かれているURLに遷移する
                    window.location = $(e.target).closest('tr').data('href');}
              });
            });

        </script>
        <script>
        //tasklistをクリックした時の処理
        jQuery(function($) {

              //data-hrefの属性を持つtrを選択しclassにclickableを付加
              $('div[data-href]').addClass('clickable')

                //クリックイベント
                .click(function(e) {

                  //e.targetはクリックした要素自体、それがa要素以外であれば
                  if(!$(e.target).is('a')){

                    //その要素の先祖要素で一番近いtrの
                    //data-href属性の値に書かれているURLに遷移する
                    window.location = $(e.target).closest('div').data('href');}
              });
            });

        </script>
        <script>
        //フッターのmemberをクリックした時の処理
        jQuery(function($) {

              //data-hrefの属性を持つtrを選択しclassにclickableを付加
              $('li[data-href]').addClass('clickable')

                //クリックイベント
                .click(function(e) {

                  //e.targetはクリックした要素自体、それがa要素以外であれば
                  if(!$(e.target).is('a')){

                    //その要素の先祖要素で一番近いtrの
                    //data-href属性の値に書かれているURLに遷移する
                    window.location = $(e.target).closest('li').data('href');}
              });
            });

        </script>

    </body>
</html>