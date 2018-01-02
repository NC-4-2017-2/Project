<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Vacation</title>
</head>
<body>
<c:forEach items="${vacationList}" var="vacation">
    <a href="/vacation/showVacation/${vacation.vacationId}">Vacation
        - ${vacation.startDate}</a> <br>
</c:forEach>
<%@include file="../errors/errorMap.jsp" %>
</body>
</html>
