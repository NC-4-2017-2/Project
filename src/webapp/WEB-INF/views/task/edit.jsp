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
    <form action="/task/edit={id}" method="post"
          commandName=taskForm">
        <table border="0">
            <tr>
            <tr>
                <h2>Updating task</h2></td>
            </tr>
            <tr>
                <td>Task Id:</td>
                <td><input type="text" name="taskId" size="20"
                           value=${taskId}></td>
            </tr>
            <tr>
                <td>Task Name:</td>
                <td><input type="text" name="name" size="20"
                           value=${name}></td>
            </tr>
            <tr>
                <td>Task Type:</td>
                <td><select name="taskType">
                    <option value="REQUEST_TASK">REQUEST_TASK</option>
                    <option value="PROJECT_TASK">PROJECT_TASK</option>
                </select></td >
            </tr>
            <tr>
                <td>StartDate (mm/dd/yyyy):</td>
                <td><input type="text" name="startDate" size="20"
                           value=${startDate}></td>
            </tr>
            <tr>
                <td>EndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="endDate" size="20"
                           value=${endDate}></td>
            </tr>
            <tr>
            <tr>
                <td>PlannedEndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="plannedEndDate" size="20"
                           value=${plannedEndDate}></td>
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
                           value=${description}></td>
            </tr>
            <tr>
                <td>Reopen counter:</td>
                <td><input type="text" name="reopenCounter" size="20"
                           value=${reopenCounter}></td>
            </tr>
            <tr>
                <td>Comments:</td>
                <td><input type="text" name="comments" size="70"
                           value=${comments}></td>
            </tr>
            <tr>
                <td>Author id:</td>
                <td><input type="text" name="authorId" size="20"
                           value=${authorId}></td>
            </tr>
            <tr>
                <td>User id:</td>
                <td><input type="text" name="userId" size="20"
                           value=${userId}></td>
            </tr>
            <tr>
                <td>Project id:</td>
                <td><input type="text" name="projectId" size="20"
                           value=${projectId}></td>
            </tr>

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
            </form>

            <tr>
                <td><input type="submit" value="Update" size="40" width="40"/></td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>



