<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>FindBusinessTripProject</title>
</head>
<body>
    <form action="createBusinessTrip" method="get">
        Choose project : <select name="projectName">
        <c:forEach items="${projectNamesList}" var="projectName">
            <option value="${projectName}">${projectName}</option>
        </c:forEach>
        </select><br>
        <input type="submit" value="Next">
    </form>
</body>
</html>