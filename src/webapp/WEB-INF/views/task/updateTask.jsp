<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update task</title>
</head>
<body>
<div align="center">
    <form action="/task/updateTask/${taskId}" method="post"
          commandName=taskForm">
        <table border="0">
            <tr>
            <tr>
                <h2>Updating task</h2></td>
            </tr>
            <tr>
                <td>Task Name:</td>
                <td><input type="text" name="name" size="20"
                           value=${task.name}></td>
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
                <td><input type="date" name="startDate"
                           value="${task.startDate}" ></td>
            </tr>
            <tr>
                <td><input type="date" name="endDate" size="20" hidden = false
                           value=${task.endDate}></td>
            </tr>
            <tr>
            <tr>
                <td>PlannedEndDate:</td>
                <td><input type="date" name="plannedEndDate" size="20"
                           value=${task.plannedEndDate}></td>
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
            <c:if test="${taskUser.userId eq curUser.userId}">
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
            </c:if>
            <tr>
                <td>Description task:</td>
                <td><input type="text" name="description" size="70"
                           value=${task.description}></td>
            </tr>
            <tr>
                <td><input type="text" name="reopenCounter" size="20" hidden = false
                           value=${task.reopenCounter}></td>
            </tr>
                <td>User last name:</td>
                <td><input type="text" name="lastName" value=${taskUser.lastName}>
                </td>
            </tr>
            <tr>
                <td>User first name:</td>
                <td><input type="text" name="firstName" value=${taskUser.firstName}>
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
        </table>
        <%@include file="../errors/errorMap.jsp" %>
        <c:if test="${taskUser.userId eq curUser.userId}">
            <tr>
                <td><input type="submit" value="Update" size="40" width="40"/></td>
            </tr>
        </c:if>
        </table>
    </form>
</div>
</body>
</html>