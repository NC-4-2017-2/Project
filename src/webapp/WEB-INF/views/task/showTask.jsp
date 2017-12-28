<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<head>
    <title>Your task</title>
</head>
<br>
<c:forEach var="task" items="${modelTask}">
    <tr>
       Task name: <td>${task.getName()}</td><br>
       Task Type: <td>${task.getTaskType()}</td><br>
       Start date:  <td>${task.getStartDate()}</td><br>
        <c:if test="${task.getStatus() eq 'CLOSED'}">
       End date: <td>${task.getEndDate()}</td><br>
        </c:if>
       Planned end date: <td>${task.getPlannedEndDate()}</td><br>
       Priority: <td>${task.getPriority()}</td><br>
       Status: <td>${task.getStatus()}</td><br>
       Description: <td>${task.getDescription()}</td><br>
       Reopen counter: <td>${task.getReopenCounter()}</td><br>
       Comments: <td>${task.getComments()}</td><br>
       Author id: <td>${task.getAuthorId()}</td><br>
       User id: <td>${task.getUserId()}</td><br>
       Project id: <td>${task.getProjectId()}</td><br>
      </br>
    </tr>
</c:forEach>
</tbody>
    </body>
</html>
