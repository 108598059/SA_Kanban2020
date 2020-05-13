<%--
  Created by IntelliJ IDEA.
  User: bao
  Date: 2020/5/13
  Time: 下午 04:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>kanban</title>
    </head>
    <body>
        <form action="homepage" method="post">
            <input name="boardName" type="text">
            <input type="submit" value="create Board">
        </form>
    </body>
</html>
