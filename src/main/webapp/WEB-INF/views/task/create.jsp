<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create a new task</title>
</head>
<body>
<div align="center">
    <form action="/task/create" method="post" name="/task/create">
        <table border="0">
            <tr>
                <h2>Create Task</h2></td>
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

            <tr>
                <td><input type="submit" value="Create" size="40" width="40"/></td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>
