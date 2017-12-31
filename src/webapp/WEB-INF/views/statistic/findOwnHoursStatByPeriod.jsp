<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Own hours statistic</title>
</head>
<body>
<form action="/statistic/viewOwnHoursStat">
    Start date: <input type="date" name="startDate"><br>
    End date: <input type="date" name="endDate"><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Find">
</form>
</body>
</html>
