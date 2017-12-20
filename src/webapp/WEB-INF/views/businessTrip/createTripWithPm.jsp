<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Create business trip</title>
</head>
<body>
<form action="createBusinessTripPostWithPm" method="post">
    <input type="hidden" name="projectId" value="${projectId}"/>
    <input type="hidden" name="authorId" value="${authorId}"/>
    Choose country :
    <select name="country">
        <c:forEach items="${countriesList}" var="country">
            <option value="${country}">${country}</option>
        </c:forEach>
    </select><br>
    Choose start date : <input type="date" name="startDate"><br>
    Choose end date : <input type="date" name="endDate"><br>
    Choose user :
    <select name="user">
        <c:forEach items="${usersList}" var="user">
            <option value="${user.userId}">${user.lastName} ${user.firstName} ${user.dateOfBirth}</option>
        </c:forEach>
    </select><br>
    <input type="submit" value="Next">
</form>
</body>
</html>
