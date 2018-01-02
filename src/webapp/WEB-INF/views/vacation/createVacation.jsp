<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Create vacation</title>
</head>
<body>

<form action="/vacation/createVacation" method="post">
    Choose start date : <input type="date" name="startDate" required><br>
    Choose end date : <input type="date" name="endDate" required><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Create"/></td>
</form>
</body>
</html>
