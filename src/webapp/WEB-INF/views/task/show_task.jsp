<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<head>
    <title>Your task</title>
</head>
Task <br>
id = ${task.taskId} <br>
name = ${task.name} <br>
task type = ${task.taskType}<br>
start date = ${task.startDate}<br>
end date = ${task.endDate}<br>
planned end date = ${task.plannedEndDate}<br>
priority = ${task.priority}<br>
status = ${task.status}<br>
description = ${task.description}<br>
reopen counter = ${task.reopenCounter}<br>
comments = ${task.comments}<br>
task author = ${task.authorId}<br>
user who took task = ${task.userId}<br>
project = ${task.projectId}<br>


<form modelAttribute="modelTask" >
    <c:forEach items="${modelTask.tasks}" var="task" varStatus="status">
        Tasks ${status.index} :<br>
        <input type="text" name="tasks[${status.index}].taskId" value="${task.taskId}" placeholder="Task id"></td><br>
        <input type="text" name="tasks[${status.index}].name" value="${task.name}" placeholder="Task name"></td><br>
        <select name="tasks[${status.index}].taskType" >
            <option value="REQUEST_TASK">REQUEST_TASK</option>
            <option value="PROJECT_TASK">PROJECT_TASK</option>
        </select><br>
        <input type="text" name="tasks[${status.index}].startDate" value="${task.startDate}" placeholder="Start date"></td><br>
        <input type="text" name="tasks[${status.index}].endDate" value="${task.endDate}" placeholder="End date"></td><br>
        <input type="text" name="tasks[${status.index}].plannedEndDate" value="${task.plannedEndDate}" placeholder="Planned end date"></td><br>
        <select name="tasks[${status.index}].priority" >
            <option value="CRITICAL">CRITICAL</option>
            <option value="HIGH">HIGH</option>
            <option value="NORMAL">NORMAL</option>
            <option value="LOW">LOW</option>
        </select><br>
        <select name="tasks[${status.index}].status" >
            <option value="OPENED">OPENED</option>
            <option value="CLOSED">CLOSED</option>
            <option value="REOPENED">REOPENED</option>
            <option value="READY_FOR_TESTING">READY_FOR_TESTING</option>
        </select><br>
        <input type="text" name="tasks[${status.index}].description" value="${task.description}" placeholder="Description"></td><br>
        <input type="text" name="tasks[${status.index}].reopenCounter" value="${task.reopenCounter}" placeholder="Reopen counter"></td><br>
        <input type="text" name="tasks[${status.index}].comments" value="${task.comments}" placeholder="Comments"></td><br>
        <input type="text" name="tasks[${status.index}].authorId" value="${task.authorId}" placeholder="Author id"></td><br>
        <input type="text" name="tasks[${status.index}].userId" value="${task.userId}" placeholder="User Id"></td><br>
        <input type="text" name="tasks[${status.index}].projectId" value="${task.projectID}" placeholder="Project ID"></td><br>

        <br>
    </c:forEach>


</body>
</html>
