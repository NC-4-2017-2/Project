<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FindVacationListByProject</title>
</head>
<body>
<c:forEach items="${vacationList}" var="vacation">
    <a href="/vacation/viewVacation/${vacation.vacationId}">Vacation${vacation.vacationId}</a> <br>
</c:forEach>
</body>
</html>
