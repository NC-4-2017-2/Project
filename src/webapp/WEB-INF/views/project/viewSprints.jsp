<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Show sprint list</title>
</head>
<body>
<table border="0">
    <c:forEach items="${sprintList}" var="sprint">
        <a href="/project/showSprint/${sprint.sprintId}">${sprint.name}</a> <br>
    </c:forEach>
    <%@include file="../errors/errorMap.jsp" %>
</table>
</body>
</html>
