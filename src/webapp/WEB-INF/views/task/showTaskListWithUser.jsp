<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Set your user for task selection</title>
</head>
<body>
<c:forEach items="${tasksUser}" var="task">
    <a href="/task/showTask/${task.taskId}">Task${task.taskId}</a>
    </br>
</c:forEach>
</body>
</html>
