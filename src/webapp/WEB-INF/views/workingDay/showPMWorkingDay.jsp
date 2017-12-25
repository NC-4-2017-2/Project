<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Working Day</title>
</head>
<body>
<form action="/workingDay/showUpdatePMWorkingDayStatus/${workingDay.workingDayId}"
      method="post">
    ${workingDay}

    <button type="submit" name = "status" value="APPROVED">Approve</button>
    <button type="submit" name = "status" value="DISAPPROVED">Disapprove</button>

    <p><c:if test="${not empty errorMap}">
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </c:if></p>
</form>
</body>
</html>
