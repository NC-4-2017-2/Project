<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Create a new task</title>
</head>
<body>
    <form action="/task/createTask" method="post">
        <table border="0">
            <tr>
                <h2>Create Task</h2></td>
            </tr>
            <tr>
                <td>Task Name:</td>
                <td><input type="text" name="name" size="20"
                           value=${name}></td>
            </tr>
            <tr>
                <td><select name="taskType" hidden="false" required>
                    <option value="PROJECT_TASK">PROJECT_TASK</option>
                    <option value="REQUEST_TASK">REQUEST_TASK</option>
                </select></td>
            </tr>
            <tr>
                <td>StartDate:</td>
                <td><input type="date" name="startDate" size="20"></td>
            </tr>
            <tr>
                <td>PlannedEndDate:</td>
                <td><input type="date" name="plannedEndDate" size="20"
                           value=${plannedEndDate}></td>
            </tr>
            <tr>
                <td>Priority:</td>
                <td><select name="priority" required>
                    <option value="CRITICAL">CRITICAL</option>
                    <option value="HIGH">HIGH</option>
                    <option value="NORMAL">NORMAL</option>
                    <option value="LOW">LOW</option>
                </select></td>
            </tr>
            <tr>
                <td>Description task:</td>
                <td><input type="text" name="description" size="70"
                           value=${description}></td>
            </tr>
            <tr>
                <td>User last name:</td>
                <td><input type="text" name="lastName">
                </td>
            </tr>
            <tr>
                <td>User first name:</td>
                <td><input type="text" name="firstName">
                </td>
            </tr>
            <tr>
                <td>Project Name:</td>
                <td>
                    <select name="projectNames">
                        <c:forEach items="${projectNamesList}" var="projectNames">
                            <option value="${projectNames}">${projectNames}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>

        <%@include file="../errors/errorMap.jsp" %>
        <tr>
            <td><input type="submit" value="Create" size="40" width="40"/>
            </td>
        </tr>
        </table>
    </form>
</body>
</html>