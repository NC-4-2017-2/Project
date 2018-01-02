<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find task by your id</title>
</head>
<body>
<form action="/task/findTaskByUserId">
    Select task status:
    <select name="status">
        <option value="OPENED">OPENED</option>
        <option value="CLOSED">CLOSED</option>
        <option value="REOPENED">REOPENED</option>
        <option value="READY_FOR_TESTING">READY_FOR_TESTING</option>
    </select><br>

    Start date: <input type="date" name="startDate"><br>
    End date: <input type="date" name="endDate"><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Find">
</form>


    <br>
</body>
</html>
