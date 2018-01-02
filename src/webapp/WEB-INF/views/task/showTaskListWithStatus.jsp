<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Set status for task selection</title>
</head>
<body>
<c:forEach items="${taskList}" var="task">
    <a href="/task/showTask/${task.taskId}">Task${task.taskId}</a>
    </br>
</c:forEach>
</body>
</html>
