<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<style>
    p {
        color: red;
    }
</style>
    <title>Your task</title>
</head>

<br>
    <tr>
       Task name: <td>${task.name}</td><br>
       Task Type: <td>${task.taskType}</td><br>
       Start date:  <td>${task.startDate}</td><br>
        <c:if test="${task.status eq 'CLOSED'}">
       End date: <td>${task.endDate}</td><br>
        </c:if>
       Planned end date: <td>${task.plannedEndDate}</td><br>
       Priority: <td>${task.priority}</td><br>
       Status: <td>${task.status}</td><br>
       Description: <td>${task.description}</td><br>
       Reopen counter: <td>${task.reopenCounter}</td><br>
       Author task: <td>${taskAuthor.lastName} ${taskAuthor.firstName}</td><br>
       Assigned on user: <td>${taskUser.lastName} ${taskUser.firstName}</td><br>
       <c:if test="${task.taskType eq 'PROJECT_TASK'}">
       Project: <td>${project.name}</td><br>
       </c:if>
        </br>
    </tr>

<br>
<br>

<form action="/task/updateTask/${task.taskId}">
    <c:if test="${taskUser.userId eq curUser.userId}">
        <button type="submit">Edit task</button>
    </c:if>
</form>

<form action="/task/showAllComments/${task.taskId}">
        <button type="submit">Show all comments</button>
</form>

<form action="/task/createComment/${task.taskId}">
    <button type="submit">Add comment</button>
</form>

<%@include file="../errors/errorMap.jsp" %>


</html>
