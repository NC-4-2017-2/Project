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
                <td><input type="text" name="taskId" size="20" hidden = true
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
                <td>StartDate:</td>
                <td><input type="date" name="startDate" size="20"
                           value=${startDate}></td>
            </tr>
            <tr>
                <td><input type="date" name="endDate" size="20" hidden = false
                           value=${endDate}></td>
            </tr>
            <tr>
            <tr>
                <td>PlannedEndDate:</td>
                <td><input type="date" name="plannedEndDate" size="20"
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
                <td><input type="text" name="reopenCounter" size="20" hidden = false
                           value=${reopenCounter}></td>
            </tr>
            <tr>
                <td>Comments:</td>
                <td><input type="text" name="comments" size="70"
                           value=${comments}></td>
            </tr>
            <tr>
                <td>Author:</td>
                <td>
                    <select name="authorNames">
                        <c:forEach items="${authorNames}" var="name">
                            <option value="${name.key}">${name.value}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>User:</td>
                <td>
                    <select name="userNames">
                        <c:forEach items="${userNames}" var="user">
                            <option value="${user.key}">${user.value}</option>
                        </c:forEach>
                    </select>
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
                <td><input type="submit" value="Create" size="40" width="40"/></td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>
