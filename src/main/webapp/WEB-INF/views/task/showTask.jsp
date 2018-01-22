<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your task</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-4">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <th scope="col" colspan="4" style="text-align: center;">
            Your task
        </th>
        </thead>
    <tbody>

        <tr>
            <th scope="row">Task name:</th>
            <td>${task.name}</td>
        </tr>
        <tr>
            <th scope="row">Task Type:</th>
            <td>${task.taskType}</td>
        </tr>
        <tr>
            <th scope="row">Start date:</th>
            <td>${task.startDate}</td>
        </tr>
        <c:if test="${task.status eq 'CLOSED'}">
        <tr>
            <th scope="row">End date:</th>
            <td>${task.endDate}</td>
        </tr>
        </c:if>
        <tr>
            <th scope="row">Planned end date:</th>
            <td>${task.plannedEndDate}</td>
        </tr>
        <tr>
            <th scope="row">Priority:</th>
            <td>${task.priority}</td>
        </tr>
        <tr>
            <th scope="row">Status:</th>
            <td>${task.status}</td>
        </tr>
        <tr>
            <th scope="row">Description:</th>
            <td>${task.description}</td>
        </tr>
        <tr>
            <th scope="row">Reopen counter:</th>
            <td>${task.reopenCounter}</td>
        </tr>
        <tr>
            <th scope="row">Author of task:</th>
            <td>${taskAuthor.lastName} ${taskAuthor.firstName}</td>
        </tr>
        <tr>
            <th scope="row"> Assigned on user:</th>
            <td>${taskUser.lastName} ${taskUser.firstName}</td>
        </tr>
        <c:if test="${task.taskType eq 'PROJECT_TASK'}">
        <tr>
            <th scope="row"> Project:</th>
            <td>${project.name}</td>
        </tr>
       </c:if>
        </tbody>
    </table>

<div class="form-group">
    <input onclick="window.history.go(-1); return false;"
               type="button" class="btn btn-primary btn-md"
               value="Back"/>
<br>
<br>
<form action="/task/updateTask/${task.taskId}">
    <c:if test="${taskUser.userId eq curUser.userId}">
        <button type="submit" class="btn btn-primary btn-md">Edit task</button>
    </c:if>
</form>

<form action="/task/showAllComments/${task.taskId}">
        <button type="submit" class="btn btn-primary btn-md">Show all comments</button>
</form>

<form action="/task/createComment/${task.taskId}">
    <button type="submit" class="btn btn-primary btn-md">Add comment</button>
</form>
</div>

<%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
