<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>FindTripByStatusAndPeriod</title>
</head>
<body>
<form action="/businessTrip/viewTrip">
    Choose status:<select name="status" required>
    <option value="APPROVED">APPROVED</option>
    <option value="DISAPPROVED">DISAPPROVED</option>
    <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL</option>
</select><br>

    Start date: <input type="date" name="startDate"><br>
    End date: <input type="date" name="endDate"><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Find">
</form>
</body>
</html>