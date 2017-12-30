<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
    p {
        color: red;
    }
</style>
    <title>ProjectByStartDate</title>
</head>
<body>
<form action="/project/findProjectByStartDate">
    Please, choose start and end of period:<br>
    Start date: <input type="date" name="startDate"><br>
    End date: <input type="date" name="endDate"><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Find">
</form>
</body>
</html>
