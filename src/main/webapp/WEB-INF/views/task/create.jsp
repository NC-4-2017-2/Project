<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create a new task</title>
</head>
<body>
<div align="center">
    <form action="/task/create" method="post" name="/task/create"
          commandName=taskForm">
        <table border="0">
            <tr>
                <h2>Create Task</h2></td>
            </tr>
            <tr>
                <td>Task Id:</td>
                <td><input type="text" name="taskId" size="20"/></td>
            </tr>
            <tr>
                <td>Task Name:</td>
                <td><input type="text" name="name" size="20"/></td>
            </tr>
            <tr>
                <td>Task Type:</td>
                <td><input type="text" name="taskType" size="20"/></td>
            </tr>
            <tr>
                <td>StartDate (mm/dd/yyyy):</td>
                <td><input type="text" name="startDate" size="20"/></td>
            </tr>
            <tr>
                <td>EndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="endDate" size="20"/></td>
            </tr>
            <tr>
            <tr>
                <td>PlannedEndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="plannedEndDate" size="20"/></td>
            </tr>
            <tr>
                <td>Priority:</td>
                <td><input type="text" name="priority" size="20"/></td>
            </tr>
            <tr>
            <tr>
                <td>Status:</td>
                <td><input type="text" name="status" size="20"/></td>
            </tr>
            <tr>
                <td>Descruption task:</td>
                <td><input type="text" name="description" size="70"/></td>
            </tr>
            <tr>
                <td>Reopen counter:</td>
                <td><input type="text" name="reopenCounter" size="20"/></td>
            </tr>
            <tr>
                <td>Comments:</td>
                <td><input type="text" name="comments" size="70"/></td>
            </tr>
            <tr>
                <td>Author id:</td>
                <td><input type="text" name="authorId" size="20"/></td>
            </tr>
            <tr>
                <td>User id:</td>
                <td><input type="text" name="userId" size="20"/></td>
            </tr>
            <tr>
                <td>Project id:</td>
                <td><input type="text" name="projectId" size="20"/></td>
            </tr>

            <tr>
                <input type="submit" value="Create"/></td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>
