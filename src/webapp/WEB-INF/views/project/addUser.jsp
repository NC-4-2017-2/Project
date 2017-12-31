<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<form action="/statistic/addUser/${projectId}" method="post">
    Last name: <input type="text" name="lastName"><br>
    First name: <input type="text" name="firstName"><br>
    Start date: <input type="date" name="startDate"><br>
    End date: <input type="date" name="endDate"><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Add">
</form>
</body>
</html>
