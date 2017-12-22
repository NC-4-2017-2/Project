<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Vacation</title>
</head>
<body>

<c:forEach items="${vacationList}" var="vacation">
    ${vacation} <br>
</c:forEach>
</body>
</html>
