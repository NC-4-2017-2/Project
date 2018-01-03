<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Update vacation</title>
</head>
<body>
<form action="/vacation/updateAuthorVacation/${vacation.vacationId}"
      method="post">
    Choose start date : <input type="date" name="startDate" required><br>
    Choose end date : <input type="date" name="endDate" required><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Update">
</form>
</body>
</html>
