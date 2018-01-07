<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Set your user for task selection</title>
</head>
<style>
    p {
        color: red;
    }
</style>
<body>
<c:forEach items="${taskList}" var="task">
    <a href="/task/showTask/${task.taskId}">Task${task.taskId}</a>
    </br>
</c:forEach>
<%@include file="../errors/errorMap.jsp" %>
</body>
</html>
