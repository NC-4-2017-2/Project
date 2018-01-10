<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Status your task</title>
</head>
<style>
    p {
        color: red;
    }
</style>
<body>
<form action="/task/findOwnTask">

    Select task status:
    <select name="status">
    <option value="OPENED">OPENED</option>
    <option value="CLOSED">CLOSED</option>
    <option value="REOPENED">REOPENED</option>
    <option value="READY_FOR_TESTING">READY_FOR_TESTING</option>
</select><br>
    <%@include file="../errors/errorMap.jsp" %>
    <br>
    <input type="submit" value="Find">
</form>
</body>
</html>
