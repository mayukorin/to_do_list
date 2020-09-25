<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<label for="content">コメント</label><br/>
            <textarea name="content" rows="10" cols="50">${comment.content}</textarea>
            <br/><br/>
            <input type="hidden" name="id" value="${task.id}"/>
            <input type="hidden" name="_token" value="${_token}"/>
            <button type="submit">投稿</button>