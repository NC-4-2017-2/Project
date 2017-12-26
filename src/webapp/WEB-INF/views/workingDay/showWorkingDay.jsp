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

    <c:if test="${currentUser.jobTitle.name() eq 'PROJECT_MANAGER'}">
        <button type="submit" name="status" value="APPROVED">Approve</button>
        <button type="submit" name="status" value="DISAPPROVED">Disapprove
        </button>
    </c:if>
    <%@include file="../errors/errorMap.jsp" %>
</form>
</body>
</html>
