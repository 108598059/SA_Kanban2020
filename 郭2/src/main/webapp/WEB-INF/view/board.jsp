<%--
  Created by IntelliJ IDEA.
  User: bao
  Date: 2020/5/13
  Time: 下午 05:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <style>
            td#boardid{
                text-align: center;
            }
        </style>
        <title>${boardName}</title>
    </head>
    <body>
        <table border="1" style="width: 100%; height: 100%">
            <tr style="width: 10%; height: 10%">
                <td id="boardId" >
                    ${boardName}<br>
                    ${boardId}
                </td>
            </tr>
            <tr></tr>
        </table>
    </body>
</html>

