<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
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
       Comments: <td>${task.comments}</td><br>
       Author id: <td>${taskAuthor.lastName} ${taskAuthor.firstName}</td><br>
       User id: <td>${taskUser.lastName} ${taskUser.firstName}</td><br>
       Project id: <td>${project.name}</td><br>
      </br>
    </tr>
</tbody>
<%@include file="../errors/errorMap.jsp" %>

<form action="/task/updateTask/${task.taskId}">
    <button type="submit">Edit task</button>
</form>
    </body>
</html>
