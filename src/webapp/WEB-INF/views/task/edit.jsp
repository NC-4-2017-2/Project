<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update task</title>
</head>
<body>
<div align="center">
    <form action="/task/edit={projectId}/taskId={taskId}" method="post"
          commandName=taskForm">
        <table border="0">
            <form modelAttribute="modelTask" >
              <c:forEach var="task" items="${modelTask}">
            <tr>
            <tr>
                <h2>Updating task</h2></td>
            </tr>
            <tr>
                <td>Task Id:</td>
                <td><input type="text" name="taskId" size="20"
                           value=${task.getTaskId()}></td>
            </tr>
            <tr>
                <td>Task Name:</td>
                <td><input type="text" name="name" size="20"
                           value=${task.getName()}></td>
            </tr>
            <tr>
                <td>Task Type:</td>
                <td><select name="taskType">
                    <option value="REQUEST_TASK">REQUEST_TASK</option>
                    <option value="PROJECT_TASK">PROJECT_TASK</option>
                </select></td >
            </tr>
            <tr>
                <td>StartDate:</td>
                <td><input type="date" name="startDate" size="20"
                           value=${task.getStartDate()}></td>
            </tr>
            <tr>
                <td>EndDate:</td>
                <td><input type="date" name="endDate" size="20"
                           value=${task.getEndDate()}></td>
            </tr>
            <tr>
            <tr>
                <td>PlannedEndDate:</td>
                <td><input type="date" name="plannedEndDate" size="20"
                           value=${task.getPlannedEndDate()}></td>
            </tr>
            <tr>
                <td>Priority:</td>
                <td><select name="priority">
                    <option value="CRITICAL">CRITICAL</option>
                    <option value="HIGH">HIGH</option>
                    <option value="NORMAL">NORMAL</option>
                    <option value="LOW">LOW</option>
                </select></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td>
                    <select name="status">
                        <option value="OPENED">OPENED</option>
                        <option value="CLOSED">CLOSED</option>
                        <option value="REOPENED">REOPENED</option>
                        <option value="READY_FOR_TESTING">READY_FOR_TESTING</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Description task:</td>
                <td><input type="text" name="description" size="70"
                           value=${task.getDescription()}></td>
            </tr>
            <tr>
                <td>Reopen counter:</td>
                <td><input type="text" name="reopenCounter" size="20"
                           value=${task.getReopenCounter()}></td>
            </tr>
            <tr>
                <td>Comments:</td>
                <td><input type="text" name="comments" size="70"
                           value=${task.getComments()}></td>
            </tr>
            <tr>
                <td>Author id:</td>
                <td><input type="text" name="authorId" size="20"
                           value=${task.getAuthorId()}></td>
            </tr>
            <tr>
                <td>User id:</td>
                <td><input type="text" name="userId" size="20"
                           value=${task.getUserId()}></td>
            </tr>
            <tr>
                <td>Project id:</td>
                <td><input type="text" name="projectId" size="20"
                           value=${task.getProjectId()}></td>
                </c:forEach>
            </form>
        </table>

            <tr>
                <td><input type="submit" value="Update" size="40" width="40"/></td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>



