<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Show Business Trips</title>
</head>
<body>
<c:forEach items="${tripList}" var="trip">
    <a href="/businessTrip/showTrip/${trip.businessTripId}">BusinessTrip${trip.businessTripId}</a> <br>
</c:forEach>
<%@include file="../errors/errorMap.jsp" %>
</body>
</html>
