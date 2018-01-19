<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All comments</title>
</head>
<style>
    p {
        color: red;
    }
</style>
    <body>


        <c:forEach items="${comments}" var="comment">
        <tr>
                    <td>${comment.getCreationDate()}</td><br>
                    <td>${comment.getLastName()}</td> <td>${comment.getFirstName()}</td> added comment:<br>
            <br>
                    <br>
                    <td>${comment.getBodyComment()}</td><br>
                    <br>
            <br>
        </tr>
        </c:forEach>


        <form action="/task/createComment/${task.taskId}">
            <button type="submit">Add comment</button>
        </form>

    </body>
</html>
