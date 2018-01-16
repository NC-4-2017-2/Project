<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select task type</title>
</head>
<style>
    p {
        color: red;
    }
</style>
<body>
<form action="/task/selectTaskType">
    <tr>
        <td>Select your task type:</td>
        <td><select name="taskType" required>
            <option value="PROJECT_TASK">PROJECT_TASK</option>
            <option value="REQUEST_TASK">REQUEST_TASK</option>
        </select></td>
    </tr>
    <%@include file="../errors/errorMap.jsp" %>
    <br>
    <input type="submit" value="Next">
</form>
</body>
</html>
